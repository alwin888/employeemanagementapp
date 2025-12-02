package digicorp.services;
import digicorp.dto.*;
import digicorp.entity.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.EntityTransaction;
import java.time.LocalDate;
import java.util.List;

public class EmployeeDAO {
    protected EntityManager em;

    public EmployeeDAO(EntityManager em) {
        this.em = em;
    }

    // Fetch full employee with history using JOIN FETCH
    public Employee findByIdWithHistory(int empNo) {
        try {
            TypedQuery<Employee> q = em.createQuery(
                    "SELECT DISTINCT e FROM Employee e " +
                            "LEFT JOIN FETCH e.departments de " +
                            "LEFT JOIN FETCH de.department d " +
                            "WHERE e.empNo = :empNo",
                    Employee.class
            );

            q.setParameter("empNo", empNo);

            Employee e = q.getSingleResult();

            // Optional eager load of manager departments
            e.getManagedDepartments().size();

            return e;

        } catch (NoResultException ex) {
            return null;
        }
    }

    public List<EmployeeRecordDTO> findByDepartment(String deptNo, int page) {
        int pageSize = 20;
        int offset = (page - 1) * pageSize;

        String jpql =
                "SELECT new digicorp.dto.EmployeeRecordDTO(" +
                        "   e.empNo, e.firstName, e.lastName, e.hireDate" +
                        ") " +
                        "FROM DeptEmployee de " +
                        "JOIN de.employee e " +
                        "WHERE de.id.deptNo = :deptNo " +
                        "ORDER BY e.empNo ASC";

        TypedQuery<EmployeeRecordDTO> query = em.createQuery(jpql, EmployeeRecordDTO.class);
        query.setParameter("deptNo", deptNo);
        query.setFirstResult(offset);
        query.setMaxResults(pageSize);

        return query.getResultList();
    }


