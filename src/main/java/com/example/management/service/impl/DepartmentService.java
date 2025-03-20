package com.example.management.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.management.dto.DepartmentDTO;
import com.example.management.repository.DepartmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public List<DepartmentDTO> countEmployee(){
        return departmentRepository.getEmployeeCountByDepartment();
    }


}
