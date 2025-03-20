package com.example.management.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.management.constant.EndpointConstant;
import com.example.management.dto.AttendanceDTO;
import com.example.management.entity.attendanceinterface.LeaveRequestProjection;
import com.example.management.service.impl.AttendanceService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(EndpointConstant.ATTENDANCE)
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping("/checkin")
    public ResponseEntity<AttendanceDTO> checkIn(Authentication authentication) {
        return ResponseEntity.ok(attendanceService.checkin(authentication));
    }

    @PostMapping("/checkout")
    public ResponseEntity<AttendanceDTO> checkOut(Authentication authentication) {
        return ResponseEntity.ok(attendanceService.checkout(authentication));
    }

    @PostMapping("/onleave")
    public ResponseEntity<AttendanceDTO> onLeave(Authentication authentication) {
        return ResponseEntity.ok(attendanceService.onLeave(authentication));
    }
    @PostMapping("/leave-requests")
    public ResponseEntity<List<LeaveRequestProjection>> getLeaveRequest() {
        return ResponseEntity.ok(attendanceService.getLeaveRequests());
    }


    @GetMapping("/{employeeId}/working-hours")
    public ResponseEntity<Integer> getTotalWorkingHours(
            @PathVariable Long employeeId,
            @RequestParam int month,
            @RequestParam int year) {
        return ResponseEntity.ok(attendanceService.getTotalWorkingHours(employeeId, month, year));
    }
}
