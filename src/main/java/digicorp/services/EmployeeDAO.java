package digicorp.services;

import digicorp.dto.EmployeeRecordDTO;
import digicorp.entity.Employee;
import digicorp.entity.DeptEmployee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

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




}