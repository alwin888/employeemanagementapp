package digicorp.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Employee entity - contains stable employee info and one-to-many relations
 * to history tables that record changes over time (salary, title, dept membership, manager).
 */
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @Column(name = "emp_no")
    private int empNo;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String gender;

    @Column(name = "hire_date")
    private LocalDate hireDate;

    // Dept membership history (dept_emp)
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // prevents infinite recursion when serializing
    private List<DeptEmployee> departments = new ArrayList<>();

    // Manager history (dept_manager)
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DeptManager> managerHistory = new ArrayList<>();

    // Salary history (salaries)
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SalaryHistory> salaryHistory = new ArrayList<>();

    // Title / position history (titles)
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TitleHistory> titleHistory = new ArrayList<>();

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

    public List<DeptManager> getManagerHistory() { return managerHistory; }
    public void setManagerHistory(List<DeptManager> managerHistory) { this.managerHistory = managerHistory; }

    public List<SalaryHistory> getSalaryHistory() { return salaryHistory; }
    public void setSalaryHistory(List<SalaryHistory> salaryHistory) { this.salaryHistory = salaryHistory; }

    public List<TitleHistory> getTitleHistory() { return titleHistory; }
    public void setTitleHistory(List<TitleHistory> titleHistory) { this.titleHistory = titleHistory; }
}