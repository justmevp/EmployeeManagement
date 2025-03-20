package com.example.management.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.management.dto.DepartmentDTO;
import com.example.management.entity.Department;

@Mapper
public interface DepartmentMapper {

    DepartmentMapper INSTANCE = Mappers.getMapper(DepartmentMapper.class);

    DepartmentDTO toDto(Department department);

    Department toEntity(DepartmentDTO departmentDTO);

}
