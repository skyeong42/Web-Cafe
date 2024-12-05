package com.example.cafemanagement.controller;

import com.example.cafemanagement.dto.CafeDto;
import com.example.cafemanagement.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorites")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @PostMapping
    public ResponseEntity<Void> addFavorite(@RequestParam Long cafeId, Authentication authentication) {
        System.out.println("Received Cafe ID: " + cafeId);
        String username = authentication.getName();
        favoriteService.addFavorite(username, cafeId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> removeFavorite(@RequestParam Long cafeId, Authentication authentication) {
        String username = authentication.getName();
        favoriteService.removeFavorite(username, cafeId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api")
    public ResponseEntity<List<CafeDto>> getFavorites(Authentication authentication) {
        String username = authentication.getName();
        List<CafeDto> favorites = favoriteService.getFavorites(username);
        return ResponseEntity.ok(favorites);
    }

    @GetMapping("/isFavorite")
    @ResponseBody
    public boolean isFavorite(@RequestParam("cafeId") Long cafeId, Authentication authentication) {
        return this.favoriteService.isFavorite(cafeId, authentication.getName());
    }

}
