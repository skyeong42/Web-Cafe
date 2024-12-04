package com.example.cafemanagement.repository;

import com.example.cafemanagement.domain.Cafe;
import com.example.cafemanagement.domain.Favorite;
import com.example.cafemanagement.domain.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO favorites (user_id, cafe_id) VALUES (:userId, :cafeId)", nativeQuery = true)
    void addFavorite(@Param("userId") Long userId, @Param("cafeId") Long cafeId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM favorites WHERE user_id = :userId AND cafe_id = :cafeId", nativeQuery = true)
    void removeFavorite(@Param("userId") Long userId, @Param("cafeId") Long cafeId);

    @Query(value = """
    SELECT c.cafe_id AS cafeId, 
           c.cafe_name AS cafeName, 
           loc.latitude AS latitude, 
           loc.longitude AS longitude, 
           loc.address AS address, 
           c.rating AS rating, 
           c.description AS description, 
           cat.category_name AS category, 
           c.cafe_image_url AS imageUrl
    FROM cafe c
    INNER JOIN favorites f ON c.cafe_id = f.cafe_id
    LEFT JOIN categories cat ON c.category_id = cat.category_id -- 수정된 부분
    LEFT JOIN locations loc ON c.location_id = loc.id
    WHERE f.user_id = :userId
    """, nativeQuery = true)
    List<Object[]> findFavoritesByUserId(@Param("userId") Long userId);

    boolean existsByUserAndCafe(User user, Cafe cafe);

}

