package com.example.cafemanagement.dto;

import java.util.List;

public class CafeDto {

    private Long cafeId; // 카페 ID
    private String cafeName; // 카페 이름
    private double rating; // 평점
    private String description; // 설명
    private String category; // 카테고리

    public String getImageUrl() {
        return imageUrl;
    }

    private String imageUrl;
    private LocationDto location;
    private List<MenuDto> menus; // 메뉴 DTO 리스트

    public List<ReviewDto> getReviewDtos() {
        return reviewDtos;
    }

    private List<ReviewDto> reviewDtos;

    // 생성자
    public CafeDto(Long cafeId, String cafeName, LocationDto location, double rating, String description, String category, String imageUrl, List<MenuDto> menus,
                   List<ReviewDto> reviewDtos) {
        this.cafeId = cafeId;
        this.cafeName = cafeName;
        this.location = location;
        this.rating = rating;
        this.description = description;
        this.category = category;
        this.imageUrl = imageUrl;
        this.menus = menus;
        this.reviewDtos = reviewDtos;
    }

    // Getters
    public Long getCafeId() {
        return cafeId;
    }

    public String getCafeName() {
        return cafeName;
    }

    public LocationDto getLocation() {
        return location;
    }

    public double getRating() {
        return rating;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public List<MenuDto> getMenus() {
        return menus;
    }

    @Override
    public String toString() {
        return "CafeDto{" +
            "cafeId=" + cafeId +
            ", cafeName='" + cafeName + '\'' +
            ", location='" + location + '\'' +
            ", rating=" + rating +
            ", description='" + description + '\'' +
            ", category='" + category + '\'' +
            ", menus=" + menus +
            '}';
    }
}
