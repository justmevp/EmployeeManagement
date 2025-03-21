package com.example.management.event;

import java.util.Map;
import org.springframework.context.ApplicationEvent;

public class AttendanceReportEvent extends ApplicationEvent {
    private final Map<String, Object> reportData;
    private final String recipient;
    private final String subject;

    public AttendanceReportEvent(Object source, Map<String, Object> reportData, String recipient, String subject) {
        super(source);
        this.reportData = reportData;
        this.recipient = recipient;
        this.subject = subject;
    }

    public Map<String, Object> getReportData() {
        return reportData;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getSubject() {
        return subject;
    }
}
