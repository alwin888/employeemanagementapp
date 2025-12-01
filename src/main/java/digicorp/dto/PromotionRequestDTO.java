package digicorp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class PromotionRequestDTO {

    private int empNo;
    private String newTitle;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fromDate;

    private int salary;
    private String deptNo;
    @JsonProperty("manager")
    private boolean manager;

    public PromotionRequestDTO() {}

    public int getEmpNo() { return empNo; }
    public void setEmpNo(int empNo) { this.empNo = empNo; }

    public String getNewTitle() { return newTitle; }
    public void setNewTitle(String newTitle) { this.newTitle = newTitle; }

    public LocalDate getFromDate() { return fromDate; }
    public void setFromDate(LocalDate fromDate) { this.fromDate = fromDate; }

    public int getNewSalary() { return salary; }
    public void setSalary(int salary) { this.salary = salary; }

    public String getNewDeptNo() { return deptNo; }
    public void setDeptNo(String deptNo) { this.deptNo = deptNo; }

    public boolean isManager() { return manager; }
    public void setManager(boolean manager) { this.manager = manager; }


}