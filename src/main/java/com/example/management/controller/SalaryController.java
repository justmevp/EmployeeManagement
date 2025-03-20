package com.example.management.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.management.constant.EndpointConstant;
import com.example.management.service.impl.SalaryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(EndpointConstant.API)
@RequiredArgsConstructor
public class SalaryController {
    private final SalaryService salaryService;

    @GetMapping("/total-cost")
    public ResponseEntity<Double> getTotalSalaryCost() {
        Double totalCost = salaryService.calculateTotalSalaryCost();
        return ResponseEntity.ok(totalCost);
    }
}