    public void promoteEmployee(PromotionRequestDTO dto) throws Exception {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            // ======================================================
            // BASIC VALIDATION
            // ======================================================

            if (dto == null) {
                throw new Exception("{\"error\":\"Request body is missing or invalid JSON\"}");
            }

            if (dto.getEmpNo() <= 0) {
                throw new Exception("{\"error\":\"empNo must be a positive integer\"}");
            }

            if (dto.getNewTitle() == null || dto.getNewTitle().trim().isEmpty()) {
                throw new Exception("{\"error\":\"newTitle cannot be blank\"}");
            }

            if (dto.getFromDate() == null) {
                throw new Exception("{\"error\":\"fromDate is required (yyyy-MM-dd)\"}");
            }

            LocalDate newFromDate = dto.getFromDate();

            // SALARY VALIDATION
            if (dto.getNewSalary() <= 0) {
                throw new Exception("{\"error\":\"salary must be greater than 0\"}");
            }

            // DEPT VALIDATION ("d001" format, case insensitive)
            if (dto.getNewDeptNo() == null ||
                    !dto.getNewDeptNo().trim().matches("^[dD][0-9]{3}$")) {
                throw new Exception("{\"error\":\"deptNo must match pattern dXXX (e.g., d001, D005)\"}");
            }

            // Normalize to lowercase for DB consistency
            String deptNoNormalized = dto.getNewDeptNo().trim().toLowerCase();


            // ======================================================
            // LOAD EMPLOYEE
            // ======================================================
            Employee emp = em.find(Employee.class, dto.getEmpNo());
            if (emp == null) {
                throw new Exception("{\"error\":\"Employee not found with empNo: " + dto.getEmpNo() + "\"}");
            }


            // ======================================================
            // VALIDATE AGAINST EXISTING HISTORY DATES
            // ======================================================

            // --- SALARY HISTORY ---
            TypedQuery<SalaryHistory> latestSalaryQ = em.createQuery(
                    "SELECT s FROM SalaryHistory s WHERE s.employee.empNo = :empNo ORDER BY s.id.fromDate DESC",
                    SalaryHistory.class
            );
            latestSalaryQ.setParameter("empNo", emp.getEmpNo());
            latestSalaryQ.setMaxResults(1);

            SalaryHistory lastSalary = latestSalaryQ.getResultList().isEmpty()
                    ? null
                    : latestSalaryQ.getResultList().get(0);

            if (lastSalary != null && !newFromDate.isAfter(lastSalary.getId().getFromDate())) {
                throw new Exception(
                        "{\"error\":\"fromDate must be AFTER last salary change date: "
                                + lastSalary.getId().getFromDate() + "\"}"
                );
            }


            // --- TITLE HISTORY ---
            TypedQuery<TitleHistory> latestTitleQ = em.createQuery(
                    "SELECT t FROM TitleHistory t WHERE t.employee.empNo = :empNo ORDER BY t.id.fromDate DESC",
                    TitleHistory.class
            );
            latestTitleQ.setParameter("empNo", emp.getEmpNo());
            latestTitleQ.setMaxResults(1);

            TitleHistory lastTitle = latestTitleQ.getResultList().isEmpty()
                    ? null
                    : latestTitleQ.getResultList().get(0);

            if (lastTitle != null && !newFromDate.isAfter(lastTitle.getId().getFromDate())) {
                throw new Exception(
                        "{\"error\":\"fromDate must be AFTER last title change date: "
                                + lastTitle.getId().getFromDate() + "\"}"
                );
            }


            // --- DEPARTMENT HISTORY ---
            TypedQuery<DeptEmployee> latestDeptQ = em.createQuery(
                    "SELECT d FROM DeptEmployee d WHERE d.employee.empNo = :empNo ORDER BY d.fromDate DESC",
                    DeptEmployee.class
            );
            latestDeptQ.setParameter("empNo", emp.getEmpNo());
            latestDeptQ.setMaxResults(1);

            DeptEmployee lastDept = latestDeptQ.getResultList().isEmpty()
                    ? null
                    : latestDeptQ.getResultList().get(0);

            if (lastDept != null && !newFromDate.isAfter(lastDept.getFromDate())) {
                throw new Exception(
                        "{\"error\":\"fromDate must be AFTER last department change date: "
                                + lastDept.getFromDate() + "\"}"
                );
            }



            // ======================================================
            // A. UPDATE TITLE HISTORY
            // ======================================================
            TypedQuery<TitleHistory> query = em.createQuery(
                    "SELECT t FROM TitleHistory t WHERE t.employee.empNo = :empNo AND t.toDate = :maxDate",
                    TitleHistory.class
            );
            query.setParameter("empNo", emp.getEmpNo());
            query.setParameter("maxDate", LocalDate.of(9999, 1, 1));

            List<TitleHistory> currentTitles = query.getResultList();

            for (TitleHistory t : currentTitles) {
                t.setToDate(newFromDate.minusDays(1));
                em.merge(t);
            }

            TitleHistoryId newTitleId =
                    new TitleHistoryId(emp.getEmpNo(), dto.getNewTitle(), newFromDate);

            TitleHistory newTitle = new TitleHistory();
            newTitle.setId(newTitleId);
            newTitle.setEmployee(emp);
            newTitle.setToDate(LocalDate.of(9999, 1, 1));

            em.persist(newTitle);


            // ======================================================
            // B. UPDATE SALARY HISTORY
            // ======================================================
            TypedQuery<SalaryHistory> salaryQuery = em.createQuery(
                    "SELECT s FROM SalaryHistory s WHERE s.employee.empNo = :empNo AND s.toDate = :maxDate",
                    SalaryHistory.class
            );
            salaryQuery.setParameter("empNo", emp.getEmpNo());
            salaryQuery.setParameter("maxDate", LocalDate.of(9999, 1, 1));

            List<SalaryHistory> currentSalaries = salaryQuery.getResultList();

            for (SalaryHistory s : currentSalaries) {
                s.setToDate(newFromDate.minusDays(1));
                em.merge(s);
            }

            SalaryHistoryId newSalaryId =
                    new SalaryHistoryId(emp.getEmpNo(), newFromDate);

            SalaryHistory newSalary = new SalaryHistory();
            newSalary.setId(newSalaryId);
            newSalary.setSalary(dto.getNewSalary());
            newSalary.setEmployee(emp);
            newSalary.setToDate(LocalDate.of(9999, 1, 1));

            em.persist(newSalary);


            // ======================================================
            // C. UPDATE DEPARTMENT ASSIGNMENT (ONLY IF CHANGED)
            // ======================================================
            TypedQuery<DeptEmployee> qDept = em.createQuery(
                    "SELECT d FROM DeptEmployee d WHERE d.employee.empNo = :empNo AND d.toDate = :maxDate",
                    DeptEmployee.class
            );
            qDept.setParameter("empNo", emp.getEmpNo());
            qDept.setParameter("maxDate", LocalDate.of(9999, 1, 1));

            List<DeptEmployee> currentDeptAssignments = qDept.getResultList();
            String oldDeptNo = currentDeptAssignments.isEmpty()
                    ? null
                    : currentDeptAssignments.get(0).getDepartment().getDeptNo();

            boolean departmentChanged =
                    !deptNoNormalized.equalsIgnoreCase(oldDeptNo);

            if (departmentChanged) {
                for (DeptEmployee d : currentDeptAssignments) {
                    d.setToDate(newFromDate.minusDays(1));
                    em.merge(d);
                }

                Department newDept = em.find(Department.class, deptNoNormalized);

                DeptEmployeeId newDeptId =
                        new DeptEmployeeId(emp.getEmpNo(), deptNoNormalized);

                DeptEmployee newDeptEmp = new DeptEmployee();
                newDeptEmp.setId(newDeptId);
                newDeptEmp.setEmployee(emp);
                newDeptEmp.setDepartment(newDept);
                newDeptEmp.setFromDate(newFromDate);
                newDeptEmp.setToDate(LocalDate.of(9999, 1, 1));

                em.persist(newDeptEmp);
            }


            // ======================================================
            // D. MANAGER PROMOTION LOGIC
            // ======================================================
            if (dto.isManager()) {

                TypedQuery<DeptManager> qMgr = em.createQuery(
                        "SELECT m FROM DeptManager m WHERE m.employee.empNo = :empNo AND m.toDate = :maxDate",
                        DeptManager.class
                );
                qMgr.setParameter("empNo", emp.getEmpNo());
                qMgr.setParameter("maxDate", LocalDate.of(9999, 1, 1));

                List<DeptManager> currentManagers = qMgr.getResultList();

                boolean alreadyManagerOfDept =
                        currentManagers.stream()
                                .anyMatch(m -> m.getDepartment().getDeptNo().equalsIgnoreCase(deptNoNormalized));

                if (!alreadyManagerOfDept) {

                    for (DeptManager m : currentManagers) {
                        m.setToDate(newFromDate.minusDays(1));
                        em.merge(m);
                    }

                    DeptManagerId mgrId =
                            new DeptManagerId(emp.getEmpNo(), deptNoNormalized);

                    DeptManager newMgr = new DeptManager();
                    newMgr.setId(mgrId);
                    newMgr.setEmployee(emp);
                    newMgr.setFromDate(newFromDate);
                    newMgr.setDepartment(em.find(Department.class, deptNoNormalized));
                    newMgr.setToDate(LocalDate.of(9999, 1, 1));

                    em.persist(newMgr);
                }
            }


            // ======================================================
            // COMMIT
            // ======================================================
            tx.commit();

        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;   // Controller catches and returns JSON error properly
        }
    }
}