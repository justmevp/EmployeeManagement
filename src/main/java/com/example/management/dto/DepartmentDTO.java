package com.example.management.dto;

import java.util.List;

import com.example.management.entity.Position;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDTO {

    private Long id;

    private String name;

    private String description;

    private List<Position> positions;
}
