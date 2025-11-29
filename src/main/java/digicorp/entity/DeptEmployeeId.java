package digicorp.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * Composite PK for dept_emp: (emp_no, dept_no)
 */
@Embeddable
public class DeptEmployeeId implements Serializable {

    @Column(name = "emp_no")
    @JsonIgnore
    private int empNo;

    @Column(name = "dept_no", length = 4)
    @JsonIgnore
    private String deptNo;

    public DeptEmployeeId() {}
    public DeptEmployeeId(int empNo, String deptNo) { this.empNo = empNo; this.deptNo = deptNo; }

    public int getEmpNo() { return empNo; }
    public void setEmpNo(int empNo) { this.empNo = empNo; }

    public String getDeptNo() { return deptNo; }
    public void setDeptNo(String deptNo) { this.deptNo = deptNo; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeptEmployeeId)) return false;
        DeptEmployeeId that = (DeptEmployeeId) o;
        return empNo == that.empNo && Objects.equals(deptNo, that.deptNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(empNo, deptNo);
    }
}