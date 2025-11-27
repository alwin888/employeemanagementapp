package digicorp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Composite PK for salaries: (emp_no, from_date)
 */
@Embeddable
public class SalaryHistoryId implements Serializable {

    @Column(name = "emp_no")
    private int empNo;

    @Column(name = "from_date")
    private LocalDate fromDate;

    public SalaryHistoryId() {}
    public SalaryHistoryId(int empNo, LocalDate fromDate) { this.empNo = empNo; this.fromDate = fromDate; }

    public int getEmpNo() { return empNo; }
    public void setEmpNo(int empNo) { this.empNo = empNo; }

    public LocalDate getFromDate() { return fromDate; }
    public void setFromDate(LocalDate fromDate) { this.fromDate = fromDate; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SalaryHistoryId)) return false;
        SalaryHistoryId that = (SalaryHistoryId) o;
        return empNo == that.empNo && Objects.equals(fromDate, that.fromDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(empNo, fromDate);
    }
}