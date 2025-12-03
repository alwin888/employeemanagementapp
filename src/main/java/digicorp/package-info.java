/**
 * Root package for the DigiCorp HR Management REST API application.
 *
 * <p>This package contains the main application configuration and serves as
 * the entry point for the JAX-RS framework. It initializes the REST API,
 * defines the base URI, and registers all resource classes.</p>
 *
 * <p>Responsibilities of this package include:</p>
 * <ul>
 *     <li>Defining the base path for all REST endpoints using {@link jakarta.ws.rs.ApplicationPath}</li>
 *     <li>Registering JAX-RS resource classes such as {@link digicorp.rest.EmployeeResource} and
 *         {@link digicorp.rest.DepartmentResource}</li>
 *     <li>Serving as the starting point for the REST application in the servlet container</li>
 * </ul>
 *
 * <p>Other packages in the DigiCorp application handle specific concerns:</p>
 * <ul>
 *     <li>{@link digicorp.rest} – REST resource classes and endpoints</li>
 *     <li>{@link digicorp.dto} – Data Transfer Objects (DTOs) for API communication</li>
 *     <li>{@link digicorp.entity} – JPA entity classes mapping to database tables</li>
 *     <li>{@link digicorp.services} – Business logic and data access objects (DAOs)</li>
 *     <li>{@link digicorp.util} – Utility classes for common tasks like JPA setup</li>
 * </ul>
 *
 * <p>Together, these packages form a modular, maintainable architecture for
 * exposing HR data over REST APIs.</p>
 */
package digicorp;