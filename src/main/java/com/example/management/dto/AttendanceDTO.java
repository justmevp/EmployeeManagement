package com.example.management.dto;

import java.time.LocalDateTime;

import com.example.management.entity.Employee;
import com.example.management.util.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceDTO {
    
    private Long id;
    private Employee employee;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private Status status;
}
