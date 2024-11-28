package com.example.cafemanagement.service;

import com.example.cafemanagement.domain.*;
import com.example.cafemanagement.dto.*;
import com.example.cafemanagement.repository.CafeRepository;
import com.example.cafemanagement.repository.CategoryRepository;
import com.example.cafemanagement.repository.MenuRepository;
import com.example.cafemanagement.repository.HashtagRepository;

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
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 변경사항: Cafe, CafeDto생성자에 Hashtag 추가 -> 관련 메서드 모두 수정
 */

@Service
public class CafeService {

    private final CafeRepository cafeRepository;
    private final MenuRepository menuRepository;
    private final CategoryRepository categoryRepository;
    private final HashtagRepository hashtagRepository; // 해시태그 저장소 추가
    private final String apiKey = "19dc7cdd122bfe1e88e7c68cdc00dd42"; // 카카오 REST API 키
    private final String apiUrl = "https://dapi.kakao.com/v2/local/search/keyword.json";


    public CafeService(CafeRepository cafeRepository, MenuRepository menuRepository,
                       CategoryRepository categoryRepository, HashtagRepository hashtagRepository) {
        this.cafeRepository = cafeRepository;
        this.menuRepository = menuRepository;
        this.categoryRepository = categoryRepository;
        this.hashtagRepository = hashtagRepository;
    }

