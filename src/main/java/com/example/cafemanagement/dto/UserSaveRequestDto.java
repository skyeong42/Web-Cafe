package com.example.cafemanagement.dto;

import java.time.LocalDate;

public class UserSaveRequestDto {

    private String username;
    private String password;
    private String confirmPassword;
    private String nickname;
    private String email;
    private String gender; // 성별 (MALE, FEMALE)

    public LocalDate getBirthDate() {
        return birthDate;
    }

    private LocalDate birthDate; // 생년월일

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    private String profileImageUrl;

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

}
