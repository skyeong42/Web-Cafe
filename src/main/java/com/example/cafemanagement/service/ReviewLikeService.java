package com.example.cafemanagement.service;

import com.example.cafemanagement.domain.Review;
import com.example.cafemanagement.domain.ReviewLike;
import com.example.cafemanagement.domain.User;
import com.example.cafemanagement.repository.ReviewLikeRepository;
import com.example.cafemanagement.repository.ReviewRepository;
import com.example.cafemanagement.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewLikeService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ReviewLikeRepository reviewLikeRepository;

    public ReviewLikeService(ReviewRepository reviewRepository, UserRepository userRepository, ReviewLikeRepository reviewLikeRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.reviewLikeRepository = reviewLikeRepository;
    }

    @Transactional
    public boolean toggleLike(Long reviewId, String username) {
        // 사용자와 리뷰 조회
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));

        // 이미 좋아요 상태인지 확인
        var existingLike = reviewLikeRepository.findByReviewAndUser(review, user);

        if (existingLike.isPresent()) {
            // 좋아요 취소
            reviewLikeRepository.delete(existingLike.get());
            return false; // 좋아요 취소됨
        } else {
            // 좋아요 추가
            ReviewLike like = new ReviewLike(review, user);
            reviewLikeRepository.save(like);
            return true; // 좋아요 추가됨
        }
    }

    public int getLikesCount(Long reviewId) {
        return reviewLikeRepository.countByReviewId(reviewId);
    }
}
