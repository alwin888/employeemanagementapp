package digicorp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.util.List;
import jakarta.persistence.*;


/**
 * Department entity.
 */
@Entity
@Table(name = "departments", schema = "employees")
public class Department {

    @Id
    @Column(name = "dept_no", length = 4)
    private String deptNo;

    @Column(name = "dept_name", length = 40)
    private String deptName;

    @OneToMany(mappedBy = "department")
    @JsonBackReference
    private List<DeptEmployee> employees;

    @OneToMany(mappedBy = "department")
    @JsonBackReference
    private List<DeptManager> managers;

    public Department() {}

    public Department(String deptNo, String deptName) {
        this.deptNo = deptNo;
        this.deptName = deptName;
    }

    public String getDeptNo() { return deptNo; }
    public void setDeptNo(String deptNo) { this.deptNo = deptNo; }

    public String getDeptName() { return deptName; }
    public void setDeptName(String deptName) { this.deptName = deptName; }

    public List<DeptEmployee> getEmployees() { return employees; }
    public void setEmployees(List<DeptEmployee> employees) { this.employees = employees; }
    @Override
    public String toString() {
        return "Department{" +
                "deptNo='" + deptNo + '\'' +
                ", deptName='" + deptName + '\'' +
                '}';
    }

}