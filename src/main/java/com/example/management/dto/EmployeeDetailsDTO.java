package com.example.management.dto;
import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_NULL)
public interface EmployeeDetailsDTO {
    Long getId();
    String getFullName();
    String getEmail();
    String getDeptName();
    String getPositionName();
    Double getNewSalary();
}
