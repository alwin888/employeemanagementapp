/**
 * This package contains enities for the employee management
 */
package digicorp.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * employee entity that represents the employees table and it's relationship to others
 */
@Entity
@Table(name = "employees")
public class Employee {

    /**
     * primary key and unique identifier for employees
     */
    @Id
    @Column(name = "emp_no")
    private int empNo;

    /**
     * birth date of employee
     */
    @Column(name = "birth_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private LocalDate birthDate;

    /**
     * first name of employee
     */
    @Column(name = "first_name")
    private String firstName;

    /**
     * last name of employee
     */
    @Column(name = "last_name")
    private String lastName;

    /**
     * gender of employee
     */
    @Column(name = "gender")
    private String gender;

    /**
     * hire date of employee
     */
    @Column(name = "hire_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private LocalDate hireDate;

    /**
     * list of titles that this employee has/had
     * on to many relationship mapped by the "employee" field in TitleHistory entity
     */
    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER)
    @JsonManagedReference("emp-titles")
    private List<TitleHistory> titleHistory;

    /**
     * list of titles that this employee has/had
     * on to many relationship mapped by the "employee" field in SalaryHistory entity
     */
    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER)
    @JsonManagedReference("emp-salaries")
    private List<SalaryHistory> salaryHistory;

    /**
     * list of department that this employee has been/is in
     * on to many relationship mapped by the "employee" field in Department entity
     */
    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER)
    @JsonManagedReference("emp-departments")
    private List<DeptEmployee> departments;

    /**
     * list of department that this employee has been/is a manager in
     * on to many relationship mapped by the "employee" field in DeptManager entity
     */
    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER)
    @JsonManagedReference("emp-managers")
    private List<DeptManager> managedDepartments;

    /**
     * default empty constructor required by JPA
     */
    public Employee() {}

    /**
     * constructor with params to create a employee
     * @param empNo the unique identifier for employee
     */
    public Employee(int empNo) {
        this.empNo = empNo;
    }

    /**
     * returns employee number
     * @return employee number as an int
     */
    public int getEmpNo() { return empNo; }
    /**
     * Sets employee number
     * @param empNo the employee number to set
    */
    public void setEmpNo(int empNo) { this.empNo = empNo; }

    /**
     * returns employee birth date
     * @return employee birth date as LocalDate
     */
    public LocalDate getBirthDate() { return birthDate; }
     /**
     * Sets employee birth date
     * @param birthDate the employee birth date to set
     */
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }

     /**
     * returns employee first name
     * @return employee first name as a String
     */
    public String getFirstName() { return firstName; }
    /**
     * Sets employee first name
     * @param firstName employees's first name to set
     */
    public void setFirstName(String firstName) { this.firstName = firstName; }

    /**
     * returns employee last name
     * @return employee last name as a String
     */
    public String getLastName() { return lastName; }
    /**
     * Sets employee last name
     * @param lastName the employee last name to set
    */
    public void setLastName(String lastName) { this.lastName = lastName; }

    /**
     * returns employee gender
     * @return employee gender as a String
     */
    public String getGender() { return gender; }
    /**
     * Sets employee gender
     * @param gender the employee gender to set
    */
    public void setGender(String gender) { this.gender = gender; }

    /**
     * returns employee hire date
     * @return employee hire date as LocalDate
     */
    public LocalDate getHireDate() { return hireDate; }
    /**
     * Sets employee hire date
     * @param hireDate the employee hire date to set
    */
    public void setHireDate(LocalDate hireDate) { this.hireDate = hireDate; }

    /**
     * returns list of employees in a department.
     * @return a list of employees
     */
    public List<DeptEmployee> getDepartments() { return departments; }
    /**
     * sets a list of employees to a department
     * @param departments a list of employees
    */
    public void setDepartments(List<DeptEmployee> departments) { this.departments = departments; }

    /**
     * returns a list of managers in a department
     * @return a list of managers in a department
     */
    public List<DeptManager> getManagedDepartments() { return managedDepartments; }
    /**
     * sets a list of managers to a department
     * @param managedDepartments a list of managers
    */
    public void setManagerHistory(List<DeptManager> managedDepartments) { this.managedDepartments = managedDepartments; }

    /**
     * returns a list of employee's salaries
     * @return a list of employee's salaries
     */
    public List<SalaryHistory> getSalaryHistory() { return salaryHistory; }
    /**
     * sets a list of employee's salaries 
     * @param salaryHistory a list of salaries
    */
    public void setSalaryHistory(List<SalaryHistory> salaryHistory) { this.salaryHistory = salaryHistory; }

    /**
     * returns a list of employee's titles
     * @return a list of employee's titles
     */
    public List<TitleHistory> getTitleHistory() { return titleHistory; }
    /**
     * sets a list of employee's titles 
     * @param titleHistory a list of employee's titles
    */
    public void setTitleHistory(List<TitleHistory> titleHistory) { this.titleHistory = titleHistory; }

    /**
     * returns a string representation of the Employee object.
     * @return a string representation of the Employee 
     */
    @Override
    public String toString() {
        return "Employee{" +
                "employeeNo='" + empNo + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender='" + gender + '\'' +
                ", hireDate='" + hireDate + '\'' +
                ", title='" + titleHistory + '\'' +
                ", salary='" + salaryHistory + '\'' +
                ", departments='" + departments + '\'' +
                ", managers='" + managedDepartments + '\'' +
                '}';
    }


}
