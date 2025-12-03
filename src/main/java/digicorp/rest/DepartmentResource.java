package digicorp.rest;
import digicorp.services.DepartmentDAO;
import digicorp.entity.Department;
import digicorp.util.JPAUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 * REST resource providing access to department information.
 * <p>
 * This resource exposes endpoints under {@code /departments} and returns
 * JSON-formatted department data. It relies on JPA for database interaction
 * and delegates data retrieval to {@link DepartmentDAO}.
 *
 * <p>
 * All returned entities are automatically serialized into JSON by JAX-RS,
 * using the system's configured message body writers.
 */
@Path("/departments")
@Produces(MediaType.APPLICATION_JSON)
public class DepartmentResource {
    /**
     * Singleton {@link EntityManagerFactory} used to create
     * {@link EntityManager} instances for request processing.
     */
    private static final EntityManagerFactory emf =
            JPAUtil.getEMF();
    /**
     * Returns a JSON list of all departments.
     * <p>
     * This method:
     * <ul>
     *     <li>Creates a new {@link EntityManager} for the request</li>
     *     <li>Uses {@link DepartmentDAO} to fetch all departments</li>
     *     <li>Returns the results as a {@link Response} with status 200 (OK)</li>
     *     <li>Ensures the {@link EntityManager} is closed to prevent resource leaks</li>
     * </ul>
     *
     * @return an HTTP 200 response containing the list of {@link Department} objects
     */
    @GET
    public Response listDepartments() {

        EntityManager em = emf.createEntityManager();

        try {
            DepartmentDAO service = new DepartmentDAO(em);
            List<Department> list = service.findAll();
            return Response.ok(list).build();
        } finally {
            em.close();  // to prevent memory leaks
        }
    }
}