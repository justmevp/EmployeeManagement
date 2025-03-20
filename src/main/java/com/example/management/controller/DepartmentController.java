package com.example.management.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.management.constant.EndpointConstant;
import com.example.management.dto.DepartmentDTO;
import com.example.management.service.impl.DepartmentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(EndpointConstant.DEPARTMENT)
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;

    @GetMapping("/employee-count")
    public ResponseEntity<List<DepartmentDTO>> getEmployeeCountByDepartment() {
        return ResponseEntity.ok(departmentService.countEmployee());
    }
}
