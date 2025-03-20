package com.example.management.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
    private String name;
    private String email;
    private Double salary;
    private LocalDate hireDate;
    private Long departmentId;
    private Long positionId;
    private SalaryDTO salaryDTO;
 
}
