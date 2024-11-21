package com.example.cafemanagement.dto;

import java.util.List;

import com.example.cafemanagement.domain.Attachment;
import com.example.cafemanagement.domain.Review;

public class ReviewDto {
    private Long reviewId;
    private String title;
    private String content;
    private int rating;
    private Long userId;
    private Long cafeId;
    private List<Attachment> attachments; // 첨부파일 URL 리스트

    public ReviewDto(Long reviewId, String title, String content, int rating, Long userId, Long cafeId, List<Attachment> attachments) {
        this.reviewId = reviewId;
        this.title = title;
        this.content = content;
        this.rating = rating;
        this.userId = userId;
        this.cafeId = cafeId;
        this.attachments = attachments;
    }

    public static ReviewDto of(Review review, Long cafeId) {
        return new ReviewDto(
            review.getId(),
            review.getTitle(),
            review.getContent(),
            review.getRating(),
            review.getUserId(),
            cafeId,
            review.getAttachments()
        );

    }

    // Getters
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

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public Long getCafeId() {
        return cafeId;
    }
}
