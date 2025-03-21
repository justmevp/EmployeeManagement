package com.example.management.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.management.dto.EmployeeDTO;
import com.example.management.dto.EmployeeDetailsDTO;
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

    @Transactional(readOnly = true)
    public EmployeeDetailsDTO getEmployeeDetailsById(Long id) {
        return employeeRepository.findEmployeeDetailsById(id);
    }

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

    @Transactional 
    public EmployeeDTO updateEmployee(EmployeeDTO employeeDTO, Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        employee.setName(employeeDTO.getName());
        employee.setEmail(employeeDTO.getEmail());
        Department department = departmentRepository.findById(employeeDTO.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));
        employee.setDepartment(department);
        Position position = positionRepository.findById(employeeDTO.getPositionId())
                .orElseThrow(() -> new RuntimeException("Position not found"));
        employee.setPosition(position);
        Salary salary = salaryRepository.findSalaryByEmployeeId(employeeId)
                .orElse(new Salary());

        if (salary.getId() != null) {
            salary.setOldSalary(salary.getNewSalary());
        }
        salary.setNewSalary(employeeDTO.getSalary());
        salary.setEmployee(employee);
        salary.setEffectiveDate(LocalDate.now());

        salaryRepository.save(salary);
        employeeRepository.save(employee);

        return EMPLOYEE_MAPPER.toDto(employee);
    }

    public List<EmployeeDTO> getEmployeesByPosition(String positionName) {
        return employeeRepository.getEmployeesByPosition(positionName)
                .stream()
                .map(EmployeeMapper.INSTANCE::employeeByPosition)
                .collect(Collectors.toList());
    }

    public Page<EmployeeDTO> getEmployeesSortedBySalary(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return employeeRepository.findEmployeesWithSalary(pageable);
    }

    public Slice<EmployeeDTO> getEmployeesSortedBySalarySlice(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return employeeRepository.findEmployeesWithSalarySlice(pageable);
    }

}
