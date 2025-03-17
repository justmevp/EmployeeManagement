package com.example.management.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.management.dto.SalaryDTO;
import com.example.management.entity.Salary;


@Mapper
public interface SalaryMapper {

    SalaryMapper INSTANCE = Mappers.getMapper(SalaryMapper.class);
    SalaryDTO toDto(Salary salary);
    Salary toEntity(SalaryDTO salaryDTO);
    
}
