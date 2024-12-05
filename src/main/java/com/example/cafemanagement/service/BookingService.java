package com.example.cafemanagement.service;

import com.example.cafemanagement.domain.*;
import com.example.cafemanagement.dto.BookingDto;
import com.example.cafemanagement.dto.BookingSaveRequestDto;
import com.example.cafemanagement.dto.MenuRequestDto;
import com.example.cafemanagement.repository.BookingRepository;
import com.example.cafemanagement.repository.CafeRepository;
import com.example.cafemanagement.repository.MenuRepository;
import com.example.cafemanagement.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final CafeRepository cafeRepository;
    private final MenuRepository menuRepository;

    public BookingService(BookingRepository bookingRepository, UserRepository userRepository, CafeRepository cafeRepository, MenuRepository menuRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.cafeRepository = cafeRepository;
        this.menuRepository = menuRepository;
    }

    @Transactional
    public Long createBooking(BookingSaveRequestDto dto) {
        // 사용자 조회
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 카페 조회
        Cafe cafe = cafeRepository.findById(dto.getCafeId())
                .orElseThrow(() -> new IllegalArgumentException("카페를 찾을 수 없습니다."));

        // Booking 객체 생성
        Booking booking = new Booking(dto.getTitle(), dto.getBookingTime(), dto.getStatus(), user, cafe, new ArrayList<>());

        // MenuRequestDto를 BookingMenu로 변환하고 Booking에 추가
        for (MenuRequestDto menuDto : dto.getMenuList()) {
            Menu menu = menuRepository.findById(menuDto.getMenuId())
                    .orElseThrow(() -> new IllegalArgumentException("메뉴를 찾을 수 없습니다."));

            BookingMenu bookingMenu = new BookingMenu(booking, menu, menuDto.getMenuCount());
            booking.getBookingMenus().add(bookingMenu); // Booking에 BookingMenu 추가
        }

        // Booking 저장
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
