package digicorp.services;

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

    // Paged list of employees in a department: returns only required fields via projection
//    public List<Object[]> findByDepartmentPaged(String deptNo, int offset, int pageSize) {
//        EntityManager em = JpaUtil.getEntityManager();
//        try {
//            TypedQuery<Object[]> q = em.createQuery(
//                    "SELECT e.empNo, e.firstName, e.lastName, e.hireDate " +
//                            "FROM DeptEmployee de JOIN de.employee e " +
//                            "WHERE de.department.deptNo = :deptNo ORDER BY e.empNo", Object[].class);
//            q.setParameter("deptNo", deptNo);
//            q.setFirstResult(offset);
//            q.setMaxResults(pageSize);
//            return q.getResultList();
//        } finally {
//            em.close();
//        }
//    }

}