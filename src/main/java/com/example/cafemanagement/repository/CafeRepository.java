package com.example.cafemanagement.repository;

import com.example.cafemanagement.domain.Cafe;
import com.example.cafemanagement.domain.Category;
import com.example.cafemanagement.domain.Location;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CafeRepository extends JpaRepository<Cafe, Long> {


    List<Cafe> findByLocation(Location location);
    List<Cafe> findByCafeName(String keyword);
    List<Cafe> findByCategory(Category category);
    List<Cafe> findByCafeNameContainingIgnoreCase(String name);

}

