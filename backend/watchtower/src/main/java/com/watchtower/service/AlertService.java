package com.watchtower.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class AlertService {

    private final JavaMailSender mailSender;

    @Value("${watchtower.alert.email}")
    private String alertEmail;

    // Cooldown tracking — prevents alert spam
    private LocalDateTime lastAlertTime = null;
    private static final int COOLDOWN_MINUTES = 5;

    public AlertService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendAlert(String subject, String body) {
        // Only send if outside cooldown window
        if (lastAlertTime != null &&
                LocalDateTime.now().isBefore(lastAlertTime.plusMinutes(COOLDOWN_MINUTES))) {
            return;
        }

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(alertEmail);
            message.setSubject("[WatchTower Alert] " + subject);
            message.setText(body + "\n\nTime: " +
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) +
                    "\n\n-- WatchTower Monitoring System");
            mailSender.send(message);
            lastAlertTime = LocalDateTime.now();
        } catch (Exception e) {
            System.err.println("Alert email failed: " + e.getMessage());
        }
    }
}