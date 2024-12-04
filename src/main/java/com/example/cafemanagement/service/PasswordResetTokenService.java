package com.example.cafemanagement.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class PasswordResetTokenService {

    private final Map<String, String> tokenStorage = new HashMap<>();
    private final Map<String, LocalDateTime> tokenExpiry = new HashMap<>();

    public void saveToken(String email, String token) {
        tokenStorage.put(token, email);
        tokenExpiry.put(token, LocalDateTime.now().plusHours(1)); // 토큰 만료 시간 1시간
    }

    public String validateTokenAndGetEmail(String token) {
        if (!tokenStorage.containsKey(token) || tokenExpiry.get(token).isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("토큰이 유효하지 않거나 만료되었습니다.");
        }
        return tokenStorage.get(token);
    }

    public void invalidateToken(String token) {
        tokenStorage.remove(token);
        tokenExpiry.remove(token);
    }
}
