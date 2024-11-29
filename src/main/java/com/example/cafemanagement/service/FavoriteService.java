package com.example.cafemanagement.service;

import com.example.cafemanagement.domain.Cafe;
import com.example.cafemanagement.domain.Favorite;
import com.example.cafemanagement.domain.User;
import com.example.cafemanagement.dto.CafeDto;
import com.example.cafemanagement.dto.LocationDto;
import com.example.cafemanagement.dto.MenuDto;
import com.example.cafemanagement.dto.ReviewDto;
import com.example.cafemanagement.dto.HashtagDto;
import com.example.cafemanagement.repository.CafeRepository;
import com.example.cafemanagement.repository.FavoriteRepository;
import com.example.cafemanagement.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final CafeRepository cafeRepository;

    public FavoriteService(FavoriteRepository favoriteRepository, UserRepository userRepository, CafeRepository cafeRepository) {
        this.favoriteRepository = favoriteRepository;
        this.userRepository = userRepository;
        this.cafeRepository = cafeRepository;
    }

    public void addFavorite(Long userId, Long cafeId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        Cafe cafe = cafeRepository.findById(cafeId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid cafe ID"));

        if (favoriteRepository.findByUser(user).stream().anyMatch(f -> f.getCafe().equals(cafe))) {
            throw new IllegalArgumentException("Cafe is already in favorites");
        }

        Favorite favorite = new Favorite(user, cafe);
        favoriteRepository.save(favorite);
    }

    public void removeFavorite(Long userId, Long cafeId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        Cafe cafe = cafeRepository.findById(cafeId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid cafe ID"));

        List<Favorite> favorites = favoriteRepository.findByUser(user).stream()
                .filter(f -> f.getCafe().equals(cafe))
                .collect(Collectors.toList());

        favoriteRepository.deleteAll(favorites);
    }

    public List<Cafe> getFavorites(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        return favoriteRepository.findByUser(user).stream()
                .map(Favorite::getCafe)
                .collect(Collectors.toList());
    }


}
