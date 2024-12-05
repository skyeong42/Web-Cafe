package com.example.cafemanagement.dto;

public class MenuRequestDto {

    private String menuName;
    private Long menuId;
    private int menuCount;

    public MenuRequestDto(String menuName, Long menuId, int menuCount) {
        this.menuName = menuName;
        this.menuId = menuId;
        this.menuCount = menuCount;
    }

    public String getMenuName() {
        return menuName;
    }

    public Long getMenuId() {
        return menuId;
    }

    public int getMenuCount() {
        return menuCount;
    }
}
