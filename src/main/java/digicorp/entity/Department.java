/**
 * This package contains enities for the employee management
 */
package digicorp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.util.List;
import jakarta.persistence.*;


/**
 * Department entity that represents the department table and it's relationship to others
 */
@Entity
@Table(name = "departments", schema = "employees")
public class Department {

    /**
     * primary key and unique identifier for department
     */
    @Id
    @Column(name = "dept_no", length = 4)
    private String deptNo;

     /**
     * name of department
     */
    @Column(name = "dept_name", length = 40)
    private String deptName;

    /**
     * list of employees in this department
     * on to many relationship mapped by the "department" field in DeptEmployee entity
     * JsonBackReference to prevent infinite loop
     */
    @OneToMany(mappedBy = "department")
    @JsonBackReference
    private List<DeptEmployee> employees;

    /**
     * list of managers in this department
     * on to many relationship mapped by the "department" field in DeptManager entity
     * JsonBackReference to prevent infinite loop
     */
    @OneToMany(mappedBy = "department")
    @JsonBackReference
    private List<DeptManager> managers;

    /**
     * default empty constructor required by JPA
     */
    public Department() {}

    /**
     * constructor with params to create a Department
     * @param deptNo the unique identifier for department
     * @param deptName the name of the department
     */
    public Department(String deptNo, String deptName) {
        this.deptNo = deptNo;
        this.deptName = deptName;
    }

    /**
     * returns department number.
     * @return department number as a String
     */
    public String getDeptNo() { return deptNo; }
    /**
     * Sets department number.
     * @param deptNo the department number to set
    */
    public void setDeptNo(String deptNo) { this.deptNo = deptNo; }

    /**
     * returns department name.
     * @return department name as a String
     */
    public String getDeptName() { return deptName; }
    /**
     * Sets department name.
     * @param deptNo the department name to set
    */
    public void setDeptName(String deptName) { this.deptName = deptName; }

    /**
     * returns list of employees in a department.
     * @return a list of employees
     */
    public List<DeptEmployee> getEmployees() { return employees; }
    /**
     * sets a list of employees to a department
     * @param a list of employees
    */
    public void setEmployees(List<DeptEmployee> employees) { this.employees = employees; }
    
    /**
     * returns a string representation of the Department object.
     * @return a string representation of the Department including the department number and name
     */
    @Override
    public String toString() {
        return "Department{" +
                "deptNo='" + deptNo + '\'' +
                ", deptName='" + deptName + '\'' +
                '}';
    }

}
