package digicorp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Composite PK for titles: (emp_no, title, from_date)
 */
@Embeddable
public class TitleHistoryId implements Serializable {

    @Column(name = "emp_no")
    private int empNo;

    @Column(name = "title")
    private String title;

    @Column(name = "from_date")
    private LocalDate fromDate;

    public TitleHistoryId() {}
    public TitleHistoryId(int empNo, String title, LocalDate fromDate) {
        this.empNo = empNo; this.title = title; this.fromDate = fromDate;
    }

    // getters/setters, equals, hashCode
    public int getEmpNo() { return empNo; }
    public void setEmpNo(int empNo) { this.empNo = empNo; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public LocalDate getFromDate() { return fromDate; }
    public void setFromDate(LocalDate fromDate) { this.fromDate = fromDate; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TitleHistoryId)) return false;
        TitleHistoryId that = (TitleHistoryId) o;
        return empNo == that.empNo &&
                Objects.equals(title, that.title) &&
                Objects.equals(fromDate, that.fromDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(empNo, title, fromDate);
    }
}