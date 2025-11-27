package digicorp.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "dept_manager")
public class DeptManager {

    @EmbeddedId
    private DeptManagerId id;

    @ManyToOne
    @MapsId("empNo")   // maps PK column emp_no
    @JoinColumn(name = "emp_no")
    private Employee employee;

    @ManyToOne
    @MapsId("deptNo")  // maps PK column dept_no
    @JoinColumn(name = "dept_no")
    private Department department;

    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "to_date")
    private LocalDate toDate;

    public DeptManager() {}

    public DeptManager(DeptManagerId id, LocalDate fromDate, LocalDate toDate) {
        this.id = id;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public DeptManagerId getId() {
        return id;
    }

    public void setId(DeptManagerId id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }
}