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

    /**
     * 추가
     * */
    // 최소 별점 필터
    List<Cafe> findByRatingGreaterThanEqual(double rating);

    // 해시태그와 필터링 (JPQL 사용)
    @Query("SELECT DISTINCT c FROM Cafe c LEFT JOIN c.hashtags h " +
            "WHERE (:category IS NULL OR LOWER(c.category.categoryName) = LOWER(:category)) " +
            "AND (:minRating IS NULL OR c.rating >= :minRating) " +
            "AND (:maxRating IS NULL OR c.rating < :maxRating) " +
            "AND (:keyword IS NULL OR LOWER(c.cafeName) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND (:hashtags IS NULL OR h.tagName IN :hashtags) " +
            "GROUP BY c " +
            "HAVING (:hashtagCount = 0 OR COUNT(DISTINCT h.tagName) = :hashtagCount)")
    List<Cafe> findByFilters(
            @Param("category") String category,
            @Param("minRating") Double minRating,
            @Param("maxRating") Double maxRating,
            @Param("keyword") String keyword,
            @Param("hashtags") List<String> hashtags,
            @Param("hashtagCount") long hashtagCount
    );



}

