package digicorp.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * dept_emp table maps employees to departments over time.
 * Composite PK: (emp_no, dept_no)
 */
@Entity
@Table(name = "dept_emp")
public class DeptEmployee {

    @EmbeddedId
    private DeptEmployeeId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("empNo")
    @JoinColumn(name = "emp_no")
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("deptNo")
    @JoinColumn(name = "dept_no")
    private Department department;

    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "to_date")
    private LocalDate toDate;

    public DeptEmployee() {}

    public DeptEmployee(DeptEmployeeId id, Employee employee, Department department, LocalDate fromDate, LocalDate toDate) {
        this.id = id;
        this.employee = employee;
        this.department = department;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    // getters + setters
    public DeptEmployeeId getId() { return id; }
    public void setId(DeptEmployeeId id) { this.id = id; }

    public Employee getEmployee() { return employee; }
    public void setEmployee(Employee employee) { this.employee = employee; }

    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }

    public LocalDate getFromDate() { return fromDate; }
    public void setFromDate(LocalDate fromDate) { this.fromDate = fromDate; }

    public LocalDate getToDate() { return toDate; }
    public void setToDate(LocalDate toDate) { this.toDate = toDate; }
}