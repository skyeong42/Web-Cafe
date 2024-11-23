package com.example.cafemanagement.service;

import com.example.cafemanagement.domain.Cafe;
import com.example.cafemanagement.domain.Category;
import com.example.cafemanagement.domain.Location;
import com.example.cafemanagement.domain.Menu;
import com.example.cafemanagement.dto.*;
import com.example.cafemanagement.repository.CafeRepository;
import com.example.cafemanagement.repository.CategoryRepository;
import com.example.cafemanagement.repository.MenuRepository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CafeService {

    private final CafeRepository cafeRepository;
    private final MenuRepository menuRepository;
    private final CategoryRepository categoryRepository;
    private final String apiKey = "19dc7cdd122bfe1e88e7c68cdc00dd42"; // 카카오 REST API 키
    private final String apiUrl = "https://dapi.kakao.com/v2/local/search/keyword.json";


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
                        cafe.getCafeId(),
                        review.getAttachments().stream()
                                .collect(Collectors.toList())
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

    /**
     * 카카오 지도 API를 사용하여 키워드로 카페 검색
     *
     * @param keyword 검색 키워드 (예: "카페")
     * @return 검색된 카페 정보 리스트
     */
    public List<KakaoCafeDto> searchCafes(String keyword) {
        List<KakaoCafeDto> cafeList = new ArrayList<>();

        try {
            // 1. 키워드 URL 인코딩
            String query = URLEncoder.encode(keyword, StandardCharsets.UTF_8.toString());
            String fullUrl = apiUrl + "?query=" + query + "&category_group_code=CE7"; // 카페 카테고리 코드 CE7

            // 2. HTTP 연결 설정
            URL url = new URL(fullUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "KakaoAK " + apiKey);

            // 3. API 응답 읽기
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
            }
            rd.close();

            // 4. JSON 파싱
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.toString());
            JsonNode documents = jsonNode.get("documents");

            // 5. 결과 매핑
            if (documents != null) {
                documents.forEach(doc -> {
                    KakaoCafeDto cafeDto = new KakaoCafeDto();
                    cafeDto.setPlaceName(doc.get("place_name").asText());
                    cafeDto.setAddressName(doc.get("address_name").asText());
                    cafeDto.setPhone(doc.get("phone").asText());
                    cafeDto.setX(doc.get("x").asText());
                    cafeDto.setY(doc.get("y").asText());
                    cafeList.add(cafeDto);
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return cafeList;
    }
}
