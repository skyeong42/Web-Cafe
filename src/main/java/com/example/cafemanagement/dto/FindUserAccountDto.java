package com.example.cafemanagement.dto;

public class FindUserAccountDto {
    private String email;
    private String username; //비밀번호 찾기를 위한 사용자 이름

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
