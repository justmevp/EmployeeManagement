package com.example.management.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.management.constant.EndpointConstant;
import com.example.management.dto.EmployeeDTO;
import com.example.management.dto.EmployeeDetailsDTO;
import com.example.management.service.impl.EmployeeService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(EndpointConstant.EMPLOYEE)
@Tag(name = "Employee Controller", description = "API for managing employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EmployeeDTO> addEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return ResponseEntity.ok(employeeService.addEmployee(employeeDTO, employeeDTO.getSalaryDTO()));
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeDetailsDTO> getEmployeeDetails(@PathVariable("employeeId") Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeDetailsById(id));
    }

    @PutMapping("/{employeeId}/update")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<EmployeeDTO> updateEmployee(@RequestBody EmployeeDTO employeeDTO,
            @PathVariable("employeeId") Long employeeId) {
        return ResponseEntity.ok(employeeService.updateEmployee(employeeDTO, employeeId));
    }

    @GetMapping("/by-position/{positionName}")
    public List<EmployeeDTO> getEmployeesByPosition(@PathVariable String positionName) {
        return employeeService.getEmployeesByPosition(positionName);
    }

    @GetMapping("/sorted-by-salary")
    public ResponseEntity<Page<EmployeeDTO>> getEmployeesSorted(@RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "4") int size) {
        return ResponseEntity.ok(employeeService.getEmployeesSortedBySalary(page, size));
    }
    @GetMapping("/sorted-by-salary-slice")
    public ResponseEntity<Slice<EmployeeDTO>> getEmployeesSliceSorted(@RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "4") int size) {
        return ResponseEntity.ok(employeeService.getEmployeesSortedBySalarySlice(page, size));
    }
}
