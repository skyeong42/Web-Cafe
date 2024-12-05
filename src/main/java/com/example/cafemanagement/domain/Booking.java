package com.example.cafemanagement.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId; // 예약 고유 ID

    @Column(nullable = false)
    private String title; // 예약 제목

    @Column(nullable = false)
    private LocalDate bookingTime; // 예약 시간

    @Column(nullable = false)
    private String status; // 예약 상태

    public List<BookingMenu> getBookingMenus() {
        return bookingMenus;
    }

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookingMenu> bookingMenus = new ArrayList<>();


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 예약 신청자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_id", nullable = false)
    private Cafe cafe; // 예약된 카페

    // 기본 생성자
    protected Booking() {}

    public Booking(String title, LocalDate bookingTime, String status, User user, Cafe cafe, List<BookingMenu> bookingMenus) {
        this.title = title;
        this.bookingTime = bookingTime;
        this.status = status;
        this.user = user;
        this.cafe = cafe;
        this.bookingMenus = bookingMenus;
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

    public User getUser() {
        return user;
    }

    public Cafe getCafe() {
        return cafe;
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }


    public void setStatus(String status) {
        this.status = status;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCafe(Cafe cafe) {
        this.cafe = cafe;
    }
}

