package digicorp.services;

import digicorp.dto.*;
import digicorp.entity.*;
import jakarta.persistence.EntityManager;
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
        TypedQuery<Employee> q = em.createQuery(
                "SELECT DISTINCT e FROM Employee e " +
                        "LEFT JOIN FETCH e.departments de " +
                        "LEFT JOIN FETCH de.department d " +
                        "WHERE e.empNo = :empNo",
                Employee.class
        );

        q.setParameter("empNo", empNo);

        Employee e = q.getSingleResult();

        // Optional: lazy load manager history if your endpoint needs it
        e.getManagedDepartments().size();

        return e;
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

            // Find the employee
            Employee emp = em.find(Employee.class, dto.getEmpNo());
            if (emp == null) {
                throw new Exception("Employee not found with empNo: " + dto.getEmpNo());
            }

            LocalDate newFromDate = dto.getFromDate();

            //A. UPDATING TITLES

            // 1. Find current title(s) for this employee
            TypedQuery<TitleHistory> query = em.createQuery(
                    "SELECT t FROM TitleHistory t WHERE t.employee.empNo = :empNo AND t.toDate = :maxDate",
                    TitleHistory.class
            );
            query.setParameter("empNo", emp.getEmpNo());
            query.setParameter("maxDate", LocalDate.of(9999, 1, 1));
            List<TitleHistory> currentTitles = query.getResultList();

            // 2. Update previous title(s) to end the day before the new promotion
            for (TitleHistory t : currentTitles) {
                t.setToDate(newFromDate.minusDays(1));
                em.merge(t);
            }

            // 3. Add new title record
            TitleHistoryId newId = new TitleHistoryId(emp.getEmpNo(), dto.getNewTitle(), newFromDate);
            TitleHistory newTitle = new TitleHistory();
            newTitle.setId(newId);
            newTitle.setEmployee(emp);
            newTitle.setToDate(LocalDate.of(9999, 1, 1));

            em.persist(newTitle);


            //B. UPDATING SALARY

            // 1. Find current salary(s) for this employee
            TypedQuery<SalaryHistory> salaryquery = em.createQuery(
                    "SELECT t FROM SalaryHistory t WHERE t.employee.empNo = :empNo AND t.toDate = :maxDate",
                    SalaryHistory.class
            );
            salaryquery.setParameter("empNo", emp.getEmpNo());
            salaryquery.setParameter("maxDate", LocalDate.of(9999, 1, 1));
            List<SalaryHistory> currentSalaries = salaryquery.getResultList();

            // 2. Update previous salary(s) to end the day before the new promotion
            for (SalaryHistory t : currentSalaries) {
                t.setToDate(newFromDate.minusDays(1));
                em.merge(t);
            }

            // 3. Add new salary record
            SalaryHistoryId newsId = new SalaryHistoryId(emp.getEmpNo(), newFromDate);
            SalaryHistory newSalary = new SalaryHistory();
            newSalary.setId(newsId);
            newSalary.setSalary(dto.getNewSalary());
            newSalary.setEmployee(emp);
            newSalary.setToDate(LocalDate.of(9999, 1, 1));

            em.persist(newSalary);

            // === C. UPDATE DEPARTMENT ONLY IF CHANGED ===

            TypedQuery<DeptEmployee> qDept = em.createQuery(
                    "SELECT d FROM DeptEmployee d WHERE d.employee.empNo = :empNo AND d.toDate = :maxDate",
                    DeptEmployee.class
            );
            qDept.setParameter("empNo", emp.getEmpNo());
            qDept.setParameter("maxDate", LocalDate.of(9999,1,1));

            List<DeptEmployee> currentDeptAssignments = qDept.getResultList();
            String oldDeptNo = currentDeptAssignments.isEmpty()
                    ? null
                    : currentDeptAssignments.get(0).getDepartment().getDeptNo();

            boolean departmentChanged =
                    dto.getNewDeptNo() != null &&
                            !dto.getNewDeptNo().equals(oldDeptNo);

            if (departmentChanged) {
                // 1. Close old DeptEmployee record
                for (DeptEmployee d : currentDeptAssignments) {
                    d.setToDate(newFromDate.minusDays(1));
                    em.merge(d);
                }

                // 2. Insert new DeptEmployee record
                Department newDept = em.find(Department.class, dto.getNewDeptNo());
                DeptEmployeeId deId =
                        new DeptEmployeeId(emp.getEmpNo(), dto.getNewDeptNo());

                DeptEmployee newDeptEmp = new DeptEmployee();
                newDeptEmp.setId(deId);
                newDeptEmp.setEmployee(emp);
                newDeptEmp.setDepartment(newDept);
                newDeptEmp.setFromDate(newFromDate);
                newDeptEmp.setToDate(LocalDate.of(9999,1,1));

                em.persist(newDeptEmp);
            }


// === D. UPDATE MANAGER ROLE ONLY IF dto.isManager() == true ===

// Only process manager update if promotion includes management role
            if (dto.isManager()) {

                // Step 1: Find current manager records
                TypedQuery<DeptManager> qMgr = em.createQuery(
                        "SELECT m FROM DeptManager m WHERE m.employee.empNo = :empNo AND m.toDate = :maxDate",
                        DeptManager.class
                );
                qMgr.setParameter("empNo", emp.getEmpNo());
                qMgr.setParameter("maxDate", LocalDate.of(9999,1,1));

                List<DeptManager> currentManagers = qMgr.getResultList();

                // Step 2: Determine if employee is already manager of the NEW dept
                boolean alreadyManagerOfNewDept = currentManagers.stream()
                        .anyMatch(m -> m.getDepartment().getDeptNo().equals(dto.getNewDeptNo()));

                if (!alreadyManagerOfNewDept) {

                    // 3. Close old manager records (if manager of another department)
                    for (DeptManager m : currentManagers) {
                        m.setToDate(newFromDate.minusDays(1));
                        em.merge(m);
                    }

                    // 4. Add new manager assignment
                    DeptManagerId mgrId =
                            new DeptManagerId(emp.getEmpNo(), dto.getNewDeptNo());

                    DeptManager newManager = new DeptManager();
                    newManager.setId(mgrId);
                    newManager.setEmployee(emp);
                    newManager.setFromDate(newFromDate);
                    newManager.setDepartment(em.find(Department.class, dto.getNewDeptNo()));
                    newManager.setToDate(LocalDate.of(9999,1,1));

                    em.persist(newManager);
                }
            }

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

}