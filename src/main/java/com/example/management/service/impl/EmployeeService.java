package com.example.management.service.impl;


import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.management.auth.AuthUtils;
import com.example.management.dto.EmployeeDTO;
import com.example.management.entity.Department;
import com.example.management.entity.Employee;
import com.example.management.entity.Position;
import com.example.management.entity.Users;
import com.example.management.exception.EmployeeAlreadyExistsException;
import com.example.management.exception.UnauthorizedException;
import com.example.management.mapper.EmployeeMapper;
import com.example.management.repository.DepartmentRepository;
import com.example.management.repository.EmployeeRepository;
import com.example.management.repository.PositionRepository;
import com.example.management.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private static final EmployeeMapper EMPLOYEE_MAPPER = EmployeeMapper.INSTANCE;

    private final EmployeeRepository employeeRepository;   
    private final UserService userService;
    private final DepartmentRepository departmentRepository;

    private final PositionRepository positionRepository;

    public EmployeeDTO addEmployee (EmployeeDTO employeeDTO){
        try {
            
            Employee employee = new Employee();
            employee.setName(employeeDTO.getName());
            if (employeeRepository.existsByEmail(employeeDTO.getEmail())) {
                throw new EmployeeAlreadyExistsException("Employee already exists");
            }
            employee.setEmail(employeeDTO.getEmail());
            employee.setSalary(employeeDTO.getSalary());
            employee.setHireDate(employeeDTO.getHireDate());
            Department department = departmentRepository.findById(employeeDTO.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Department not found"));
            employee.setDepartment(department);
            Position position = positionRepository.findById(employeeDTO.getPositionId())
                    .orElseThrow(() -> new RuntimeException("Position not found"));
            employee.setPosition(position);
            return EMPLOYEE_MAPPER.toDto(employeeRepository.save(employee));
        } catch (UnauthorizedException | EmployeeAlreadyExistsException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    
}
