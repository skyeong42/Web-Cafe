package com.example.cafemanagement.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.coyote.BadRequestException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.cafemanagement.config.InMemoryTokenBlacklist;
import com.example.cafemanagement.domain.User;
import com.example.cafemanagement.dto.BookingDto;
import com.example.cafemanagement.dto.ReviewDto;
import com.example.cafemanagement.dto.UserProfileDetailsDto;
import com.example.cafemanagement.dto.UserSaveRequestDto;
import com.example.cafemanagement.dto.UserUpdateRequestDto;
import com.example.cafemanagement.repository.UserRepository;
import com.example.cafemanagement.util.JwtUtil;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final InMemoryTokenBlacklist inMemoryTokenBlacklist;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, InMemoryTokenBlacklist inMemoryTokenBlacklist, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.inMemoryTokenBlacklist = inMemoryTokenBlacklist;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    public User registerUser(UserSaveRequestDto request) {
        validateRegistration(request);
        User user = new User(
                request.getUsername(),
                passwordEncoder.encode(request.getPassword()),
                request.getNickname(),
                request.getGender(),
                request.getEmail()

        );
        return userRepository.save(user);
    }

    private void validateRegistration(UserSaveRequestDto request) {
        if (request.getPassword().length() < 8) {
            throw new IllegalArgumentException("비밀번호는 12자 이상 20자 이하로 입력해야 합니다.");
        }
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 등록된 이메일입니다.");
        }
        if (!validatePasswordStrength(request.getPassword())) {
            throw new IllegalArgumentException("비밀번호는 영문자, 숫자, 특수문자를 포함해야 합니다.");
        }
        if (!validatePasswordMatch(request.getPassword(), request.getConfirmPassword())) {
            throw new IllegalArgumentException("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }
    }

    @Transactional(readOnly = true)
    public boolean checkUsernameAvailability(String username) {
        return !userRepository.existsByUsername(username);
    }

    public boolean validatePasswordStrength(String password) {
        // 비밀번호가 영문자, 숫자, 특수문자를 포함하는지 정규식을 사용하여 검증
        return password.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&+=]).{12,20}$");
    }

    public boolean validatePasswordMatch(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    @Transactional
    public String login(String username, String rawPassword) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));
        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
        }
        return jwtUtil.generateToken(username);
    }

    @Transactional
    public void logout(String token) {
        inMemoryTokenBlacklist.addToBlacklist(token, System.currentTimeMillis());
    }

    @Transactional(readOnly = true)
    public UserProfileDetailsDto getUserProfileWithDetails(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        // 리뷰 DTO 변환
        List<ReviewDto> reviews = user.getReviews().stream()
                .map(review -> new ReviewDto(
                        review.getReviewId(),
                        review.getTitle(),
                        review.getContent(),
                        review.getRating(),
                        user.getId(),
                        review.getCafe().getId())).toList();

        // 예약 DTO 변환
        List<BookingDto> bookings = user.getBookings().stream()
                .map(booking -> new BookingDto(
                        booking.getBookingId(),
                        booking.getTitle(),
                        booking.getBookingTime(),
                        booking.getStatus(),
                        user.getId(),
                        booking.getCafe().getId()))
                .collect(Collectors.toList());

        return new UserProfileDetailsDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getNickname(),
                user.getProfilePicture(),
                reviews,
                bookings
        );
    }


    @Transactional
    public UserUpdateRequestDto updateUserInfo(String username, UserUpdateRequestDto requestDto) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        if (requestDto.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        }
        if (requestDto.getEmail() != null) {
            user.setEmail(requestDto.getEmail());
        }
        if (requestDto.getNickname() != null) {
            user.setNickname(requestDto.getNickname());
        }

        userRepository.save(user);

        return new UserUpdateRequestDto(user.getPassword(), user.getEmail(), user.getNickname(), user.getProfilePicture());
    }

    public List<User> getSearchedUser(String keyword, String myname) {
        return this.userRepository.findByKeyword(keyword, myname);
    }
}


