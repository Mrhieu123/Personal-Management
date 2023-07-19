package com.group6.moneymanagementbooking.service;


import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public void sendVerifyEmail(String to, String subject, String body) throws EmailException {
        Runnable sendEmailTask = () -> {
            try {
                HtmlEmail email = new HtmlEmail();
                email.setHostName("imap.gmail.com");
                email.setSmtpPort(587);
                email.setAuthenticator(new DefaultAuthenticator("nguyntrng234@gmail.com", "kubbxerhqhoazngp"));
                email.setStartTLSEnabled(true);
                email.setFrom("nguyntrng234@gmail.com");
                email.addTo(to);
                email.setSubject(subject);
                email.setHtmlMsg(body);
                email.send();
            } catch (EmailException e) {
                if (e.getMessage().contains("Invalid Addresses")) {
                    System.out.println("Invalid Addresses");
                } else {
                    System.out.println("Lỗi gửi email: " + e.getMessage());
                }
            }
        };

        // Tạo một luồng mới để gửi email bất đồng bộ
        Thread thread = new Thread(sendEmailTask);
        thread.start();
    }

    // Generate a unique identifier for the token
}