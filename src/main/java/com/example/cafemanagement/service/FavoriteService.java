package com.example.cafemanagement.service;

import com.example.cafemanagement.domain.Cafe;
import com.example.cafemanagement.domain.User;
import com.example.cafemanagement.dto.CafeDto;
import com.example.cafemanagement.dto.LocationDto;
import com.example.cafemanagement.repository.CafeRepository;
import com.example.cafemanagement.repository.FavoriteRepository;
import com.example.cafemanagement.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CafeRepository cafeRepository;


    private final CafeService cafeService;

    @Autowired
    public FavoriteService(FavoriteRepository favoriteRepository,
                           UserRepository userRepository,
                           CafeService cafeService) {
        this.favoriteRepository = favoriteRepository;
        this.userRepository = userRepository;
        this.cafeService = cafeService;
    }

    public void addFavorite(String username, Long cafeId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Cafe cafe = cafeRepository.findById(cafeId)
                .orElseThrow(() -> new EntityNotFoundException("Cafe not found"));
        if (favoriteRepository.existsByUserAndCafe(user, cafe)) {
            throw new IllegalStateException("이미 즐겨찾기에 추가된 카페입니다.");
        }
        favoriteRepository.addFavorite(user.getId(), cafe.getId());
    }

    public void removeFavorite(String username, Long cafeId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        favoriteRepository.removeFavorite(user.getId(), cafeId);
    }

    public List<CafeDto> getFavorites(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<Object[]> favorites = favoriteRepository.findFavoritesByUserId(user.getId());

        return favorites.stream()
                .map(row -> new CafeDto(
                        ((Number) row[0]).longValue(), // cafeId
                        (String) row[1],              // cafeName
                        new LocationDto(
                                ((Number) row[2]).doubleValue(), // latitude
                                ((Number) row[3]).doubleValue(), // longitude
                                (String) row[4]                 // address
                        ),
                        ((Number) row[5]).doubleValue(),    // rating
                        (String) row[6],                   // description
                        (String) row[7],                   // category
                        (String) row[8],                   // imageUrl
                        null,                              // menus (필요 시 추가)
                        null                               // reviewDtos (필요 시 추가)
                ))
                .collect(Collectors.toList());
    }

}
