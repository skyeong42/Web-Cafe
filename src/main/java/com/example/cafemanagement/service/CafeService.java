package com.example.cafemanagement.service;

import com.example.cafemanagement.domain.Cafe;
import com.example.cafemanagement.domain.Category;
import com.example.cafemanagement.domain.Location;
import com.example.cafemanagement.domain.Menu;
import com.example.cafemanagement.dto.CafeDto;
import com.example.cafemanagement.dto.CafeRequestDto;
import com.example.cafemanagement.dto.CategoryDto;
import com.example.cafemanagement.dto.LocationDto;
import com.example.cafemanagement.dto.MenuDto;
import com.example.cafemanagement.dto.ReviewDto;
import com.example.cafemanagement.dto.ReviewResponseDto;
import com.example.cafemanagement.repository.CafeRepository;
import com.example.cafemanagement.repository.CategoryRepository;
import com.example.cafemanagement.repository.MenuRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CafeService {

    private final CafeRepository cafeRepository;
    private final MenuRepository menuRepository;
    private final CategoryRepository categoryRepository;

    public CafeService(CafeRepository cafeRepository, MenuRepository menuRepository, CategoryRepository categoryRepository) {
        this.cafeRepository = cafeRepository;
        this.menuRepository = menuRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public Long addCafe(CafeRequestDto dto) {
        // 카테고리와 위치 생성
        Category category = new Category(dto.getCategory());
        Location location = Location.of(dto.getLocation());

        // 카페 생성 및 저장
        Cafe cafe = new Cafe(dto.getCafeName(), location, 0L, dto.getDescription(), dto.getCafeImageUrl(), category);
        Cafe savedCafe = cafeRepository.save(cafe);

        // 메뉴 추가
        dto.getMenus().forEach(menuDto -> menuRepository.save(new Menu(savedCafe, menuDto.getName(), menuDto.getPrice())));

        return savedCafe.getCafeId();
    }


    @Transactional(readOnly = true)
    public CafeDto getCafeDetails(Long cafeId) {
        Cafe cafe = cafeRepository.findById(cafeId)
                .orElseThrow(() -> new IllegalArgumentException("카페를 찾을 수 없습니다."));

        List<MenuDto> menus = menuRepository.findByCafeId(cafeId).stream()
                .map(menu -> new MenuDto(menu.getName(), menu.getPrice()))
                .collect(Collectors.toList());

        return new CafeDto(cafe.getCafeId(), cafe.getCafeName(), LocationDto.of(cafe.getLocation()), cafe.getRating(), cafe.getDescription(), cafe.getCategory().getCategoryName(),
                cafe.getCafeImageUrl(), menus, cafe.getReviews().stream().map(review -> ReviewDto.of(review, cafeId)).collect(Collectors.toList()));
    }

    @Transactional(readOnly = true)
    public List<CafeDto> searchCafes(String keyword, String category, String hashtag, Double minRating) {
        List<Cafe> filteredCafes = cafeRepository.findAll().stream()
                .filter(cafe -> (keyword == null || cafe.getCafeName().contains(keyword) || cafe.getDescription().contains(keyword)))
                .filter(cafe -> (category == null || cafe.getCategory().getCategoryName().equalsIgnoreCase(category)))
                .filter(cafe -> (minRating == null || cafe.getRating() >= minRating))
                .collect(Collectors.toList());

        return filteredCafes.stream()
                .map(cafe -> new CafeDto(
                        cafe.getCafeId(),
                        cafe.getCafeName(),
                        LocationDto.of(cafe.getLocation()),
                        cafe.getRating(),
                        cafe.getDescription(),
                        cafe.getCategory().getCategoryName(),
                        cafe.getCafeImageUrl(),
                        cafe.getMenus().stream().map(MenuDto::of).toList(),
                        cafe.getReviews().stream().map(review -> ReviewDto.of(review, cafe.getId())).toList())
                ).toList();
    }

    public List<CafeDto> searchCafesByName(String query) {
        return cafeRepository.findByCafeNameContainingIgnoreCase(query).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<CafeDto> getAllCafes() {
        return cafeRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public List<CafeDto> findCafesByLocation(LocationDto location) {
        return cafeRepository.findByLocation(Location.of(location)).stream()
                .map(cafe -> new CafeDto(
                        cafe.getCafeId(),
                        cafe.getCafeName(),
                        LocationDto.of(cafe.getLocation()),
                        cafe.getRating(),
                        cafe.getDescription(),
                        cafe.getCategory().getCategoryName(),
                        cafe.getCafeImageUrl(),
                        cafe.getMenus().stream().map(MenuDto::of).toList(),
                        cafe.getReviews().stream().map(review -> ReviewDto.of(review, cafe.getId())).toList())
                ).toList();
    }

    @Transactional(readOnly = true)
    public List<CafeDto> getCafesByCategory(String categoryName) {
        Category category = categoryRepository.findByCategoryName(categoryName).orElse(null);
        return cafeRepository.findByCategory(category).stream()
                .map(cafe -> new CafeDto(
                        cafe.getCafeId(),
                        cafe.getCafeName(),
                        LocationDto.of(cafe.getLocation()),
                        cafe.getRating(),
                        cafe.getDescription(),
                        cafe.getCategory().getCategoryName(),
                        cafe.getCafeImageUrl(),
                        cafe.getMenus().stream().map(MenuDto::of).toList(),
                        cafe.getReviews().stream().map(review -> ReviewDto.of(review, cafe.getId())).toList())
                ).toList();
    }

    @Transactional
    public void updateCafe(Long cafeId, CafeDto dto) {
        Cafe cafe = cafeRepository.findById(cafeId)
                .orElseThrow(() -> new IllegalArgumentException("카페를 찾을 수 없습니다."));

        Category category = categoryRepository.findByCategoryName(dto.getCategory()).orElse(categoryRepository.save(new Category(dto.getCategory())));

        cafe.updateDetails(dto.getCafeName(), Location.of(dto.getLocation()), dto.getRating(), dto.getDescription(), category);

        cafeRepository.save(cafe); // 변경된 관계 저장
    }

    @Transactional
    public void deleteCafe(Long cafeId) {
        cafeRepository.deleteById(cafeId);
    }

    @Transactional(readOnly = true)
    public List<CafeDto> getPopularCafes() {
        return cafeRepository.findAll().stream()
                .sorted(Comparator.comparing(Cafe::getRating).reversed())
                .limit(10)
                .map(cafe -> new CafeDto(
                        cafe.getCafeId(),
                        cafe.getCafeName(),
                        LocationDto.of(cafe.getLocation()),
                        cafe.getRating(),
                        cafe.getDescription(),
                        cafe.getCategory().getCategoryName(),
                        cafe.getCafeImageUrl(),
                        cafe.getMenus().stream().map(MenuDto::of).toList(),
                        cafe.getReviews().stream().map(review -> ReviewDto.of(review, cafe.getId())).toList())
                ).toList();
    }

    @Transactional(readOnly = true)
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream().map(category -> new CategoryDto(category.getCategoryName())).toList();

    }

    private CafeDto convertToDto(Cafe cafe) {
        // LocationDto 생성
        LocationDto locationDto = new LocationDto(
                cafe.getLocation().getLatitude(),
                cafe.getLocation().getLongitude(),
                cafe.getLocation().getAddress()
        );

        // MenuDto 리스트 생성
        List<MenuDto> menuDtos = cafe.getMenus().stream()
                .map(menu -> new MenuDto(menu.getName(), menu.getPrice()))
                .collect(Collectors.toList());

        // ReviewDto 리스트 생성
        List<ReviewDto> reviewDtos = cafe.getReviews().stream()
                .map(review -> new ReviewDto(
                        review.getReviewId(),
                        review.getTitle(),
                        review.getContent(),
                        review.getRating(),
                        review.getUser().getId(),
                        cafe.getCafeId()
                ))
                .collect(Collectors.toList());

        return new CafeDto(
                cafe.getCafeId(),
                cafe.getCafeName(),
                locationDto,
                cafe.getRating(),
                cafe.getDescription(),
                cafe.getCategory().getCategoryName(),
                cafe.getCafeImageUrl(),
                menuDtos,
                reviewDtos
        );
    }

    public List<ReviewResponseDto> getCafeReviews(Long cafeId) {
        Cafe cafe = cafeRepository.findById(cafeId)
                .orElseThrow(() -> new EntityNotFoundException("Cafe not found"));
        return cafe.getReviews().stream()
                .map(review -> ReviewResponseDto.of(review, cafe.getId()))
                .collect(Collectors.toList());
    }

    public List<MenuDto> getCafeMenus(Long cafeId) {
        Cafe cafe = cafeRepository.findById(cafeId)
                .orElseThrow(() -> new EntityNotFoundException("Cafe not found"));
        return cafe.getMenus().stream()
                .map(MenuDto::of).toList();
    }
}
