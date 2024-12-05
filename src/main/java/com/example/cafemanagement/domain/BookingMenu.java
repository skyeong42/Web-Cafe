package com.example.cafemanagement.domain;

import jakarta.persistence.*;

@Entity
public class BookingMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 중간 테이블의 고유 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false) // 예약과의 연결
    private Booking booking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false) // 메뉴와의 연결
    private Menu menu;

    @Column(nullable = false)
    private int quantity; // 주문 수량

    // 기본 생성자
    protected BookingMenu() {}

    public BookingMenu(Booking booking, Menu menu, int quantity) {
        this.booking = booking;
        this.menu = menu;
        this.quantity = quantity;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
