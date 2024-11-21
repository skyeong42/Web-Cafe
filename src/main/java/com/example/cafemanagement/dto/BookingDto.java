package com.example.cafemanagement.dto;

import java.time.LocalDateTime;

public class BookingDto {
    private Long bookingId;
    private String title;
    private LocalDateTime bookingTime;
    private String status;
    private Long userId; // 사용자 ID
    private Long cafeId; // 카페 ID

    public BookingDto(Long bookingId, String title, LocalDateTime bookingTime, String status, Long userId, Long cafeId) {
        this.bookingId = bookingId;
        this.title = title;
        this.bookingTime = bookingTime;
        this.status = status;
        this.userId = userId;
        this.cafeId = cafeId;
    }

    // Getters
    public Long getBookingId() {
        return bookingId;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public String getStatus() {
        return status;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getCafeId() {
        return cafeId;
    }
}