    //수정
    @Transactional
    public Long addCafe(CafeRequestDto dto) {
        // 카테고리 조회
        Category category = categoryRepository.findByCategoryName(dto.getCategory())
                .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다."));

        // 위치 생성
        Location location = Location.of(dto.getLocation());

        // 해시태그 처리 (영속화 보장)
        Set<Hashtag> hashtags = dto.getHashtags().stream()
                .map(tag -> {
                    Hashtag existingHashtag = hashtagRepository.findByName(tag).orElse(null);
                    if (existingHashtag != null) {
                        return existingHashtag; // 이미 존재하는 해시태그 사용
                    } else {
                        Hashtag newHashtag = new Hashtag(tag); // 새로운 해시태그 생성
                        return hashtagRepository.save(newHashtag); // 저장 후 반환
                    }
                })
                .collect(Collectors.toSet());

        // 카페 생성 및 저장
        Cafe cafe = new Cafe(dto.getCafeName(), location, 0L, dto.getDescription(), dto.getCafeImageUrl(), category, hashtags);
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

        List<String> hashtags = cafe.getHashtags().stream()
                .map(Hashtag::getName)
                .collect(Collectors.toList());

        return new CafeDto(
                cafe.getCafeId(),
                cafe.getCafeName(),
                LocationDto.of(cafe.getLocation()),
                cafe.getRating(),
                cafe.getDescription(),
                cafe.getCategory().getCategoryName(),
                cafe.getCafeImageUrl(),
                menus,
                cafe.getReviews().stream().map(review -> ReviewDto.of(review, cafeId)).collect(Collectors.toList()),
                hashtags // 해시태그 추가
        );
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
                        cafe.getReviews().stream().map(review -> ReviewDto.of(review, cafe.getId())).toList(),
                        cafe.getHashtags().stream().map(Hashtag::getName).toList() // 해시태그 추가
                ))
                .toList();
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
                        cafe.getReviews().stream().map(review -> ReviewDto.of(review, cafe.getId())).toList(),
                        cafe.getHashtags().stream().map(Hashtag::getName).toList() // 해시태그 추가
                ))
                .toList();
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
                        cafe.getReviews().stream().map(review -> ReviewDto.of(review, cafe.getId())).toList(),
                        cafe.getHashtags().stream().map(Hashtag::getName).toList() // 해시태그 추가
                ))
                .toList();
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
                        cafe.getReviews().stream().map(review -> ReviewDto.of(review, cafe.getId())).toList(),
                        cafe.getHashtags().stream().map(Hashtag::getName).toList() // 해시태그 추가
                ))
                .toList();
    }


    @Transactional(readOnly = true)
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream().map(category -> new CategoryDto(category.getCategoryName())).toList();

    }

    /**
     * Cafe -> CafeDto 변환 메서드
     */
    private CafeDto convertToDto(Cafe cafe) {
        return new CafeDto(
                cafe.getCafeId(),
                cafe.getCafeName(),
                new LocationDto(
                        cafe.getLocation().getLatitude(),
                        cafe.getLocation().getLongitude(),
                        cafe.getLocation().getAddress()
                ),
                cafe.getRating(),
                cafe.getDescription(),
                cafe.getCategory().getCategoryName(),
                cafe.getCafeImageUrl(),
                cafe.getMenus().stream()
                        .map(menu -> new MenuDto(menu.getName(), menu.getPrice()))
                        .collect(Collectors.toList()),
                cafe.getReviews().stream()
                        .map(review -> new ReviewDto(
                                review.getReviewId(),
                                review.getTitle(),
                                review.getContent(),
                                review.getRating(),
                                review.getUser().getId(),
                                cafe.getCafeId(),
                                new ArrayList<>(review.getAttachments()) // Attachments 변환
                        ))
                        .collect(Collectors.toList()),
                cafe.getHashtags().stream()
                        .map(Hashtag::getName)
                        .collect(Collectors.toList())
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

    //로직추가예정
    @Transactional
    public void saveSearchResults(List<CafeDto> cafes) {
        // Logic to save or log search results
        cafes.forEach(cafe -> {
            System.out.println("Saving search result: " + cafe.getCafeName());
            // Implement persistence logic if necessary
        });
    }

    /**
     * 내부 DB 데이터 필터링 메서드
     */
    @Transactional(readOnly = true)
    public List<CafeDto> filterCafes(String category, List<String> hashtags, Double minRating, String keyword) {
        Category categoryEntity = (category != null && !category.isBlank())
                ? categoryRepository.findByCategoryName(category).orElse(null)
                : null;

        // DB 데이터 필터링
        List<Cafe> filteredCafes = cafeRepository.findAll().stream()
                .filter(cafe -> categoryEntity == null || cafe.getCategory().equals(categoryEntity))
                .filter(cafe -> minRating == null || cafe.getRating() >= minRating)
                .filter(cafe -> keyword == null || cafe.getCafeName().contains(keyword) || cafe.getDescription().contains(keyword))
                .filter(cafe -> {
                    if (hashtags == null || hashtags.isEmpty()) return true;
                    Set<String> cafeHashtags = cafe.getHashtags().stream()
                            .map(Hashtag::getName)
                            .collect(Collectors.toSet());
                    return cafeHashtags.containsAll(hashtags);
                })
                .collect(Collectors.toList());

        // Cafe -> CafeDto 변환
        return filteredCafes.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    //카카오 API 검색 후 필터링 없이 반환
    public List<CafeDto> searchFromKakaoApi(String keyword) {
        List<KakaoCafeDto> kakaoCafes = searchCafes(keyword);

        // 카카오 API 데이터를 CafeDto로 변환
        return kakaoCafes.stream()
                .map(cafe -> new CafeDto(
                        null, // 외부 데이터에는 DB ID가 없음
                        cafe.getPlaceName(),
                        new LocationDto(
                                Double.parseDouble(cafe.getY()),
                                Double.parseDouble(cafe.getX()),
                                cafe.getAddressName()
                        ),
                        0.0, // 별점 정보 없음
                        "카카오 데이터",
                        "외부 데이터",
                        null, // 이미지 없음
                        List.of(), // 메뉴 없음
                        List.of(), // 리뷰 없음
                        List.of() // 해시태그 없음
                ))
                .collect(Collectors.toList());
    }

    /**
     * 검색 및 필터링 메서드
     * 내부 DB 데이터만 검색 및 필터링
     */
    @Transactional(readOnly = true)
    public List<CafeDto> searchAndFilterCafes(String keyword, String category, List<String> hashtags, Double minRating) {
        return filterCafes(category, hashtags, minRating, keyword);
    }



}
