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
 * Department employee entity that represents the deptEmp table and it's relationship to others
 */
@Entity
@Table(name = "dept_emp")
public class DeptEmployee {

    /**
     * Composite primary key for DeptEmployee entity
     * Combines employee number, department number
     */
    @EmbeddedId
    @JsonUnwrapped
    private DeptEmployeeId id;

    /**
     * The employee associated with this department
     */
    @ManyToOne
    @MapsId("empNo")
    @JoinColumn(name = "emp_no")
    @JsonBackReference("emp-departments")
    private Employee employee;

    /**
     * The dapartment where the employee is assigned to
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonUnwrapped
    @MapsId("deptNo")
    @JoinColumn(name = "dept_no")
    private Department department;

    /**
     * Start date of the department assignment
     */
    @Column(name = "from_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private LocalDate fromDate;

    /**
     * end date of the department assignment, 9999-01-01 means ongoing
     */
    @Column(name = "to_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private LocalDate toDate;

    /**
     * default empty constructor required by JPA
     */
    public DeptEmployee() {}
    
    /**
     * constructor with params to create a new Department employee record
     * @param id composite key with employee number, department number
     * @param employee the assigned employee
     * @param department the assigned department
     * @param fromDate starting date
     * @param toDate end date
     */
    public DeptEmployee(DeptEmployeeId id, Employee employee, Department department, LocalDate fromDate, LocalDate toDate) {
        this.id = id;
        this.employee = employee;
        this.department = department;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    /**
     * Returns composite primary key.
     * @return composite key object
     */
    public DeptEmployeeId getId() { return id; }
    /**
     * Sets composite primary key.
     * @param id composite key to set
     */
    public void setId(DeptEmployeeId id) { this.id = id; }

    /**
     * Returns assigned employee.
     * @return employee entity
     */
    public Employee getEmployee() { return employee; }
    /**
     * Sets assigned employee.
     * @param employee the employee to set
     */
    public void setEmployee(Employee employee) { this.employee = employee; }

    /**
     * Returns assigned department.
     * @return department entity
     */
    public Department getDepartment() { return department; }
    /**
     * Sets assigned department.
     * @param department the department to set
     */
    public void setDepartment(Department department) { this.department = department; }

    /**
     * Returns assignment start date.
     * @return the start date
     */
    public LocalDate getFromDate() { return fromDate; }
    /**
     * Sets assigned start date.
     * @param fromDate start date to set
     */
    public void setFromDate(LocalDate fromDate) { this.fromDate = fromDate; }

    /**
     * Returns assignment end date.
     * @return the end date, 9999-01-01 means ongoing
     */
    public LocalDate getToDate() { return toDate; }
    /**
     * Sets assignment end date.
     * @param toDate the end date to set with 9999-01-01 as ongoing
     */
    public void setToDate(LocalDate toDate) { this.toDate = toDate; }
}
