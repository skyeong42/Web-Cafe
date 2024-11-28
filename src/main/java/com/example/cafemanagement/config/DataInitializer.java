package com.example.cafemanagement.config;

import com.example.cafemanagement.domain.Category;
import com.example.cafemanagement.domain.Cafe;
import com.example.cafemanagement.domain.Hashtag;
import com.example.cafemanagement.domain.Location;
import com.example.cafemanagement.repository.CategoryRepository;
import com.example.cafemanagement.repository.CafeRepository;
import com.example.cafemanagement.repository.HashtagRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final HashtagRepository hashtagRepository;
    private final CafeRepository cafeRepository;

    public DataInitializer(CategoryRepository categoryRepository, HashtagRepository hashtagRepository, CafeRepository cafeRepository) {
        this.categoryRepository = categoryRepository;
        this.hashtagRepository = hashtagRepository;
        this.cafeRepository = cafeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // 1. 카테고리 초기화
        List<String> categories = List.of("DessertCafe", "LargeCafe", "StudyCafe", "AtmosphericCafe");
        categories.forEach(categoryName -> categoryRepository.findByCategoryName(categoryName)
                .orElseGet(() -> categoryRepository.save(new Category(categoryName))));

        // 카테고리 참조 가져오기
        Category dessertCafe = categoryRepository.findByCategoryName("DessertCafe").orElseThrow();
        Category largeCafe = categoryRepository.findByCategoryName("LargeCafe").orElseThrow();
        Category studyCafe = categoryRepository.findByCategoryName("StudyCafe").orElseThrow();
        Category atmosphericCafe = categoryRepository.findByCategoryName("AtmosphericCafe").orElseThrow();

        // 2. 해시태그 초기화
        List<String> hashtags = List.of("와이파이잘됨", "조용함", "친절함", "청결함", "콘센트많음");
        hashtags.forEach(hashtagName -> hashtagRepository.findByName(hashtagName)
                .orElseGet(() -> hashtagRepository.save(new Hashtag(hashtagName))));

        // 해시태그 참조 가져오기 (영속성 컨텍스트 내에서 관리되도록 처리)
        Hashtag hashtag1 = hashtagRepository.findByName("와이파이잘됨").orElseThrow();
        Hashtag hashtag2 = hashtagRepository.findByName("조용함").orElseThrow();
        Hashtag hashtag3 = hashtagRepository.findByName("친절함").orElseThrow();
        Hashtag hashtag4 = hashtagRepository.findByName("청결함").orElseThrow();
        Hashtag hashtag5 = hashtagRepository.findByName("콘센트많음").orElseThrow();

        // 3. 샘플 위치 데이터 생성
        List<Location> locations = Arrays.asList(
                new Location(37.5665, 126.9780, "서울특별시 중구 세종대로 110"),
                new Location(37.5700, 126.9833, "서울특별시 종로구 종로 1가"),
                new Location(37.5172, 127.0473, "서울특별시 강남구 테헤란로 521"),
                new Location(37.5045, 127.0497, "서울특별시 서초구 반포대로 305"),
                new Location(37.5512, 126.9882, "서울특별시 마포구 서교동"),
                new Location(37.5775, 126.9769, "서울특별시 은평구 응암로"),
                new Location(37.5599, 126.9368, "서울특별시 강서구 화곡로"),
                new Location(37.4917, 126.9768, "서울특별시 노원구 동일로"),
                new Location(37.5013, 127.0398, "서울특별시 도봉구 방학로"),
                new Location(37.5410, 127.0345, "서울특별시 송파구 올림픽로")
        );

        // 4. 샘플 카페 데이터 생성
        List<Cafe> cafes = Arrays.asList(
                new Cafe("Cafe Sunrise", locations.get(0), 4.5, "아침을 여는 따뜻한 카페입니다.",
                        "https://images.unsplash.com/photo-1509042239860-f550ce710b93?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=60",
                        dessertCafe, Set.of(hashtag1, hashtag2)),
                new Cafe("Cafe Blossom", locations.get(1), 4.2, "봄꽃처럼 아름다운 분위기의 카페.",
                        "https://images.unsplash.com/photo-1509042239860-f550ce710b93?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=60",
                        dessertCafe, Set.of(hashtag3, hashtag4)),
                new Cafe("Cafe Harmony", locations.get(2), 4.7, "조용하고 평화로운 시간을 보낼 수 있는 카페.",
                        "https://images.unsplash.com/photo-1498804103079-a6351b050096?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=60",
                        largeCafe, Set.of(hashtag2, hashtag5)),
                new Cafe("Cafe Delight", locations.get(3), 4.3, "다양한 음료와 디저트를 즐길 수 있는 카페.",
                        "https://images.unsplash.com/photo-1521295121783-8a321d551ad2?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=60",
                        largeCafe, Set.of(hashtag1, hashtag4, hashtag5)),
                new Cafe("Cafe Bliss", locations.get(4), 4.6, "행복을 주는 맛있는 커피가 있는 카페.",
                        "https://images.unsplash.com/photo-1495474472287-4d71bcdd2085?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=60",
                        studyCafe, Set.of(hashtag1, hashtag2)),
                new Cafe("Cafe Aroma", locations.get(5), 4.1, "향긋한 커피 향이 가득한 카페.",
                        "https://images.unsplash.com/photo-1476224203421-9ac39bcb3327?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=60",
                        studyCafe, Set.of(hashtag4, hashtag3)),
                new Cafe("Cafe Luna", locations.get(6), 4.4, "달빛 아래에서 즐기는 로맨틱한 카페.",
                        "https://images.unsplash.com/photo-1472214103451-9374bd1c798e?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=60",
                        atmosphericCafe, Set.of(hashtag2, hashtag4)),
                new Cafe("Cafe Mocha", locations.get(7), 4.0, "진한 모카 맛을 자랑하는 카페.",
                        "https://images.unsplash.com/photo-1509042239860-f550ce710b93?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=60",
                        atmosphericCafe, Set.of(hashtag5, hashtag1)),
                new Cafe("Cafe Zen", locations.get(8), 4.8, "명상과 커피를 함께 즐길 수 있는 카페.",
                        "https://images.unsplash.com/photo-1511920170033-f8396924c348?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=60",
                        studyCafe, Set.of(hashtag1, hashtag5)),
                new Cafe("Cafe Velvet", locations.get(9), 4.9, "고급스러운 인테리어와 맛있는 커피가 있는 카페.",
                        "https://images.unsplash.com/photo-1495474472287-4d71bcdd2085?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=60",
                        atmosphericCafe, Set.of(hashtag4, hashtag3))
        );

        // 5. 카페 데이터 저장
        cafeRepository.saveAll(cafes);

        System.out.println("샘플 카페 데이터 10개가 초기화되었습니다.");
    }
}
