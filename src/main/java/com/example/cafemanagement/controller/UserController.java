package com.example.cafemanagement.controller;

import com.example.cafemanagement.dto.LoginDto;
import com.example.cafemanagement.dto.UserSaveRequestDto;
import com.example.cafemanagement.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
    public String registerUser(UserSaveRequestDto userDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "registration_form"; // 에러 발생 시 폼 재렌더링
        }

        try {
            userService.registerUser(userDto);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "registration_form"; // 에러 메시지와 함께 폼 재렌더링
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
    public String loginUser(LoginDto loginDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("errorMessage", "로그인 정보를 다시 확인해주세요.");
            return "login";
        }

        try {
            String token = userService.login(loginDto.getUsername(), loginDto.getPassword());
            return "redirect:/"; // 로그인 성공 시 메인 페이지로 리다이렉트
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "login"; // 에러 메시지와 함께 로그인 폼 재렌더링
        }
    }

}
