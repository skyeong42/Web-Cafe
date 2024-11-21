package com.example.cafemanagement.controller;

import com.example.cafemanagement.dto.ReviewDto;
import com.example.cafemanagement.dto.ReviewSaveRequestDto;
import com.example.cafemanagement.dto.ReviewUpdateDto;
import com.example.cafemanagement.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@Tag(name = "리뷰 관리", description = "리뷰 작성, 수정, 삭제 및 조회 API")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    @Operation(summary = "리뷰 작성", description = "카페에 대한 리뷰를 작성합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "리뷰 작성 성공"),
        @ApiResponse(responseCode = "400", description = "리뷰 내용 또는 평점이 유효하지 않음")
    })
    public ResponseEntity<ReviewDto> writeReview(@RequestBody ReviewSaveRequestDto dto) {
        ReviewDto createdReview = reviewService.writeReview(dto);
        return ResponseEntity.ok(createdReview);
    }

    @PutMapping("/{reviewId}")
    @Operation(summary = "리뷰 수정", description = "기존 리뷰를 수정합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "리뷰 수정 성공"),
        @ApiResponse(responseCode = "404", description = "리뷰를 찾을 수 없음")
    })
    public ResponseEntity<ReviewDto> editReview(
        @PathVariable Long reviewId,
        @RequestBody ReviewUpdateDto dto) {
        ReviewDto updatedReview = reviewService.editReview(reviewId, dto);
        return ResponseEntity.ok(updatedReview);
    }

    @DeleteMapping("/{reviewId}")
    @Operation(summary = "리뷰 삭제", description = "리뷰를 삭제합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "리뷰 삭제 성공"),
        @ApiResponse(responseCode = "404", description = "리뷰를 찾을 수 없음")
    })
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/cafe/{cafeId}")
    @Operation(summary = "리뷰 조회", description = "특정 카페에 대한 모든 리뷰를 조회합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "리뷰 조회 성공"),
        @ApiResponse(responseCode = "404", description = "카페를 찾을 수 없음")
    })
    public ResponseEntity<List<ReviewDto>> getReviewsForCafe(@PathVariable Long cafeId) {
        List<ReviewDto> reviews = reviewService.getReviewsForCafe(cafeId);
        return ResponseEntity.ok(reviews);
    }
}
