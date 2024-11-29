package com.example.cafemanagement.config;

import com.example.cafemanagement.domain.*;
import com.example.cafemanagement.repository.CategoryRepository;
import com.example.cafemanagement.repository.CafeRepository;
import com.example.cafemanagement.repository.UserRepository;
import com.example.cafemanagement.repository.HashtagRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final CafeRepository cafeRepository;
    private final CategoryRepository categoryRepository;
    private final HashtagRepository hashtagRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;



    @Autowired
    public DataInitializer(CafeRepository cafeRepository, CategoryRepository categoryRepository,HashtagRepository hashtagRepository ,UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.cafeRepository = cafeRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.hashtagRepository = hashtagRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // 1. 카테고리 초기화
        Category dessertCafe = categoryRepository.findByCategoryName("DessertCafe")
                .orElseGet(() -> categoryRepository.save(new Category("DessertCafe")));
        Category largeCafe = categoryRepository.findByCategoryName("LargeCafe")
                .orElseGet(() -> categoryRepository.save(new Category("LargeCafe")));
        Category studyCafe = categoryRepository.findByCategoryName("StudyCafe")
                .orElseGet(() -> categoryRepository.save(new Category("StudyCafe")));
        Category atmosphericCafe = categoryRepository.findByCategoryName("AtmosphericCafe")
                .orElseGet(() -> categoryRepository.save(new Category("AtmosphericCafe")));

        // 2. 해시태그 초기화 (저장)

        Hashtag hashtag1 = hashtagRepository.findByName("와이파이잘됨")
                .orElseGet(() -> hashtagRepository.save(new Hashtag("와이파이잘됨")));
        Hashtag hashtag2 = hashtagRepository.findByName("조용함")
                .orElseGet(() -> hashtagRepository.save(new Hashtag("조용함")));
        Hashtag hashtag3 = hashtagRepository.findByName("친절함")
                .orElseGet(() -> hashtagRepository.save(new Hashtag("친절함")));
        Hashtag hashtag4 = hashtagRepository.findByName("청결함")
                .orElseGet(() -> hashtagRepository.save(new Hashtag("청결함")));
        Hashtag hashtag5 = hashtagRepository.findByName("콘센트많음")
                .orElseGet(() -> hashtagRepository.save(new Hashtag("콘센트많음")));


        List<Hashtag> hashtags = List.of(hashtag1, hashtag2, hashtag3, hashtag4, hashtag5);

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
        // 4. 샘플 카페 데이터 생성
        Cafe cafe1 = new Cafe("Cafe Sunrise", locations.get(0), 4.5, "아침을 여는 따뜻한 카페입니다.",
                "https://images.unsplash.com/photo-1509042239860-f550ce710b93?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=60",
                dessertCafe);
        cafe1.addHashtag(hashtag1);
        cafe1.addHashtag(hashtag2);

        Cafe cafe2 = new Cafe("Cafe Blossom", locations.get(1), 5.0, "봄꽃처럼 아름다운 분위기의 카페.",
                "https://images.unsplash.com/photo-1509042239860-f550ce710b93?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=60",
                atmosphericCafe);
        cafe2.addHashtag(hashtag3);
        cafe2.addHashtag(hashtag4);

        Cafe cafe3 = new Cafe("Cafe Harmony", locations.get(2), 4.2, "조용하고 평화로운 시간을 보낼 수 있는 카페.",
                "https://images.unsplash.com/photo-1498804103079-a6351b050096?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=60",
                studyCafe);
        cafe3.addHashtag(hashtag2);
        cafe3.addHashtag(hashtag5);

        Cafe cafe4 = new Cafe("Cafe Delight", locations.get(3), 4.8, "다양한 음료와 디저트를 즐길 수 있는 카페.",
                "https://images.unsplash.com/photo-1521295121783-8a321d551ad2?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=60",
                dessertCafe);
        cafe4.addHashtag(hashtag1);
        cafe4.addHashtag(hashtag5);

        Cafe cafe5 = new Cafe("Cafe Bliss", locations.get(4), 3.7, "행복을 주는 맛있는 커피가 있는 카페.",
                "https://images.unsplash.com/photo-1495474472287-4d71bcdd2085?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=60",
                largeCafe);
        cafe5.addHashtag(hashtag4);
        cafe5.addHashtag(hashtag3);

        Cafe cafe6 = new Cafe("Cafe Aroma", locations.get(5), 3.9, "향긋한 커피 향이 가득한 카페.",
                "https://images.unsplash.com/photo-1476224203421-9ac39bcb3327?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=60",
                atmosphericCafe);
        cafe6.addHashtag(hashtag2);
        cafe6.addHashtag(hashtag3);

        Cafe cafe7 = new Cafe("Cafe Luna", locations.get(6), 4.3, "달빛 아래에서 즐기는 로맨틱한 카페.",
                "https://images.unsplash.com/photo-1472214103451-9374bd1c798e?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=60",
                studyCafe);
        cafe7.addHashtag(hashtag1);
        cafe7.addHashtag(hashtag5);

        Cafe cafe8 = new Cafe("Cafe Mocha", locations.get(7), 2.5, "진한 모카 맛을 자랑하는 카페.",
                "https://images.unsplash.com/photo-1509042239860-f550ce710b93?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=60",
                largeCafe);
        cafe8.addHashtag(hashtag4);
        cafe8.addHashtag(hashtag5);

        Cafe cafe9 = new Cafe("Cafe Zen", locations.get(8), 4.7, "명상과 커피를 함께 즐길 수 있는 카페.",
                "https://images.unsplash.com/photo-1511920170033-f8396924c348?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=60",
                atmosphericCafe) ;
        cafe9.addHashtag(hashtag2);
        cafe9.addHashtag(hashtag3);

        Cafe cafe10 = new Cafe("Cafe Velvet", locations.get(9), 4.9, "고급스러운 인테리어와 맛있는 커피가 있는 카페.",
                "https://images.unsplash.com/photo-1495474472287-4d71bcdd2085?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=60",
                dessertCafe);
        cafe10.addHashtag(hashtag2);
        cafe10.addHashtag(hashtag4);

        // 메뉴 데이터 추가
        cafe1.addMenu(new Menu(cafe1, "아메리카노", 4500));
        cafe1.addMenu(new Menu(cafe1, "카페 라떼", 5000));

        cafe2.addMenu(new Menu(cafe2, "카라멜 마끼아또", 5500));
        cafe2.addMenu(new Menu(cafe2, "바닐라 라떼", 5200));
        cafe2.addMenu(new Menu(cafe2, "허니 브레드", 6500));

        cafe3.addMenu(new Menu(cafe3, "에스프레소", 3500));
        cafe3.addMenu(new Menu(cafe3, "카푸치노", 4800));
        cafe3.addMenu(new Menu(cafe3, "초콜릿 머핀", 4000));

        cafe4.addMenu(new Menu(cafe4, "녹차 라떼", 5300));
        cafe4.addMenu(new Menu(cafe4, "딸기 스무디", 5500));
        cafe4.addMenu(new Menu(cafe4, "레몬 케이크", 6000));

        cafe5.addMenu(new Menu(cafe5, "모카 라떼", 4900));
        cafe5.addMenu(new Menu(cafe5, "플랫 화이트", 5200));
        cafe5.addMenu(new Menu(cafe5, "치즈 케이크", 7500));

        cafe6.addMenu(new Menu(cafe6, "아포가토", 5500));
        cafe6.addMenu(new Menu(cafe6, "아이스티", 4000));
        cafe6.addMenu(new Menu(cafe6, "마카롱 세트", 8000));

        cafe7.addMenu(new Menu(cafe7, "핫초코", 4500));
        cafe7.addMenu(new Menu(cafe7, "카라멜 라떼", 5200));
        cafe7.addMenu(new Menu(cafe7, "베이글 세트", 6500));

        cafe8.addMenu(new Menu(cafe8, "콜드브루", 5000));
        cafe8.addMenu(new Menu(cafe8, "에스프레소 마끼아또", 4000));
        cafe8.addMenu(new Menu(cafe8, "초코 케이크", 7000));

        cafe9.addMenu(new Menu(cafe9, "프라푸치노", 6000));
        cafe9.addMenu(new Menu(cafe9, "아몬드 라떼", 5800));
        cafe9.addMenu(new Menu(cafe9, "애플 타르트", 7500));

        cafe10.addMenu(new Menu(cafe10, "오트밀 라떼", 5700));
        cafe10.addMenu(new Menu(cafe10, "얼그레이 티", 4500));
        cafe10.addMenu(new Menu(cafe10, "티라미수", 8000));


        // 카페 데이터 저장
        cafeRepository.saveAll(List.of(cafe1, cafe2, cafe3, cafe4, cafe5, cafe6, cafe7, cafe8, cafe9, cafe10));

        // 임시 사용자
        String testUsername = "user";
        String testPassword = "1234";

        // 비밀번호 인코딩
        String encodedPassword = passwordEncoder.encode(testPassword);

        // 테스트 사용자 생성
        User testUser = new User(testUsername, encodedPassword, "테스트 사용자", "Other", "testuser@example.com");
        testUser.setProfilePicture("https://example.com/profile/testuser.png"); // 사진(필요에 따라 추가)

        // 사용자 저장
        userRepository.save(testUser);

        System.out.println("샘플 카페 데이터 10개가 초기화되었습니다.");
    }
}
