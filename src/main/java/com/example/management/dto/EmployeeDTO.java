package com.example.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeDTO {
    private Long id;
    private String name;
    private String email;
    private Double salary;
    private LocalDate hireDate;
    private Long departmentId;
    private Long positionId;
    private SalaryDTO salaryDTO;
    private String positionName;
    private String createdBy;

    public EmployeeDTO(Long id, String name, Double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

}
