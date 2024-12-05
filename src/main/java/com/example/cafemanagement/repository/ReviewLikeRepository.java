package com.example.cafemanagement.repository;

import com.example.cafemanagement.domain.Review;
import com.example.cafemanagement.domain.ReviewLike;
import com.example.cafemanagement.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Long> {
    Optional<ReviewLike> findByReviewAndUser(Review review, User user);

    int countByReviewId(Long reviewId);
}
