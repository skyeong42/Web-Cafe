package com.example.cafemanagement.service;

import com.example.cafemanagement.domain.Cafe;
import com.example.cafemanagement.domain.Review;
import com.example.cafemanagement.domain.User;
import com.example.cafemanagement.dto.ReviewDto;
import com.example.cafemanagement.dto.ReviewSaveRequestDto;
import com.example.cafemanagement.dto.ReviewUpdateDto;
import com.example.cafemanagement.repository.CafeRepository;
import com.example.cafemanagement.repository.ReviewRepository;
import com.example.cafemanagement.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final CafeRepository cafeRepository;

    public ReviewService(ReviewRepository reviewRepository, UserRepository userRepository, CafeRepository cafeRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.cafeRepository = cafeRepository;
    }

    /**
     * 리뷰 작성
     */
    @Transactional
    public ReviewDto writeReview(ReviewSaveRequestDto dto, String username) {
        // 사용자를 username으로 조회
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Cafe cafe = cafeRepository.findById(dto.getCafeId())
                .orElseThrow(() -> new IllegalArgumentException("cafe not found for id: " + dto.getCafeId()));

        Review review = new Review(
                dto.getTitle(),
                dto.getContent(),
                dto.getRating(),
                user,
                cafe
        );

        Review savedReview = reviewRepository.save(review);

        // 카페 평점 업데이트
        updateCafeRating(cafe);

        return convertToDto(savedReview);
    }

    /**
     * 리뷰 수정
     */
    @Transactional
    public ReviewDto editReview(Long reviewId, ReviewUpdateDto reviewUpdateDto) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));

        if (reviewUpdateDto.getContent().length() < 20) {
            throw new IllegalArgumentException("리뷰는 최소 20자 이상이어야 합니다.");
        }

        review.setTitle(reviewUpdateDto.getTitle());
        review.setContent(reviewUpdateDto.getContent());
        review.setRating(reviewUpdateDto.getRating());
        Review updatedReview = reviewRepository.save(review);

        updateCafeRating(review.getCafe());

        return convertToDto(updatedReview);
    }

    /**
     * 리뷰 삭제
     */
    @Transactional
    public void deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));
        updateCafeRating(review.getCafe());
        reviewRepository.delete(review);
    }

    /**
     * 특정 카페의 모든 리뷰 조회
     */
    @Transactional(readOnly = true)
    public List<ReviewDto> getReviewsForCafe(Long cafeId) {
        Cafe cafe = cafeRepository.findById(cafeId).orElseThrow(() -> new IllegalArgumentException("cafe not found for id: " + cafeId));
        return reviewRepository.findByCafe(cafe).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * DTO 변환 로직
     */
    private ReviewDto convertToDto(Review review) {
        return new ReviewDto(
                review.getId(),
                review.getTitle(),
                review.getContent(),
                review.getRating(),
                review.getUserId(),
                review.getCafe().getCafeId()
        );
    }

    private void updateCafeRating(Cafe cafe) {
        List<Review> reviews = reviewRepository.findByCafe(cafe);
        double averageRating = reviews.stream()
                .mapToDouble(Review::getRating)
                .average()
                .orElse(0.0);

        cafe.setRating(averageRating);
        cafeRepository.save(cafe);
    }
}
