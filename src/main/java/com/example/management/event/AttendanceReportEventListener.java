package com.example.management.event;



import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.example.management.service.impl.EmailService;

@Component
public class AttendanceReportEventListener implements ApplicationListener<AttendanceReportEvent> {

    private final EmailService emailService;

    public AttendanceReportEventListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public void onApplicationEvent(AttendanceReportEvent event) {
        emailService.sendAttendanceReportEmail(event.getRecipient(), event.getSubject(), event.getReportData());
    }
}
