package digicorp.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "salaryhistory")
public class SalaryHistory {

    @Id
    private String emp_no;
    private double salary;
    private Date from_date;
    private Date to_date;

    // getters + setters
}