package com.example.cafemanagement.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String to, String subject, String text) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            // 보내는 사람 이메일과 이름 설정
            helper.setFrom("leejm9630@gmail.com", "Caffong"); // 어플 이름 설정
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true); // HTML 텍스트

            mailSender.send(message);
            System.out.println("이메일 전송 성공");
        } catch (MessagingException | java.io.UnsupportedEncodingException e) {
            System.err.println("이메일 전송 실패: " + e.getMessage());
        }
    }
}
