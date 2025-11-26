package digicorp.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "managerhistory")
public class ManagerHistory {

    @Id
    private String emp_no;
    private String dept_no;
    private Date from_date;
    private Date to_date;

    // getters + setters
}