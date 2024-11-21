package com.example.cafemanagement.dto;

import java.util.List;

public class UserProfileDetailsDto {
    private Long userId;
    private String username;
    private String email;
    private String nickname;
    private String profilePicture;
    private List<ReviewDto> reviews;
    private List<BookingDto> bookings;

    // Constructor
    public UserProfileDetailsDto(Long userId, String username, String email, String nickname, String profilePicture, List<ReviewDto> reviews, List<BookingDto> bookings) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.nickname = nickname;
        this.profilePicture = profilePicture;
        this.reviews = reviews;
        this.bookings = bookings;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public List<ReviewDto> getReviews() {
        return reviews;
    }

    public Long getUserId() {
        return userId;
    }

    public List<BookingDto> getBookings() {
        return bookings;
    }
}
