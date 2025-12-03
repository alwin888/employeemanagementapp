package digicorp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) representing a request to promote an employee.
 * <p>
 * This DTO is typically used for incoming JSON payloads that specify
 * updated job details such as new title, salary, department, and whether
 * the employee should be assigned a managerial role.
 */
public class PromotionRequestDTO {
    /** The unique employee number identifying the employee to be promoted. */
    private int empNo;
    /** The new job title assigned to the employee after the promotion. */
    private String newTitle;
    /**
     * The effective date from which the promotion applies.
     * <p>
     * Serialized/deserialized in {@code yyyy-MM-dd} format.
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fromDate;
    /** The new salary amount associated with the promotion. */
    private int salary;
    /** The department number to which the employee will be reassigned, if applicable. */
    private String deptNo;
    /**
     * Flag indicating whether the promoted employee should have managerial status.
     * <p>
     * Exposed in JSON under the property name {@code manager}.
     */
    @JsonProperty("manager")
    private boolean manager;

    /**
     * Default no-argument constructor required for JSON deserialization.
     */
    public PromotionRequestDTO() {}
    /**
     * Returns the employee number.
     *
     * @return the employee's unique ID
     */
    public int getEmpNo() { return empNo; }
    /**
     * Sets the employee number.
     *
     * @param empNo the employee's unique ID
     */
    public void setEmpNo(int empNo) { this.empNo = empNo; }
    /**
     * Returns the employee title.
     *
     * @return the employee's job title
     */
    public String getNewTitle() { return newTitle; }
    /**
     * Sets the employee title.
     *
     * @param newTitle the employee's title
     */
    public void setNewTitle(String newTitle) { this.newTitle = newTitle; }
    /**
     * Returns the date from which the promotion is effective.
     *
     * @return the effective date of promotion
     */
    public LocalDate getFromDate() { return fromDate; }
    /**
     * Sets the effective date for the promotion.
     *
     * @param fromDate the promotion effective date
     */
    public void setFromDate(LocalDate fromDate) { this.fromDate = fromDate; }
    /**
     * Returns the new salary value.
     *
     * @return the updated salary
     */
    public int getNewSalary() { return salary; }
    /**
     * Sets the new salary value.
     *
     * @param salary employee's salary
     */
    public void setSalary(int salary) { this.salary = salary; }
    /**
     * Returns the new department number, if the promotion involves a transfer.
     *
     * @return the updated department number
     */
    public String getNewDeptNo() { return deptNo; }
    /**
     * Sets the new department number.
     *
     * @param deptNo the updated department number
     */
    public void setDeptNo(String deptNo) { this.deptNo = deptNo; }
    /**
     * Returns whether the employee should be assigned managerial status.
     *
     * @return {@code true} if the employee becomes a manager; otherwise {@code false}
     */
    public boolean isManager() { return manager; }
    /**
     * Sets whether the employee is assigned a managerial role.
     *
     * @param manager {@code true} for managerial status; {@code false} otherwise
     */
    public void setManager(boolean manager) { this.manager = manager; }


}