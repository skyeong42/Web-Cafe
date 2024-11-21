package com.example.cafemanagement.domain;

import jakarta.persistence.*;

@Entity
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_id", nullable = false) // 외래 키 매핑
    private Cafe cafe; // 연관된 카페 객체

    @Column(nullable = false)
    private String name; // 메뉴 이름

    @Column(nullable = false)
    private double price; // 메뉴 가격

    public Menu(Cafe cafe, String name, double price) {
        this.cafe = cafe;
        this.name = name;
        this.price = price;
    }

    public Menu() {
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Cafe getCafe() {
        return cafe;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    // Setters
    public void setCafe(Cafe cafe) {
        this.cafe = cafe;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Menu{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", price=" + price +
            '}';
    }
}
