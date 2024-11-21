package com.example.cafemanagement.dto;

public class ReviewUpdateDto {
    private String title;
    private String content;
    private int rating;

    // Constructor, Getters, and Setters
    public ReviewUpdateDto(String title, String content, int rating) {
        this.title = title;
        this.content = content;
        this.rating = rating;
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
}
