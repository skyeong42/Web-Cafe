package com.example.cafemanagement.repository;

import com.example.cafemanagement.domain.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserId(Long userId);
    List<Booking> findByCafeId(Long cafeId);
    List<Booking> findByCafeIdAndBookingTime(Long cafeId, LocalDateTime date);
}
