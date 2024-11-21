package com.example.cafemanagement.repository;

import com.example.cafemanagement.domain.Cafe;
import com.example.cafemanagement.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByCafe(Cafe cafe);
}
