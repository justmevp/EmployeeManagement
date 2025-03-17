package com.example.management.dto;

import com.example.management.entity.Department;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PositionDTO {

    private Long id;

    private String name;

    private String description;

    private Department department;
}
