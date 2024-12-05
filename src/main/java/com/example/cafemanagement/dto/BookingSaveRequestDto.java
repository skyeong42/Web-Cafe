package com.example.cafemanagement.dto;

import com.example.cafemanagement.domain.Menu;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class BookingSaveRequestDto {
    private String title; // 예약 제목
    private LocalDate bookingTime; // 예약 시간
    private List<MenuRequestDto> menuList;
    private String status; // 예약 상태
    private Long userId; // 사용자 ID
    private Long cafeId; // 카페 ID

    // Constructor
    public BookingSaveRequestDto(String title, LocalDate bookingTime, List<MenuRequestDto> menuList, String status, Long userId, Long cafeId) {
        this.title = title;
        this.bookingTime = bookingTime;
        this.menuList = menuList;
        this.status = status;
        this.userId = userId;
        this.cafeId = cafeId;
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getBookingTime() {
        return bookingTime;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCafeId() {
        return cafeId;
    }

    public void setCafeId(Long cafeId) {
        this.cafeId = cafeId;
    }

    public List<MenuRequestDto> getMenuList() {
        return menuList;
    }
}
