package com.example.cafemanagement.controller;

import com.example.cafemanagement.domain.Category;
import com.example.cafemanagement.dto.*;
import com.example.cafemanagement.service.CafeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cafes")
@Tag(name = "카페 관리", description = "카페 추가, 조회, 검색 등 카페 관련 API")
public class CafeController {

    private final CafeService cafeService;

    public CafeController(CafeService cafeService) {
        this.cafeService = cafeService;
    }

    @PostMapping
    @Operation(summary = "카페 등록", description = "새로운 카페를 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "카페 등록 성공"),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 요청 데이터")
    })
    public ResponseEntity<Long> addCafe(@RequestBody @Parameter(description = "카페 정보", required = true) CafeRequestDto requestDto) {
        Long cafeId = cafeService.addCafe(requestDto);
        return ResponseEntity.ok(cafeId);
    }

    @GetMapping("/{cafeId}")
    @Operation(summary = "카페 상세 조회", description = "특정 카페의 상세 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "카페 상세 정보 조회 성공"),
            @ApiResponse(responseCode = "404", description = "카페를 찾을 수 없음")
    })
    public ResponseEntity<CafeDto> getCafeDetails(
            @PathVariable @Parameter(description = "카페 ID", required = true) Long cafeId) {
        CafeDto cafe = cafeService.getCafeDetails(cafeId);
        return ResponseEntity.ok(cafe);
    }



    @GetMapping("/categories")
    @Operation(summary = "카테고리 조회", description = "모든 카페 카테고리를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "카테고리 조회 성공")
    })
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> categories = cafeService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/categories/{categoryName}")
    @Operation(summary = "카페 카테고리별 조회", description = "특정 카테고리의 카페를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "카페 조회 성공"),
            @ApiResponse(responseCode = "404", description = "카테고리를 찾을 수 없음")
    })
    public ResponseEntity<List<CafeDto>> getCafesByCategory(
            @PathVariable @Parameter(description = "카테고리 이름", required = true) String categoryName) {
        List<CafeDto> cafes = cafeService.getCafesByCategory(categoryName);
        return ResponseEntity.ok(cafes);
    }

    @GetMapping("/popular")
    @Operation(summary = "추천 카페 조회", description = "평점 또는 조회수가 높은 인기 카페 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인기 카페 조회 성공")
    })
    public ResponseEntity<List<CafeDto>> getPopularCafes() {
        List<CafeDto> popularCafes = cafeService.getPopularCafes();
        return ResponseEntity.ok(popularCafes);
    }

    @PutMapping("/{cafeId}")
    @Operation(summary = "카페 정보 수정", description = "특정 카페의 정보를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "카페 수정 성공"),
            @ApiResponse(responseCode = "404", description = "카페를 찾을 수 없음")
    })
    public ResponseEntity<Void> updateCafe(
            @PathVariable @Parameter(description = "수정할 카페 ID", required = true) Long cafeId,
            @RequestBody @Parameter(description = "수정할 카페 정보", required = true) CafeDto dto) {
        cafeService.updateCafe(cafeId, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{cafeId}")
    @Operation(summary = "카페 삭제", description = "특정 카페를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "카페 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "카페를 찾을 수 없음")
    })
    public ResponseEntity<Void> deleteCafe(
            @PathVariable @Parameter(description = "삭제할 카페 ID", required = true) Long cafeId) {
        cafeService.deleteCafe(cafeId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/kakao/search")
    @Operation(summary = "카카오 지도 API로 카페 검색", description = "카카오 지도 API를 사용하여 카페 정보를 검색합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "카페 검색 성공"),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 요청")
    })
    public ResponseEntity<List<KakaoCafeDto>> searchCafesFromKakao(
            @RequestParam @Parameter(description = "검색 키워드", required = true) String keyword) {
        List<KakaoCafeDto> cafes = cafeService.searchCafes(keyword);
        return ResponseEntity.ok(cafes);
    }

    @GetMapping("/location")
    @Operation(summary = "위치 기반 카페 조회", description = "주어진 위치를 기준으로 카페를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "위치 기반 카페 조회 성공"),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 위치 데이터")
    })
    public ResponseEntity<List<CafeDto>> findCafesByLocation(
            @RequestBody @Parameter(description = "위치 정보", required = true) LocationDto location) {
        List<CafeDto> cafes = cafeService.findCafesByLocation(location);
        return ResponseEntity.ok(cafes);
    }

    // 매장찾기 페이지
    @GetMapping("/find-store")
    @Operation(summary = "Find Store Page", description = "Find store page rendering")
    public String renderFindStorePage() {
        return "find-store"; // Returns the Thymeleaf template for the find-store page
    }

    //카테고리, 해시태그, 별점 필터
    //추가
    @GetMapping("/filters")
    @Operation(summary = "Filter Cafes", description = "Filters cafes from internal DB only.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Filter success"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    public ResponseEntity<List<CafeDto>> filterCafes(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) List<String> hashtags,
            @RequestParam(required = false) Double minRating,
            @RequestParam(required = false) String keyword
    ) {
        try {
            // Null 또는 빈 값 처리
            category = (category != null && !category.isBlank()) ? category : null;
            keyword = (keyword != null && !keyword.isBlank()) ? keyword : null;
            hashtags = (hashtags != null && !hashtags.isEmpty()) ? hashtags : List.of();

            // 서비스 호출 (내부 DB 필터링 전용)
            List<CafeDto> filteredResults = cafeService.filterCafes(category, hashtags, minRating, keyword);

            return ResponseEntity.ok(filteredResults);
        } catch (Exception e) {
            // 로깅은 시스템에 따라 변경 가능
            System.err.println("Error in filterCafes: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }







    @PostMapping("/save-search")
    @Operation(summary = "Save Search Results", description = "Save search results for cafes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Save success"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    public ResponseEntity<Void> saveSearchResults(@RequestBody List<CafeDto> cafes) {
        cafeService.saveSearchResults(cafes);
        return ResponseEntity.ok().build();
    }

    // 키워드 중심 검색
    @GetMapping("/search")
    @Operation(summary = "Search and filter cafes", description = "Search cafes using keyword, and apply filters from DB.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search success"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    public ResponseEntity<List<CafeDto>> searchAndFilterCafes(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) List<String> hashtags,
            @RequestParam(required = false) Double minRating
    ) {
        List<CafeDto> results = cafeService.searchAndFilterCafes(keyword, category, hashtags, minRating);
        return ResponseEntity.ok(results);
    }



}
