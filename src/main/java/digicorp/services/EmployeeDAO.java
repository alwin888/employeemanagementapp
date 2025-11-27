package digicorp.services;

import digicorp.entity.Employee;
import digicorp.entity.DeptEmployee;
import digicorp.util.JpaUtil;
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
        try {
            // Left joins to fetch collections (avoid duplicates with DISTINCT)
            TypedQuery<Employee> q = em.createQuery(
                    "SELECT DISTINCT e FROM Employee e " +
                            "LEFT JOIN FETCH e.salaryHistory " +
                            "LEFT JOIN FETCH e.titleHistory " +
                            "LEFT JOIN FETCH e.departments d " +
                            "LEFT JOIN FETCH e.managerHistory m " +
                            "WHERE e.empNo = :empNo", Employee.class);
            q.setParameter("empNo", empNo);
            return q.getResultStream().findFirst().orElse(null);
        } finally {
            em.close();
        }
    }

    // Paged list of employees in a department: returns only required fields via projection
    public List<Object[]> findByDepartmentPaged(String deptNo, int offset, int pageSize) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<Object[]> q = em.createQuery(
                    "SELECT e.empNo, e.firstName, e.lastName, e.hireDate " +
                            "FROM DeptEmployee de JOIN de.employee e " +
                            "WHERE de.department.deptNo = :deptNo ORDER BY e.empNo", Object[].class);
            q.setParameter("deptNo", deptNo);
            q.setFirstResult(offset);
            q.setMaxResults(pageSize);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    // Persist a new TitleHistory and/or SalaryHistory for promotion
    public void persistPromotionEntities(Object... entities) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            for (Object o : entities) em.persist(o);
            em.getTransaction().commit();
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }
}