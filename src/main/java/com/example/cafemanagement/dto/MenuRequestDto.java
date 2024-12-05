package com.example.cafemanagement.dto;

public class MenuRequestDto {

    private String menuName;
    private Long menuId;
    private int quantity;

    public MenuRequestDto(String menuName, Long menuId, int quantity) {
        this.menuName = menuName;
        this.menuId = menuId;
        this.quantity = quantity;
    }

    public String getMenuName() {
        return menuName;
    }

    public Long getMenuId() {
        return menuId;
    }

    public int getQuantity() {
        return quantity;
    }
}
