package digicorp.services;
import digicorp.entity.Department;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

/**
 * Data Access Object (DAO) for the {@link Department} entity.
 * <p>
 * This class provides database access methods for performing CRUD and query
 * operations on departments using JPA. It relies on a provided
 * {@link EntityManager} to manage persistence context and transactions.
 *
 * <p>Typical usage involves creating an instance with an {@link EntityManager}
 * and calling the available methods to retrieve department data.</p>
 */
public class DepartmentDAO {
    /** The {@link EntityManager} used to access the persistence context. */
    protected EntityManager em;

    /**
     * Constructs a new {@code DepartmentDAO} with the given {@link EntityManager}.
     *
     * @param em the EntityManager used for database operations
     */
    public DepartmentDAO(EntityManager em) {
        this.em = em;
    }

    /**
     * Retrieves all departments from the database, ordered by their department number.
     *
     * @return a list of {@link Department} entities; never {@code null}
     */
    public List<Department> findAll() {
        TypedQuery<Department> q = em.createQuery("SELECT d FROM Department d ORDER BY d.deptNo", Department.class);
        return q.getResultList();
    }


}