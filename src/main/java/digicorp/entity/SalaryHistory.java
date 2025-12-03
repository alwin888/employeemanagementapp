/**
 * This package contains enities for the employee management
 */
package digicorp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * SalaryHistory entity that represents the salaries table and it's relationship to others
 */
@Entity
@Table(name = "salaries")
public class SalaryHistory {

    /**
     * The amount of salary
     */
    @Column(name="salary", precision = 11)
    private int salary;

    /**
     * Composite primary key for SalaryHistoryId entity
     * Combines employee number, fromDate
     */
    @EmbeddedId
    @JsonUnwrapped
    private SalaryHistoryId id;

    /**
     * The employee associated with this salary
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("empNo")
    @JoinColumn(name = "emp_no")
    @JsonBackReference("emp-salaries")
    private Employee employee;

    /**
     * The end date of this salary
     */
    @Column(name = "to_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private LocalDate toDate;

    /**
     * default empty constructor required by JPA
     */
    public SalaryHistory() {}

    /**
     * constructor with params to create a new Salaryhistory record
     * @param id composite key with employee number, fromDate
     * @param employee the employee who owns this salry
     * @param salary the amount of salary
     * @param toDate end date of receiving this salary
     */
    public SalaryHistory(SalaryHistoryId id, Employee employee, int salary, LocalDate toDate) {
        this.id = id;
        this.employee = employee;
        this.salary = salary;
        this.toDate = toDate;
    }

    /**
     * Returns composite primary key.
     * @return composite key object
     */
    public SalaryHistoryId getId() { return id; }
    /**
     * Sets composite primary key.
     * @param id composite key to set
     */
    public void setId(SalaryHistoryId id) { this.id = id; }

    /**
     * Returns employee who owns this salary
     * @return employee entity
     */
    public Employee getEmployee() { return employee; }
    /**
     * Sets employee who owns this salary
     * @param employee the employee to set
     */
    public void setEmployee(Employee employee) { this.employee = employee; }

    /**
     * Returns salary amount
     * @return salary salary amount as int
     */
    public int getSalary() { return salary; }
    /**
     * Sets asalary amount
     * @param salary salary amount as int
     */
    public void setSalary(int salary) { this.salary = salary; }

    /**
     * Returns ending date for receiving this salary
     * @return the ending date
     */
    public LocalDate getToDate() { return toDate; }
    /**
     * Sets ending date for receiving this salary
     * @param toDate ending date to set
     */
    public void setToDate(LocalDate toDate) { this.toDate = toDate; }
}
