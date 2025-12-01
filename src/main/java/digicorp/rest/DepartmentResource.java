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
 * Departments endpoints.
 */
@Path("/departments")
@Produces(MediaType.APPLICATION_JSON)
public class DepartmentResource {

    private static final EntityManagerFactory emf =
            JPAUtil.getEMF();

    @GET
    public Response listDepartments() {

        EntityManager em = emf.createEntityManager();

        try {
            DepartmentDAO service = new DepartmentDAO(em);
            List<Department> list = service.findAll();
            return Response.ok(list).build();
        } finally {
            em.close();  // prevent memory leaks
        }
    }
}