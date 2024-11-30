package com.example.cafemanagement.controller;

import com.example.cafemanagement.dto.LoginDto;
import com.example.cafemanagement.dto.UserSaveRequestDto;
import com.example.cafemanagement.service.UserService;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원가입 폼을 렌더링
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userDto", new UserSaveRequestDto());
        return "registration_form"; // Thymeleaf 템플릿 이름
    }

    // 회원가입 처리
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("userDto") UserSaveRequestDto userDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "registration_form"; // 에러 발생 시 폼 재렌더링
        }

        try {
            userService.registerUser(userDto); // 회원가입 처리
        } catch (IllegalArgumentException e) {
            // 예상된 예외 발생 시
            model.addAttribute("errorMessage", e.getMessage());
            return "registration_form"; // 에러 메시지와 함께 페이지 재렌더링
        } catch (Exception e) {
            // 기타 예외 발생 시
            model.addAttribute("errorMessage", "알 수 없는 에러가 발생했습니다. 다시 시도해주세요.");
            return "registration_form";
        }
        return "redirect:/login"; // 성공 시 로그인 페이지로 이동
    }

    // 로그인 폼을 렌더링
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("loginDto", new LoginDto());
        return "login"; // Thymeleaf 템플릿 이름
    }

    // 로그인 처리
    @PostMapping("/login")
    public String loginUser(Model model, LoginDto loginDto, BindingResult result, HttpServletResponse response) {
        if (result.hasErrors()) {
            return "login";
        }

        try {
            String token = userService.login(loginDto.getUsername(), loginDto.getPassword());

            // JWT를 쿠키에 저장
            Cookie jwtCookie = new Cookie("Authorization", token);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setPath("/");
            jwtCookie.setMaxAge(60 * 60 * 24); // 1일 동안 유효
            response.addCookie(jwtCookie);

            return "redirect:/";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", "아이디 또는 비밀번호가 잘못되었습니다.");
            return "login";
        }
    }

}
