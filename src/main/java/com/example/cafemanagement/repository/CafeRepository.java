package com.example.cafemanagement.repository;

import com.example.cafemanagement.domain.Cafe;
import com.example.cafemanagement.domain.Category;
import com.example.cafemanagement.domain.Location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CafeRepository extends JpaRepository<Cafe, Long> {


    List<Cafe> findByLocation(Location location);
    List<Cafe> findByCafeName(String keyword);
    List<Cafe> findByCategory(Category category);
    List<Cafe> findByCafeNameContainingIgnoreCase(String name);
    @Query("SELECT c FROM Cafe c " +
            "WHERE (:category IS NULL OR c.category.categoryName = :category) " +
            "AND (:minRating IS NULL OR c.rating >= :minRating) " +
            "AND (:keyword IS NULL OR c.cafeName LIKE %:keyword% OR c.description LIKE %:keyword%) " +
            "AND (:hashtags IS NULL OR EXISTS (SELECT h FROM c.hashtags h WHERE h.name IN :hashtags))")
    List<Cafe> findFilteredCafes(
            @Param("category") String category,
            @Param("hashtags") List<String> hashtags,
            @Param("minRating") Double minRating,
            @Param("keyword") String keyword);

    // Wrapper 메서드
    default List<Cafe> filterCafes(String category, List<String> hashtags, Double minRating, String keyword) {
        return findFilteredCafes(category, hashtags, minRating, keyword);
    }
}

