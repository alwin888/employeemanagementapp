package digicorp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "departments")
public class Department {

    @Id
    private String dept_no;
    private String dept_name;

    // getters + setters
}