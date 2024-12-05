package com.example.cafemanagement.dto;

import java.util.List;

import com.example.cafemanagement.domain.Menu;

public class MenuDto {
    public Long getId() {
        return id;
    }

    private Long id;
    private String name;
    private double price;

    public MenuDto(Long id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public static MenuDto of(Menu menus) {
        return new MenuDto(menus.getId(), menus.getName(), menus.getPrice());
    }

    // Getters
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
