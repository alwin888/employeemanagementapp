package digicorp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public class PromotionRequestDTO {

    private int empNo;
    private String newTitle;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fromDate;

    public PromotionRequestDTO() {}

    public int getEmpNo() { return empNo; }
    public void setEmpNo(int empNo) { this.empNo = empNo; }

    public String getNewTitle() { return newTitle; }
    public void setNewTitle(String newTitle) { this.newTitle = newTitle; }

    public LocalDate getFromDate() { return fromDate; }
    public void setFromDate(LocalDate fromDate) { this.fromDate = fromDate; }
}