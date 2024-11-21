package com.example.cafemanagement.service;

import com.example.cafemanagement.domain.Booking;
import com.example.cafemanagement.domain.Cafe;
import com.example.cafemanagement.domain.User;
import com.example.cafemanagement.dto.BookingDto;
import com.example.cafemanagement.dto.BookingSaveRequestDto;
import com.example.cafemanagement.repository.BookingRepository;
import com.example.cafemanagement.repository.CafeRepository;
import com.example.cafemanagement.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final CafeRepository cafeRepository;

    public BookingService(BookingRepository bookingRepository, UserRepository userRepository, CafeRepository cafeRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.cafeRepository = cafeRepository;
    }

    @Transactional
    public Long createBooking(BookingSaveRequestDto dto) {
        User user = userRepository.findById(dto.getUserId())
                                  .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        Cafe cafe = cafeRepository.findById(dto.getCafeId())
                                  .orElseThrow(() -> new IllegalArgumentException("카페를 찾을 수 없습니다."));

        Booking booking = new Booking(dto.getTitle(), dto.getBookingTime(), dto.getStatus(), user, cafe);
        Booking savedBooking = bookingRepository.save(booking);

        return savedBooking.getBookingId();
    }

    @Transactional
    public void cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                                           .orElseThrow(() -> new IllegalArgumentException("예약을 찾을 수 없습니다."));

        bookingRepository.delete(booking);
    }

    @Transactional(readOnly = true)
    public List<BookingDto> findBookingsByUser(Long userId) {
        return bookingRepository.findByUserId(userId).stream()
                                .map(this::convertToDto)
                                .collect(Collectors.toList());
    }

    @Transactional
    public void updatePickupStatus(Long bookingId, String status) {
        Booking booking = bookingRepository.findById(bookingId)
                                           .orElseThrow(() -> new IllegalArgumentException("예약을 찾을 수 없습니다."));
        booking.setStatus(status);
        bookingRepository.save(booking);
    }

    private BookingDto convertToDto(Booking booking) {
        return new BookingDto(
            booking.getBookingId(),
            booking.getTitle(),
            booking.getBookingTime(),
            booking.getStatus(),
            booking.getUser().getId(),
            booking.getCafe().getCafeId()
        );
    }
}
