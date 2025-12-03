/**
 * Provides the JPA entity classes for the DigiCorp HR Management System.
 * <p>
 * This package contains classes representing the core database entities, including:
 * <ul>
 *     <li>{@link digicorp.entity.Employee} - Represents an employee.</li>
 *     <li>{@link digicorp.entity.Department} - Represents a department.</li>
 *     <li>{@link digicorp.entity.DeptEmployee} - Associates employees with departments.</li>
 *     <li>{@link digicorp.entity.DeptManager} - Tracks department managers.</li>
 *     <li>{@link digicorp.entity.TitleHistory} - Tracks employee title history.</li>
 *     <li>{@link digicorp.entity.SalaryHistory} - Tracks employee salary history.</li>
 *     <li>Any other entity classes related to employee, department, or HR data.</li>
 * </ul>
 * <p>
 * All entities are annotated with Jakarta Persistence (JPA) annotations and are used
 * in combination with {@link jakarta.persistence.EntityManager} for database operations.
 * <p>
 * This package is typically used by DAO/service classes in {@code digicorp.services}
 * and REST endpoints in {@code digicorp.rest}.
 */
package digicorp.entity;