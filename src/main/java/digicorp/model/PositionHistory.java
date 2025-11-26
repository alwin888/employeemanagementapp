package digicorp.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "positionhistory")
public class PositionHistory {

    @Id
    private String emp_no;
    private String title;
    private Date from_date;
    private Date to_date;
    // getters + setters
}