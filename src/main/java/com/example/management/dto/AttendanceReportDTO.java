package com.example.management.dto;



import java.time.LocalDateTime;
import com.example.management.util.Status;

public interface AttendanceReportDTO {
    String getName();          
    LocalDateTime getCheckIn();  
    LocalDateTime getCheckOut(); 
    Status getStatus();        
}
