package com.example.cafemanagement.domain;

import jakarta.persistence.*;

@Entity
public class ReviewLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    protected ReviewLike() {}

    public ReviewLike(Review review, User user) {
        this.review = review;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public Review getReview() {
        return review;
    }

    public User getUser() {
        return user;
    }
}
