package digicorp.services;

import digicorp.entity.Department;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class DepartmentDAO {
    protected EntityManager em;
    public DepartmentDAO(EntityManager em) {
        this.em = em;
    }

    public List<Department> findAll() {
        TypedQuery<Department> q = em.createQuery("SELECT d FROM Department d", Department.class);
        return q.getResultList();
    }
}