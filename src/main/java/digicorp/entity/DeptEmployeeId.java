/**
 * This package contains enities for the employee management
 */
package digicorp.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * Composite PK for dept_emp entity: (emp_no, dept_no)
 */
@Embeddable
public class DeptEmployeeId implements Serializable {

    /**
     * Employee number: foreign key to employees table.
     */
    @Column(name = "emp_no")
    @JsonIgnore
    private int empNo;

    /**
     * Department number: foreign key to departments table.
     */
    @Column(name = "dept_no", length = 4)
    @JsonIgnore
    private String deptNo;

    /**
     * Default constructor required by JPA.
     */
    public DeptEmployeeId() {}
    /**
     * Creates a composite key
     * @param empNo the employee number
     * @param deptNo the department number
     */
    public DeptEmployeeId(int empNo, String deptNo) { this.empNo = empNo; this.deptNo = deptNo; }

    /**
     * Returns employee number
     * @return employee number as an int
     */
    public int getEmpNo() { return empNo; }
    /**
     * Sets employee number
     * @param empNo the employee number to set
     */
    public void setEmpNo(int empNo) { this.empNo = empNo; }

    /**
     * Returns department number
     * @return department number as a String
     */
    public String getDeptNo() { return deptNo; }
    /**
     * Sets department number
     * @param septNo the department number to set
     */
    public void setDeptNo(String deptNo) { this.deptNo = deptNo; }

    /**
     * Compares this composite key with another object for equality.
     * @param o the object to compare with
     * @return true if both objects have same employee and department numbers
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeptEmployeeId)) return false;
        DeptEmployeeId that = (DeptEmployeeId) o;
        return empNo == that.empNo && Objects.equals(deptNo, that.deptNo);
    }

    /**
    * Returns a hash code value for this composite key.
    * @return a hash code value for this object
    */
    @Override
    public int hashCode() {
        return Objects.hash(empNo, deptNo);
    }
}
