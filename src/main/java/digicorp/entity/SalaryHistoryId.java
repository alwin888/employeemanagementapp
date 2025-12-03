/**
 * This package contains enities for the employee management
 */
package digicorp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Composite PK for salaries: (emp_no, from_date)
 */
@Embeddable
public class SalaryHistoryId implements Serializable {

    /**
     * Employee number: foreign key to employees table.
     */
    @Column(name = "emp_no")
    @JsonIgnore
    private int empNo;

    /**
     * when the slary started
     */
    @Column(name = "from_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private LocalDate fromDate;

    /**
     * Default constructor required by JPA.
     */
    public SalaryHistoryId() {}
    /**
     * Creates a composite key
     * @param empNo the employee number
     * @param fromDate the from_date
     */
    public SalaryHistoryId(int empNo, LocalDate fromDate) { this.empNo = empNo; this.fromDate = fromDate; }

    /**
     * Returns employee number
     * @return employee number as an int
     */
    public int getEmpNo() { return empNo; }
    /**
     * Sets employee number
     * @param empNo the employee number to set
     */
    public void setEmpNo(int empNo) { this.empNo = empNo; }

    /**
     * Returns the salary from date
     * @return the salary from date as a LocalDate
     */
    public LocalDate getFromDate() { return fromDate; }
    /**
     * Sets the salary from date
     * @param fromDate the the salary from date to set
     */
    public void setFromDate(LocalDate fromDate) { this.fromDate = fromDate; }

    /**
     * Compares this composite key with another object for equality.
     * @param o the object to compare with
     * @return true if both objects have same employee and department numbers
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SalaryHistoryId)) return false;
        SalaryHistoryId that = (SalaryHistoryId) o;
        return empNo == that.empNo && Objects.equals(fromDate, that.fromDate);
    }

    /**
    * Returns a hash code value for this composite key.
    * @return a hash code value for this object
    */
    @Override
    public int hashCode() {
        return Objects.hash(empNo, fromDate);
    }
}
