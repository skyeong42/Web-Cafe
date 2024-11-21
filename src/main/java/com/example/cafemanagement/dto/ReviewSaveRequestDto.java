package com.example.cafemanagement.dto;

public class ReviewSaveRequestDto {
    private String title;
    private String content;
    private int rating;
    private Long userId;
    private Long cafeId;

    // Constructor, Getters, and Setters
    public ReviewSaveRequestDto(String title, String content, int rating, Long userId, Long cafeId) {
        this.title = title;
        this.content = content;
        this.rating = rating;
        this.userId = userId;
        this.cafeId = cafeId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public int getRating() {
        return rating;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getCafeId() {
        return cafeId;
    }
}
