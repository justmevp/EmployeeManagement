package com.example.management.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationEventPublisher;
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
import com.example.management.dto.AttendanceReportDTO;
import com.example.management.entity.attendanceinterface.LeaveRequestProjection;
import com.example.management.event.AttendanceReportEvent;
import com.example.management.service.impl.AttendanceService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(EndpointConstant.ATTENDANCE)
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;
    private final ApplicationEventPublisher eventPublisher;

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

     @PostMapping("/send-report")
    public ResponseEntity<String> sendAttendanceReport(@RequestParam String hrEmail,
                                                       @RequestParam(required = false) String date) {
        // Nếu không truyền date, sử dụng ngày hiện tại
        LocalDate reportDate = (date == null || date.isEmpty()) ? LocalDate.now() : LocalDate.parse(date);

        // Lấy danh sách chấm công theo ngày
        List<AttendanceReportDTO> attendanceRecords = attendanceService.getAttendanceRecordsByDate(reportDate);

        // Tạo model cho Thymeleaf template
        Map<String, Object> reportData = new HashMap<>();
        reportData.put("reportDate", reportDate);
        reportData.put("attendanceRecords", attendanceRecords);

        // Tạo event để gửi email báo cáo
        AttendanceReportEvent event = new AttendanceReportEvent(this, reportData, hrEmail, "Báo cáo chấm công ngày " + reportDate);
        eventPublisher.publishEvent(event);

        return ResponseEntity.ok("Email báo cáo chấm công đã được gửi đến " + hrEmail);
    }
}
