package com.example.management.entity.attendanceinterface;

import com.example.management.util.Status;

public interface LeaveRequestProjection {
    Long getId();
    String getEmployeeName();
    Status getStatus();
}
