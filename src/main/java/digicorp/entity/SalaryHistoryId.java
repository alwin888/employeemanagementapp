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

    @Column(name = "emp_no")
    @JsonIgnore
    private int empNo;

    @Column(name = "from_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
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