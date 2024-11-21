package com.example.cafemanagement.dto;

public class UserUpdateRequestDto {

    private String password;
    private String email;
    private String nickname;
    private String profileImageUrl;

    public UserUpdateRequestDto(String password, String email, String nickname, String profileImageUrl) {
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }

    // Getters and Setters
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getProfileImageUrl() {
        return profileImageUrl;
    }
}
