/**
 * Provides Data Transfer Object (DTO) classes used for moving structured data
 * between the API layer and service/business layers of the DigiCorp application.
 * <p>
 * DTOs in this package:
 * <ul>
 *     <li>Encapsulate data sent to and from REST endpoints</li>
 *     <li>Ensure a clean separation between internal entity models and public API models</li>
 *     <li>Control JSON serialization/deserialization formats using Jackson annotations</li>
 *     <li>Prevent accidental exposure of database entities or unnecessary internal fields</li>
 * </ul>
 *
 * <h2>Included DTOs</h2>
 * <ul>
 *     <li>{@link digicorp.dto.EmployeeRecordDTO} – Simplified employee record returned to clients</li>
 *     <li>{@link digicorp.dto.PromotionRequestDTO} – Input payload for employee promotion requests</li>
 * </ul>
 *
 * <p>
 * Overall, this package defines the data structures that form the contract
 * between external clients and the DigiCorp HR system.
 * </p>
 */
package digicorp.dto;