package com.example.cafemanagement.controller;


import com.example.cafemanagement.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }


    // 아이디 찾기 (폼 데이터 지원)
    @PostMapping("/find-id")
    public ResponseEntity<Map<String, Object>> findUserId(@RequestParam("email") String email) {
        try {
            String username = accountService.findUserIdByEmail(email);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "등록된 아이디는: " + username);
            response.put("status", HttpStatus.OK);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            errorResponse.put("status", HttpStatus.NOT_FOUND);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }



    // 비밀번호 재설정 요청 (폼 데이터 처리)
    @PostMapping("/reset-password")
    public ResponseEntity<String> requestPasswordReset(
            @RequestParam("username") String username,
            @RequestParam("email") String email) {
        try {
            accountService.requestPasswordReset(username, email);
            return ResponseEntity.ok("비밀번호 재설정 링크가 이메일로 발송되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }



    // 비밀번호 재설정
    @PostMapping("/reset-password/confirm")
    public String resetPassword(@RequestParam String token, @RequestParam String newPassword, Model model) {
        try {
            accountService.resetPassword(token, newPassword);
            model.addAttribute("message", "비밀번호가 성공적으로 변경되었습니다.");
            return "reset-password-confirm"; // 성공 페이지
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("token", token); // token 값 전달
            return "reset-password-error"; // 에러 페이지
        }
    }


    @GetMapping("/reset-password")
    public String showResetPasswordPage(@RequestParam String token, Model model) {
        try {
            // 토큰 검증
            accountService.validatePasswordResetToken(token);
            model.addAttribute("token", token); // 유효한 토큰을 모델에 추가
            return "reset-password"; // 비밀번호 재설정 페이지
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "reset-password-error"; // 에러 페이지
        }
    }


}

