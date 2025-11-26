package digicorp.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    private Long empNo;
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

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<titleHistory> titleHistory;

    // getters + setters
}