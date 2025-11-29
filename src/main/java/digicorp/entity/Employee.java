package digicorp.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @Column(name = "emp_no")
    private int empNo;


    @Column(name = "birth_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private LocalDate birthDate;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "gender")
    private String gender;

    @Column(name = "hire_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private LocalDate hireDate;

    // One employee can have many title records
    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER)
    @JsonManagedReference("emp-titles")
    private List<TitleHistory> titleHistory;

    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER)
    @JsonManagedReference("emp-salaries")
    private List<SalaryHistory> salaryHistory;

    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER)
    @JsonManagedReference("emp-departments")
    private List<DeptEmployee> departments;

    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER)
    @JsonManagedReference("emp-managers")
    private List<DeptManager> managedDepartments;

    // Getters and setters…

    public Employee() {}

    public Employee(int empNo) {
        this.empNo = empNo;
    }

    // getters + setters
    public int getEmpNo() { return empNo; }
    public void setEmpNo(int empNo) { this.empNo = empNo; }

    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public LocalDate getHireDate() { return hireDate; }
    public void setHireDate(LocalDate hireDate) { this.hireDate = hireDate; }

    public List<DeptEmployee> getDepartments() { return departments; }
    public void setDepartments(List<DeptEmployee> departments) { this.departments = departments; }

    public List<DeptManager> getManagedDepartments() { return managedDepartments; }
    public void setManagerHistory(List<DeptManager> managedDepartments) { this.managedDepartments = managedDepartments; }

    public List<SalaryHistory> getSalaryHistory() { return salaryHistory; }
    public void setSalaryHistory(List<SalaryHistory> salaryHistory) { this.salaryHistory = salaryHistory; }

    public List<TitleHistory> getTitleHistory() { return titleHistory; }
    public void setTitleHistory(List<TitleHistory> titleHistory) { this.titleHistory = titleHistory; }

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