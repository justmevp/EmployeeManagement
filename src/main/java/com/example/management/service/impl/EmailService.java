package com.example.management.service.impl;


import java.util.Locale;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Autowired
    public EmailService(JavaMailSender mailSender, SpringTemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    /**
     * Gửi email báo cáo chấm công cho HR bất đồng bộ.
     * @param to Địa chỉ email người nhận (HR)
     * @param subject Tiêu đề email
     * @param templateModel Model chứa dữ liệu hiển thị trong email (ví dụ: reportDate, attendanceRecords)
     */
    @Async
    public void sendAttendanceReportEmail(String to, String subject, Map<String, Object> templateModel) {
        Context context = new Context(Locale.US);
        context.setVariables(templateModel);
        String htmlContent = templateEngine.process("attendance-report", context);

        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom("vpek215@gmail.com"); // Điều chỉnh email người gửi
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true); // true: sử dụng HTML
        };

        mailSender.send(messagePreparator);
    }
}
