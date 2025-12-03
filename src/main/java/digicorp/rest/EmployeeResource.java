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
 * REST resource exposing employee-related endpoints for DigiCorp HR operations.
 * <p>
 * This resource provides:
 * <ul>
 *     <li>Lookup of employees by employee number</li>
 *     <li>Lookup of employees by department with pagination</li>
 *     <li>Promotion operations (title, salary, department, manager status)</li>
 * </ul>
 * All responses are produced in JSON format.
 */
@Path("/employees")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmployeeResource {
    /**
     * Singleton {@link EntityManagerFactory} used to create
     * {@link EntityManager} instances for request processing.
     */
    private static final EntityManagerFactory emf =
            JPAUtil.getEMF();
    /**
     * Retrieves a single employee along with their job and salary history.
     * <p>
     * Validation rules:
     * <ul>
     *     <li>{@code empNo} must be a positive integer</li>
     *     <li>Returns 404 if the employee does not exist</li>
     * </ul>
     *
     * @param empNoStr the employee number as a string path parameter
     * @return a {@link Response} containing the employee record or an error message
     */
    @GET
    @Path("/{empNo}")
    public Response getEmployee(@PathParam("empNo") String empNoStr) {
        // Edge Case: validate empNo format
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
    /**
     * Retrieves employees belonging to a given department, optionally paged.
     * <p>
     * Validation rules:
     * <ul>
     *     <li>{@code deptNo} is required</li>
     *     <li>{@code deptNo} must follow the format {@code d###} (case-insensitive)</li>
     *     <li>Returns 404 if the department does not exist</li>
     * </ul>
     *
     * @param deptNo the department number (e.g., d001)
     * @param page the page number for pagination; defaults to 1
     * @return a paginated list of {@link EmployeeRecordDTO} or an error response
     */
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
    /**
     * Promotes an employee by updating their title, salary, department, and/or
     * managerial status based on the provided request payload.
     * <p>
     * Expected JSON format:
     * <pre>
     * {
     *   "empNo": 10004,
     *   "newTitle": "Manager",
     *   "fromDate": "2025-12-11",
     *   "salary": 1000278,
     *   "deptNo": "d001",
     *   "manager": true
     * }
     * </pre>
     * <p>
     * Validation and business rules are handled in {@link EmployeeDAO#promoteEmployee(PromotionRequestDTO)}.
     *
     * @param request the promotion details sent in the JSON request body
     * @return 200 OK on success, or 400 BAD REQUEST if validation or updates fail
     */
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