package com.example.cafemanagement.config;

import com.example.cafemanagement.domain.Category;
import com.example.cafemanagement.domain.Cafe;
import com.example.cafemanagement.domain.Location;
import com.example.cafemanagement.repository.CategoryRepository;
import com.example.cafemanagement.repository.CafeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final CafeRepository cafeRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public DataInitializer(CafeRepository cafeRepository, CategoryRepository categoryRepository) {
        this.cafeRepository = cafeRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // 1. 카테고리 초기화
        Category cafeCategory = categoryRepository.findByCategoryName("Cafe")
                                                  .orElseGet(() -> categoryRepository.save(new Category("Cafe")));

        // 2. 샘플 위치 데이터 생성
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

        // 위치 데이터 저장
        // 3. 샘플 카페 데이터 생성
        List<Cafe> cafes = Arrays.asList(
            new Cafe("Cafe Sunrise", locations.get(0), 4.5, "아침을 여는 따뜻한 카페입니다.",
                     "https://images.unsplash.com/photo-1509042239860-f550ce710b93?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=60",
                     cafeCategory),
            new Cafe("Cafe Blossom", locations.get(1), 4.2, "봄꽃처럼 아름다운 분위기의 카페.",
                     "https://images.unsplash.com/photo-1509042239860-f550ce710b93?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=60",
                     cafeCategory),
            new Cafe("Cafe Harmony", locations.get(2), 4.7, "조용하고 평화로운 시간을 보낼 수 있는 카페.",
                     "https://images.unsplash.com/photo-1498804103079-a6351b050096?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=60",
                     cafeCategory),
            new Cafe("Cafe Delight", locations.get(3), 4.3, "다양한 음료와 디저트를 즐길 수 있는 카페.",
                     "https://images.unsplash.com/photo-1521295121783-8a321d551ad2?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=60",
                     cafeCategory),
            new Cafe("Cafe Bliss", locations.get(4), 4.6, "행복을 주는 맛있는 커피가 있는 카페.",
                     "https://images.unsplash.com/photo-1495474472287-4d71bcdd2085?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=60",
                     cafeCategory),
            new Cafe("Cafe Aroma", locations.get(5), 4.1, "향긋한 커피 향이 가득한 카페.",
                     "https://images.unsplash.com/photo-1476224203421-9ac39bcb3327?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=60",
                     cafeCategory),
            new Cafe("Cafe Luna", locations.get(6), 4.4, "달빛 아래에서 즐기는 로맨틱한 카페.",
                     "https://images.unsplash.com/photo-1472214103451-9374bd1c798e?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=60",
                     cafeCategory),
            new Cafe("Cafe Mocha", locations.get(7), 4.0, "진한 모카 맛을 자랑하는 카페.",
                     "https://images.unsplash.com/photo-1509042239860-f550ce710b93?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=60",
                     cafeCategory),
            new Cafe("Cafe Zen", locations.get(8), 4.8, "명상과 커피를 함께 즐길 수 있는 카페.",
                     "https://images.unsplash.com/photo-1511920170033-f8396924c348?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=60",
                     cafeCategory),
            new Cafe("Cafe Velvet", locations.get(9), 4.9, "고급스러운 인테리어와 맛있는 커피가 있는 카페.",
                     "https://images.unsplash.com/photo-1495474472287-4d71bcdd2085?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=60",
                     cafeCategory)
        );

        // 카페 데이터 저장
        cafeRepository.saveAll(cafes);

        System.out.println("샘플 카페 데이터 10개가 초기화되었습니다.");
    }
}
