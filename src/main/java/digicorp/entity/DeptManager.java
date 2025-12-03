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
 * Department manager entity that represents the deptManager table and it's relationship to others
 */
@Entity
@Table(name = "dept_manager")
public class DeptManager {

    /**
     * Composite primary key for DeptManager entity
     * Combines employee number, department number
     */
    @EmbeddedId
    @JsonUnwrapped
    private DeptManagerId id;

    /**
     * The employee who is a manager associated with this department
     */
    @ManyToOne
    @MapsId("empNo")
    @JoinColumn(name = "emp_no")
    @JsonBackReference("emp-managers")
    private Employee employee;

    /**
     * The dapartment where the manager is assigned to
     */
    @ManyToOne
    @JsonUnwrapped
    @MapsId("deptNo")  // maps PK column dept_no
    @JoinColumn(name = "dept_no")
    private Department department;

    /**
     * Start date of the when the employee is a manager in the department he/she is assigned to 
     */
    @Column(name = "from_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private LocalDate fromDate;

    /**
     * end date of the manageer's assignment to the department, 9999-01-01 means ongoing
     */
    @Column(name = "to_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private LocalDate toDate;

    /**
     * default empty constructor required by JPA
     */
    public DeptManager() {}

    /**
     * constructor with params to create a new department manager record
     * @param id composite key with employee number, department number
     * @param fromDate starting date
     * @param toDate end date
     */
    public DeptManager(DeptManagerId id, LocalDate fromDate, LocalDate toDate) {
        this.id = id;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    /**
     * Returns composite primary key.
     * @return composite key object
     */
    public DeptManagerId getId() {
        return id;
    }
    /**
     * Sets composite primary key.
     * @param id composite key to set
     */
    public void setId(DeptManagerId id) {
        this.id = id;
    }

    /**
     * Returns assigned employee.
     * @return employee entity
     */
    public Employee getEmployee() {
        return employee;
    }
    /**
     * Sets assigned employee.
     * @param employee the employee to set
     */
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    /**
     * Returns assigned department.
     * @return department entity
     */
    public Department getDepartment() {
        return department;
    }
    /**
     * Sets assigned department.
     * @param department the department to set
     */
    public void setDepartment(Department department) {
        this.department = department;
    }

    /**
     * Returns assignment start date.
     * @return the start date
     */
    public LocalDate getFromDate() {
        return fromDate;
    }
    /**
     * Sets assigned start date.
     * @param fromDate start date to set
     */
    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    /**
     * Returns assignment end date.
     * @return the end date, 9999-01-01 means ongoing
     */
    public LocalDate getToDate() {
        return toDate;
    }
    /**
     * Sets assignment end date.
     * @param toDate the end date to set with 9999-01-01 as ongoing
     */
    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }
}
