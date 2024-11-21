package com.example.cafemanagement.dto;

import java.util.List;

import com.example.cafemanagement.domain.Menu;

public class MenuDto {
    private String name;
    private double price;

    public MenuDto(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public static MenuDto of(Menu menus) {
        return new MenuDto(menus.getName(), menus.getPrice());
    }

    // Getters
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
