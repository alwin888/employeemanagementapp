package digicorp.dto;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Data Transfer Object (DTO) representing a simplified view of an employee record.
 * <p>
 * This class is typically used to send employee data to clients in a
 * controlled and structured format. It includes basic personal information
 * and the employee's hire date.
 */
public class EmployeeRecordDTO {
    /** The employee's unique identification number. */
    private int empNo;
    /** The employee's given name. */
    private String firstName;
    /** The employee's family name or surname. */
    private String lastName;
    /**
     * The date the employee was hired.
     * <p>
     * The {@code @JsonFormat} annotation ensures that when serialized to JSON,
     * the date is output in {@code yyyy-MM-dd} format.
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate hireDate;

    /**
     * Constructs a new {@code EmployeeRecordDTO} with the provided details.
     *
     * @param empNo     the employee number
     * @param firstName the employee's first name
     * @param lastName  the employee's last name
     * @param hireDate  the date the employee was hired
     */
    public EmployeeRecordDTO(int empNo, String firstName, String lastName, LocalDate hireDate) {
        this.empNo = empNo;
        this.firstName = firstName;
        this.lastName = lastName;
        this.hireDate = hireDate;
    }
    /**
     * Returns the employee's unique ID.
     *
     * @return the employee number
     */
    public int getEmpNo() { return empNo; }
    /**
     * Returns the employee's first name.
     *
     * @return the first name
     */
    public String getFirstName() { return firstName; }
    /**
     * Returns the employee's last name.
     *
     * @return the last name
     */
    public String getLastName() { return lastName; }
    /**
     * Returns the date the employee was hired.
     *
     * @return the hire date
     */
    public LocalDate getHireDate() { return hireDate; }
}