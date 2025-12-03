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
 * TitleHistory entity that represents the title table and it's relationship to others
 */
@Entity
@Table(name = "titles")
public class TitleHistory {

    /**
     * Composite primary key for SalaryHistoryId entity
     * Combines emp_no, title, fromDate
     */
    @EmbeddedId
    @JsonUnwrapped
    private TitleHistoryId id;

    /**
     * The employee associated with this title
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("empNo")
    @JoinColumn(name = "emp_no")
    @JsonBackReference("emp-titles")
    private Employee employee;

    /**
     * The end date of this title
     */
    @Column(name = "to_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate toDate;

    /**
     * default empty constructor required by JPA
     */
    public TitleHistory() {}

    /**
     * constructor with params to create a new TitleHistory record
     * @param id composite key with emp_no, title, fromDate
     * @param employee the employee who has this title
     * @param toDate end date of having this title
     */
    public TitleHistory(TitleHistoryId id, Employee employee, LocalDate toDate) {
        this.id = id;
        this.employee = employee;
        this.toDate = toDate;
    }

    /**
     * Returns composite primary key.
     * @return composite key object
     */
    public TitleHistoryId getId() { return id; }
    /**
     * Sets composite primary key.
     * @param id composite key to set
     */
    public void setId(TitleHistoryId id) { this.id = id; }

    /**
     * Returns employee who has this title
     * @return employee entity
     */
    public Employee getEmployee() { return employee; }
    /**
     * Sets employee who has this title
     * @param employee the employee to set
     */
    public void setEmployee(Employee employee) { this.employee = employee; }

    /**
     * Returns ending date for receiving this title
     * @return the ending date
     */
    public LocalDate getToDate() { return toDate; }
    /**
     * Sets ending date for receiving this title
     * @param toDate ending date to set
     */
    public void setToDate(LocalDate toDate) { this.toDate = toDate; }

    /**
     * returns object as a string
     */
    @Override
    public String toString() {
        return "Employee{" +
                ", employee='" + toDate + '\'' +
                '}';
    }
}
