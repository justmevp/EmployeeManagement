package com.example.management.dto;

import java.util.List;

import com.example.management.entity.Position;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DepartmentDTO {

    private Long id;

    private String name;

    private String description;

    private List<Position> positions;

    private Long employeeCount;

    public DepartmentDTO(String name, Long employeeCount) {
        this.name = name;
        this.employeeCount = employeeCount;
    }
}
