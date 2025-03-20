package com.example.management.service.impl;

import org.springframework.stereotype.Service;

import com.example.management.repository.SalaryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SalaryService {
    private final SalaryRepository salaryRepository;

    public Double calculateTotalSalaryCost() {
        return salaryRepository.calculateTotalSalaryCost();
    }
}
