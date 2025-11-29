package digicorp.rest;

import digicorp.dto.*;
import digicorp.entity.Employee;
import digicorp.services.EmployeeDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Employee endpoints implementing your 4 required operations.
 */
@Path("/employees")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmployeeResource {
    private static final EntityManagerFactory emf;

    static {
        // Build EMF once
        Map<String, String> props = new HashMap<>();
        props.put("jakarta.persistence.jdbc.url", "jdbc:mariadb://localhost:3306/employees");

        emf = Persistence.createEntityManagerFactory("EmployeeService", props);
    }

    @GET
    @Path("/{empNo}")
    public Response getEmployee(@PathParam("empNo") int empNo) {
        EntityManager em = emf.createEntityManager();
        try {
            EmployeeDAO dao = new EmployeeDAO(em);
            Employee e = dao.findByIdWithHistory(empNo);

            if (e == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Employee " + empNo + " not found").build();
            }

            return Response.ok(e).build();

        } finally {
            em.close();
        }
    }

    @GET
    @Path("/by-department/{deptNo}")
    public Response getEmployeesByDepartment(
            @PathParam("deptNo") String deptNo,
            @QueryParam("page") @DefaultValue("1") int page
    ) {
        EntityManager em = emf.createEntityManager();

        try {
            EmployeeDAO dao = new EmployeeDAO(em);
            List<EmployeeRecordDTO> employees = dao.findByDepartment(deptNo, page);

            return Response.ok(employees).build();

        } finally {
            em.close();
        }
    }



    //Json format to paste in raw in Postman:

//    {
//        "empNo": 10002,
//            "newTitle": "Staff promoted",
//            "fromDate": "2025-12-01"
//    }

    @POST
    @Path("/promote")
    public Response promoteEmployee(PromotionRequestDTO request) {
        EntityManager em = emf.createEntityManager();
        try {
            EmployeeDAO dao = new EmployeeDAO(em);
            dao.promoteEmployee(request);
            return Response.ok("{\"message\":\"Promotion successful for empNo " + request.getEmpNo() + "\"}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"" + e.getMessage() + "\"}")
                    .build();
        } finally {
            em.close();
        }
    }


}