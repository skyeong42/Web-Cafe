package com.example.cafemanagement.config;

public interface TokenBlacklist {
    void addToBlacklist(String token, long expirationTime);
    boolean isBlacklisted(String token);
}

