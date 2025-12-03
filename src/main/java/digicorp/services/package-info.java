/**
 * Service layer and Data Access Objects (DAOs) for the DigiCorp application.
 *
 * <p>This package provides classes responsible for encapsulating the business
 * logic and database interactions for the HR Management System. DAOs handle
 * CRUD operations, complex queries, and history tracking for entities such as
 * {@link digicorp.entity.Employee} and {@link digicorp.entity.Department}.</p>
 *
 * <p>Responsibilities of classes in this package include:</p>
 * <ul>
 *     <li>Performing database operations using JPA {@link jakarta.persistence.EntityManager}</li>
 *     <li>Providing methods for fetching entities with related history eagerly loaded</li>
 *     <li>Filtering and paginating results, e.g., employees by department</li>
 *     <li>Handling complex transactional operations, such as employee promotions</li>
 *     <li>Validating business rules and entity relationships before persisting changes</li>
 * </ul>
 *
 * <p>Classes in this package should be used by REST resources or other service layers
 * to keep the persistence logic isolated and maintain a clean separation of concerns.</p>
 */
package digicorp.services;