package com.example.cafemanagement.config;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class InMemoryTokenBlacklist implements TokenBlacklist {

    private final Map<String, Long> blacklist = new ConcurrentHashMap<>();

    @Override
    public void addToBlacklist(String token, long expirationTime) {
        blacklist.put(token, expirationTime);
    }

    @Override
    public boolean isBlacklisted(String token) {
        return blacklist.containsKey(token);
    }

    // 만료된 토큰 제거 (주기적으로 실행)
    @Scheduled(fixedRate = 60000) // 1분마다 실행
    public void removeExpiredTokens() {
        long now = System.currentTimeMillis();
        blacklist.entrySet().removeIf(entry -> entry.getValue() < now);
    }
}

