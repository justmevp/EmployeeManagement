package com.example.management.dto;

import java.time.LocalDate;

import com.example.management.entity.Employee;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalaryDTO {

    private Long id;

    private Employee employee;

    private Double oldSalary;

    private Double newSalary;

    private LocalDate effectiveDate;
}
