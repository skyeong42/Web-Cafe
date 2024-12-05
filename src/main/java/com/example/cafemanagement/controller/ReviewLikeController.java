package com.example.cafemanagement.controller;

import com.example.cafemanagement.service.ReviewLikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/reviews")
public class ReviewLikeController {

    private final ReviewLikeService reviewLikeService;

    public ReviewLikeController(ReviewLikeService reviewLikeService) {
        this.reviewLikeService = reviewLikeService;
    }

    @PostMapping("/{reviewId}/like")
    public ResponseEntity<Map<String, Object>> toggleLike(
            @PathVariable Long reviewId, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String username = authentication.getName();
        boolean isLiked = reviewLikeService.toggleLike(reviewId, username); // 좋아요 상태 토글

        int updatedLikesCount = reviewLikeService.getLikesCount(reviewId);

        Map<String, Object> response = new HashMap<>();
        response.put("isLiked", isLiked);
        response.put("likesCount", updatedLikesCount);

        return ResponseEntity.ok(response);
    }

}
