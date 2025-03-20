package com.example.management.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.management.dto.AttendanceDTO;
import com.example.management.entity.Attendance;
import com.example.management.entity.Employee;
import com.example.management.entity.attendanceinterface.LeaveRequestProjection;
import com.example.management.mapper.AttendanceMapper;
import com.example.management.repository.AttendanceRepository;
import com.example.management.repository.EmployeeRepository;
import com.example.management.util.Status;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;

    private final EmployeeRepository employeeRepository;

    private static final AttendanceMapper ATTENDANCE_MAPPER = AttendanceMapper.INSTANCE;

    @Transactional
    public AttendanceDTO checkin(Authentication authentication) {
        String email = authentication.getName();
        Optional<Employee> optionalEmployee = employeeRepository.findByEmail(email);
        if (optionalEmployee.isEmpty()) {
            throw new RuntimeException("There is no employee like this");
        }
        Employee employee = optionalEmployee.get();
        Attendance attendance = new Attendance();
        attendance.setCheckIn(LocalDateTime.now());
        attendance.setDate(LocalDate.now());
        attendance.setStatus(Status.PRESENT);
        attendance.setEmployee(employee);
        return ATTENDANCE_MAPPER.toDto(attendanceRepository.save(attendance));
    }

    @Transactional
    public AttendanceDTO checkout(Authentication authentication) {
        String email = authentication.getName();
        Optional<Employee> optionalEmployee = employeeRepository.findByEmail(email);
        if (optionalEmployee.isEmpty()) {
            throw new RuntimeException("There is no employee like this");
        }
        Employee employee = optionalEmployee.get();
        Attendance attendance = attendanceRepository.findByEmployee_IdAndDate(employee.getId(), LocalDate.now())
        .orElseThrow(() -> new RuntimeException("There is no check-in today"));
        // Kiểm tra nếu đã checkout rồi
        if (attendance.getCheckOut() != null) {
            throw new RuntimeException("Employee has already checked out today.");
        }
        attendance.setCheckOut(LocalDateTime.now());
        return ATTENDANCE_MAPPER.toDto(attendanceRepository.save(attendance));
    }

    public AttendanceDTO onLeave(Authentication authentication){
        String email = authentication.getName();
        Optional<Employee> optionalEmployee = employeeRepository.findByEmail(email);
        if(optionalEmployee.isEmpty()){
            throw new RuntimeException("No employee like this");
        }
        Employee employee = optionalEmployee.get();
        Attendance attendance = new Attendance();
        attendance.setStatus(Status.ONLEAVE);
        attendance.setDate(LocalDate.now());
        attendance.setEmployee(employee);    
        return ATTENDANCE_MAPPER.toDto(attendanceRepository.save(attendance));

    }

     public List<LeaveRequestProjection> getLeaveRequests() {
        return attendanceRepository.findLeaveRequests(Status.ONLEAVE);
    }

    public Integer getTotalWorkingHours(Long employeeId, int month, int year) {
        Integer hours = attendanceRepository.calculateTotalWorkingHours(employeeId, month, year);
        return hours != null ? hours : 0; // Tránh lỗi NullPointer
    }
}
