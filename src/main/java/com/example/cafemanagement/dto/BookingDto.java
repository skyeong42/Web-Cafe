package com.example.cafemanagement.dto;

import com.example.cafemanagement.domain.BookingMenu;

import java.time.LocalDate;
import java.util.List;

public class BookingDto {
    private Long bookingId;
    private String title;
    private LocalDate bookingTime;
    private String status;
    private Long userId; // 사용자 ID
    private Long cafeId; // 카페 ID

    public List<BookingMenu> getMenuDtoList() {
        return menuDtoList;
    }

    private List<BookingMenu> menuDtoList;

    public BookingDto(Long bookingId, String title, LocalDate bookingTime, String status, Long userId, Long cafeId, List<BookingMenu> menuDtoList) {
        this.bookingId = bookingId;
        this.title = title;
        this.bookingTime = bookingTime;
        this.status = status;
        this.userId = userId;
        this.cafeId = cafeId;
        this.menuDtoList = menuDtoList;
    }

    // Getters
    public Long getBookingId() {
        return bookingId;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getBookingTime() {
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
