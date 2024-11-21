package com.example.cafemanagement.dto;

import java.time.LocalDateTime;

public class BookingSaveRequestDto {
    private String title; // 예약 제목
    private LocalDateTime bookingTime; // 예약 시간
    private String status; // 예약 상태
    private Long userId; // 사용자 ID
    private Long cafeId; // 카페 ID

    // Constructor
    public BookingSaveRequestDto(String title, LocalDateTime bookingTime, String status, Long userId, Long cafeId) {
        this.title = title;
        this.bookingTime = bookingTime;
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

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
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
}
