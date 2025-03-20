package com.example.management.service.impl;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.management.dto.EmployeeDTO;
import com.example.management.dto.SalaryDTO;
import com.example.management.entity.Department;
import com.example.management.entity.Employee;
import com.example.management.entity.Position;
import com.example.management.entity.Salary;
import com.example.management.exception.EmployeeAlreadyExistsException;
import com.example.management.mapper.EmployeeMapper;
import com.example.management.repository.DepartmentRepository;
import com.example.management.repository.EmployeeRepository;
import com.example.management.repository.PositionRepository;
import com.example.management.repository.SalaryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private static final EmployeeMapper EMPLOYEE_MAPPER = EmployeeMapper.INSTANCE;

    private final EmployeeRepository employeeRepository;
    private final SalaryRepository salaryRepository;
    private final DepartmentRepository departmentRepository;

    private final PositionRepository positionRepository;

    public EmployeeDTO addEmployee(EmployeeDTO employeeDTO, SalaryDTO salaryDTO) {
        if (employeeRepository.existsByEmail(employeeDTO.getEmail())) {
            throw new EmployeeAlreadyExistsException("Employee already exists");
        }
        Employee employee = EMPLOYEE_MAPPER.toEntity(employeeDTO);
        Department department = departmentRepository.findById(employeeDTO.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));
        employee.setDepartment(department);
        Position position = positionRepository.findById(employeeDTO.getPositionId())
                .orElseThrow(() -> new RuntimeException("Position not found"));
        employee.setPosition(position);
        employee.setHireDate(LocalDate.now());
        Employee savedEmployee = employeeRepository.save(employee);
        Salary salary = new Salary();
        salary.setNewSalary(salaryDTO.getNewSalary());
        salary.setEffectiveDate(LocalDate.now());
        salary.setEmployee(savedEmployee);
        salaryRepository.save(salary);
        return EMPLOYEE_MAPPER.toDto(savedEmployee);
    }


    public EmployeeDTO updateEmployee(EmployeeDTO employeeDTO, Long employeeId) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        if (optionalEmployee.isEmpty()) {
            throw new RuntimeException("Employee not found");
        }
        Employee employee = optionalEmployee.get();
        employee.setName(employeeDTO.getName());
        employee.setEmail(employeeDTO.getEmail());
        Department department = departmentRepository.findById(employeeDTO.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));
        employee.setDepartment(department);
        Position position = positionRepository.findById(employeeDTO.getPositionId())
                .orElseThrow(() -> new RuntimeException("Position not found"));
        employee.setPosition(position);
        return EMPLOYEE_MAPPER.toDto(employeeRepository.save(employee));
    }
}
