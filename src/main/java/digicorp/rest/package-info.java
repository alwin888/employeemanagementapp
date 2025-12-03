/**
 * Provides the RESTful API endpoints for the DigiCorp HR Management System.
 * <p>
 * Classes in this package expose HTTP resources using JAX-RS annotations and
 * serve as the primary interface for clients interacting with employee and
 * department data. These endpoints delegate business logic to service-layer
 * components and return JSON-formatted responses suitable for frontend
 * applications or external integrations.
 *
 * <h2>Responsibilities</h2>
 * <ul>
 *     <li>Handle incoming HTTP requests and query parameters</li>
 *     <li>Perform lightweight validation of input values</li>
 *     <li>Manage request-scoped JPA {@link jakarta.persistence.EntityManager} instances</li>
 *     <li>Invoke service/DAO classes to execute business operations</li>
 *     <li>Serialize entities and DTOs into JSON responses</li>
 *     <li>Return meaningful HTTP status codes for success and error cases</li>
 * </ul>
 *
 * <h2>Included REST Resources</h2>
 * <ul>
 *     <li>{@link digicorp.rest.EmployeeResource} — employee lookup, filtering, and promotion operations</li>
 *     <li>{@link digicorp.rest.DepartmentResource} — department listing and metadata endpoints</li>
 * </ul>
 *
 * <p>
 * Overall, this package defines the public-facing REST API layer of the DigiCorp
 * application, connecting HTTP requests to the underlying business logic and data models.
 * </p>
 */
package digicorp.rest;