package com.example.cafemanagement.dto;

import java.util.List;

public class CafeRequestDto {
    private String cafeName;
    private String description;
    private String category;
    private LocationDto location;
    //수정
    private List<String> hashtags;
    private List<MenuDto> menus;

    public String getCafeImageUrl() {
        return cafeImageUrl;
    }

    private String cafeImageUrl;


    // Getters and Setters
    public String getCafeName() {
        return cafeName;
    }

    public void setCafeName(String cafeName) {
        this.cafeName = cafeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocationDto getLocation() {
        return location;
    }

    public void setLocation(LocationDto location) {
        this.location = location;
    }

    //수정
    public List<String> getHashtags() {
        return hashtags;
    }

    //수정 List<String>으로 변경
    public void setHashtags(List<String> hashtags) {
        this.hashtags = hashtags;
    }
    public List<MenuDto> getMenus() {
        return menus;
    }

    public void setMenus(List<MenuDto> menus) {
        this.menus = menus;
    }
}
