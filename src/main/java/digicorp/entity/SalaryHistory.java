package digicorp.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Salary history: composite PK (emp_no, from_date)
 */
@Entity
@Table(name = "salaries")
public class SalaryHistory {

    @EmbeddedId
    private SalaryHistoryId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("empNo")
    @JoinColumn(name = "emp_no")
    private Employee employee;

    private int salary;

    @Column(name = "to_date")
    private LocalDate toDate;

    public SalaryHistory() {}

    public SalaryHistory(SalaryHistoryId id, Employee employee, int salary, LocalDate toDate) {
        this.id = id;
        this.employee = employee;
        this.salary = salary;
        this.toDate = toDate;
    }

    // getters + setters
    public SalaryHistoryId getId() { return id; }
    public void setId(SalaryHistoryId id) { this.id = id; }

    public Employee getEmployee() { return employee; }
    public void setEmployee(Employee employee) { this.employee = employee; }

    public int getSalary() { return salary; }
    public void setSalary(int salary) { this.salary = salary; }

    public LocalDate getToDate() { return toDate; }
    public void setToDate(LocalDate toDate) { this.toDate = toDate; }
}