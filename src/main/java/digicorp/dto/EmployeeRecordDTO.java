package digicorp.dto;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;

public class EmployeeRecordDTO {

    private int empNo;
    private String firstName;
    private String lastName;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate hireDate;


    public EmployeeRecordDTO(int empNo, String firstName, String lastName, LocalDate hireDate) {
        this.empNo = empNo;
        this.firstName = firstName;
        this.lastName = lastName;
        this.hireDate = hireDate;
    }

    public int getEmpNo() { return empNo; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public LocalDate getHireDate() { return hireDate; }
}