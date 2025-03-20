package com.example.management.service.impl;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.management.dto.DepartmentDTO;
import com.example.management.entity.Department;
import com.example.management.mapper.DepartmentMapper;
import com.example.management.repository.DepartmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    private final DepartmentMapper DEPARTMENT_MAPPER =  DepartmentMapper.INSTANCE;
    public List<DepartmentDTO> countEmployee(){
        return departmentRepository.getEmployeeCountByDepartment();
    }

    @Cacheable(value = "departmentsWithPositions")
    public List<DepartmentDTO> getDepartmentsWithPositions() {
        List<Department> departments = departmentRepository.findAllWithPositions();
        return DEPARTMENT_MAPPER.toDtoList(departments);
    }

}
