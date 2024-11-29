package com.example.cafemanagement.controller;

import com.example.cafemanagement.dto.CafeDto;
import com.example.cafemanagement.dto.LocationDto;
import com.example.cafemanagement.dto.MenuDto;
import com.example.cafemanagement.dto.ReviewDto;
import com.example.cafemanagement.dto.HashtagDto;
import com.example.cafemanagement.service.FavoriteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping("/{userId}/{cafeId}")
    public ResponseEntity<Void> addFavorite(@PathVariable Long userId, @PathVariable Long cafeId) {
        favoriteService.addFavorite(userId, cafeId);
        return ResponseEntity.ok().build();
    }

    // 즐겨찾기 삭제
    @DeleteMapping("/{userId}/{cafeId}")
    public ResponseEntity<Void> removeFavorite(@PathVariable Long userId, @PathVariable Long cafeId) {
        favoriteService.removeFavorite(userId, cafeId);
        return ResponseEntity.ok().build();
    }
/**
    // 즐겨찾기 목록 조회
    @GetMapping("/{userId}")
    public ResponseEntity<List<CafeDto>> getFavorites(@PathVariable Long userId) {
        // favoriteService가 List<CafeDto>를 반환하도록 설계
        List<CafeDto> favoriteCafes = favoriteService.getFavoriteDtos(userId);
        return ResponseEntity.ok(favoriteCafes);
    }
*/


}
