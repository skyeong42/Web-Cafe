package com.example.cafemanagement.controller;

import com.example.cafemanagement.dto.CafeDto;
import com.example.cafemanagement.dto.LocationDto;
import com.example.cafemanagement.dto.MenuDto;
import com.example.cafemanagement.dto.ReviewDto;
import com.example.cafemanagement.service.CafeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MainController {

    private final CafeService cafeService;

    @Autowired
    public MainController(CafeService cafeService) {
        this.cafeService = cafeService;
    }

    // 메인 페이지 렌더링
    @GetMapping("/")
    public String showMainPage(Model model) {
        List<CafeDto> cafes = cafeService.getAllCafes();

        // 모델에 카페 리스트 추가
        model.addAttribute("cafes", cafes);
        return "main"; // Thymeleaf 템플릿 이름
    }

    // 카페 검색 처리
    @PostMapping("/search-cafe")
    @ResponseBody
    public List<CafeDto> searchCafe(@RequestBody SearchRequest searchRequest) {
        String query = searchRequest.getQuery();
        return cafeService.searchCafesByName(query);
    }

    // SearchRequest DTO 클래스
    public static class SearchRequest {
        private String query;

        public String getQuery() {
            return query;
        }

        public void setQuery(String query) {
            this.query = query;
        }
    }
}
