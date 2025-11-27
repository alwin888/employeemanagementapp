package digicorp.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Title history (titles table). Composite PK (emp_no, title, from_date).
 */
@Entity
@Table(name = "titles")
public class TitleHistory {

    @EmbeddedId
    private TitleHistoryId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("empNo")
    @JoinColumn(name = "emp_no")
    private Employee employee;

    @Column(name = "to_date")
    private LocalDate toDate;

    public TitleHistory() {}

    public TitleHistory(TitleHistoryId id, Employee employee, LocalDate toDate) {
        this.id = id;
        this.employee = employee;
        this.toDate = toDate;
    }

    // getters + setters
    public TitleHistoryId getId() { return id; }
    public void setId(TitleHistoryId id) { this.id = id; }

    public Employee getEmployee() { return employee; }
    public void setEmployee(Employee employee) { this.employee = employee; }

    public LocalDate getToDate() { return toDate; }
    public void setToDate(LocalDate toDate) { this.toDate = toDate; }
}