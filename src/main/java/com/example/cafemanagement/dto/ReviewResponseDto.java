package com.example.cafemanagement.dto;

import com.example.cafemanagement.domain.Review;

public class ReviewResponseDto {
    private Long reviewId;
    private String title;
    private String content;
    private int rating;
    private Long userId;
    private Long cafeId;
    private String userName;

    public int getLikeCount() {
        return likeCount;
    }

    private int likeCount;

    public static ReviewResponseDto of(Review review, Long cafeId) {
        return new ReviewResponseDto(
                review.getId(),
                review.getTitle(),
                review.getContent(),
                review.getRating(),
                review.getUserId(),
                cafeId,
                review.getUser().getNickname(),
                review.getLikeCount()
        );

    }

    public ReviewResponseDto(Long reviewId, String title, String content, int rating, Long userId, Long cafeId, String userName, int likeCount) {
        this.reviewId = reviewId;
        this.title = title;
        this.content = content;
        this.rating = rating;
        this.userId = userId;
        this.cafeId = cafeId;
        this.userName = userName;
        this.likeCount = likeCount;
    }

    public Long getReviewId() {
        return reviewId;
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

    public String getUserName() {
        return userName;
    }
}
