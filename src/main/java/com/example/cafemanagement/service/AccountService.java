package com.example.cafemanagement.service;

import com.example.cafemanagement.domain.User;
import com.example.cafemanagement.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccountService {

    private final UserRepository userRepository;
    private final PasswordResetTokenService passwordResetTokenService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    public AccountService(UserRepository userRepository, PasswordResetTokenService passwordResetTokenService,
                          EmailService emailService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordResetTokenService = passwordResetTokenService;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    // 아이디 찾기
    public String findUserIdByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(User::getUsername)
                .orElseThrow(() -> new IllegalArgumentException("해당 이메일로 등록된 계정을 찾을 수 없습니다."));
    }

    // 비밀번호 재설정 요청
    public void requestPasswordReset(String username, String email) {
        User user = userRepository.findByUsernameAndEmail(username, email)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디와 이메일 조합을 찾을 수 없습니다."));

        // 비밀번호 재설정 토큰 생성 및 저장
        String resetToken = UUID.randomUUID().toString();
        passwordResetTokenService.saveToken(email, resetToken);

        // 이메일 전송
        emailService.sendEmail(email, "비밀번호 재설정",
                "<p>안녕하세요,</p>" +
                        "<p>비밀번호를 재설정하려면 아래 링크를 클릭하세요:</p>" +
                        "<a href='http://localhost:8080/account/reset-password?token=" + resetToken + "'>비밀번호 재설정 링크</a>" +
                        "<p>감사합니다.</p>"
        );

    }

    // 비밀번호 재설정
    public void resetPassword(String token, String newPassword) {
        String email = passwordResetTokenService.validateTokenAndGetEmail(token);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 이메일로 등록된 계정을 찾을 수 없습니다."));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    // 토큰 유효성 검사
    public String validatePasswordResetToken(String token) {
        return passwordResetTokenService.validateTokenAndGetEmail(token);
    }
}
