package digicorp.entity;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    private int empNo;
    private Date birthDate;
    private String firstName;
    private String lastName;
    private String gender;
    private Date hireDate;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Department> department;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<PositionHistory> positionHistory;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<ManagerHistory> manager;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<SalaryHistory> salaryHistory;

//    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
//    private List<titleHistory> titleHistory;

    // getters + setters
    public Employee() {}
    public Employee(int id) {
        this.empNo = id;
    }

    public int getEmpNo() {
        return empNo;
    }
    public void setEmpNo(int empNo) {
        this.empNo = empNo;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    @Override
    public String toString() {
        return String.format("emp_no: %s, birthdate: %s, firstname: %s, " +
                        "lastname: %s, gender: %s, hiredate: %.2f",
                this.empNo, this.birthDate, this.firstName,
                this.lastName, this.gender, this.hireDate);
    }

}