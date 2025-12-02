package digicorp.rest;
import digicorp.dto.*;
import digicorp.entity.Employee;
import digicorp.services.EmployeeDAO;
import digicorp.util.JPAUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
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

    private static final EntityManagerFactory emf =
            JPAUtil.getEMF();

    @GET
    @Path("/{empNo}")
    public Response getEmployee(@PathParam("empNo") String empNoStr) {

        // Edge Case 1: validate empNo format
        if (empNoStr == null || !empNoStr.matches("\\d+")) {
            Map<String, String> err = new HashMap<>();
            err.put("error", "Employee number must be a positive integer.");
            return Response.status(Response.Status.BAD_REQUEST).entity(err).build();
        }

        int empNo = Integer.parseInt(empNoStr);

        EntityManager em = emf.createEntityManager();
        try {
            EmployeeDAO dao = new EmployeeDAO(em);
            Employee e = dao.findByIdWithHistory(empNo);

            if (e == null) {
                Map<String, String> err = new HashMap<>();
                err.put("error", "Employee " + empNo + " not found");
                return Response.status(Response.Status.NOT_FOUND).entity(err).build();
            }

            return Response.ok(e).build();

        } finally {
            em.close();
        }
    }

    @GET
    @Path("/by-department")
    public Response getEmployeesByDepartment(
            @QueryParam("deptNo") String deptNo,
            @QueryParam("page") @DefaultValue("1") int page
    ) {

        // Validate deptNo exists
        if (deptNo == null || deptNo.trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Query parameter 'deptNo' is required\"}")
                    .build();
        }

        deptNo = deptNo.trim();

        // Validate format: d### (anycase d + 3 digits)
        if (!deptNo.matches("(?i)^d\\d{3}$")) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Invalid deptNo format. Expected format 'd001', 'd002', ...\"}")
                    .build();
        }

        EntityManager em = emf.createEntityManager();
        try {
            // Verify department exists
            Long count = em.createQuery(
                            "SELECT COUNT(d) FROM Department d WHERE d.deptNo = :deptNo",
                            Long.class
                    )
                    .setParameter("deptNo", deptNo)
                    .getSingleResult();

            if (count == 0) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"error\":\"Department " + deptNo + " not found\"}")
                        .build();
            }

            // Fetch paged results
            EmployeeDAO dao = new EmployeeDAO(em);
            List<EmployeeRecordDTO> employees = dao.findByDepartment(deptNo, page);

            return Response.ok(employees).build();

        } finally {
            em.close();
        }
    }



    //Json format to paste in raw in Postman:

//    {
//        "empNo": 10004,
//            "newTitle": "Manager",
//            "fromDate": "2025-12-11",
//            "salary" : 1000278,
//            "deptNo" : "d001",
//            "manager" : true
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