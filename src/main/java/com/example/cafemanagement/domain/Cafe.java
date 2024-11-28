package com.example.cafemanagement.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Cafe extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cafeId; // 카페 고유 ID

    @Column(nullable = false)
    private String cafeName; // 카페 이름

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Column(nullable = false)
    private double rating; // 평점

    @Column(length = 500)
    private String description; // 카페 설명

    public String getCafeImageUrl() {
        return cafeImageUrl;
    }

    private String cafeImageUrl;

    //수정
    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "cafe_hashtags",
            joinColumns = @JoinColumn(name = "cafe_id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id")
    )
    private Set<Hashtag> hashtags = new HashSet<>();// 해시태그 관계(수정)

    @OneToMany(mappedBy = "cafe", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Menu> menus = new ArrayList<>(); // 카페 메뉴 리스트

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category; // 카페 카테고리

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    public List<Booking> getBooks() {
        return books;
    }

    @OneToMany(mappedBy = "cafe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> books = new ArrayList<>();

    public List<Review> getReviews() {
        return reviews;
    }

    @OneToMany(mappedBy = "cafe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>(); // 카페 메뉴 리스트

    // 생성자 변경 (수정)
    public Cafe(String cafeName, Location location, double rating, String description, String cafeImageUrl, Category category, Set<Hashtag> hashtags) {
        this.cafeName = cafeName;
        this.location = location;
        this.rating = rating;
        this.description = description;
        this.cafeImageUrl = cafeImageUrl;
        this.category = category;
        this.hashtags = hashtags;
    }

    // Getter 추가
    public Location getLocation() {
        return location;
    }

    // Setter 추가
    public void setLocation(Location location) {
        this.location = location;
    }

    // 기본 생성자 (JPA 요구사항)
    protected Cafe() {}


    // Getters
    public Long getCafeId() {
        return cafeId;
    }

    public String getCafeName() {
        return cafeName;
    }

    public double getRating() {
        return rating;
    }

    public String getDescription() {
        return description;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    // 메뉴 추가 메서드 (필요할 경우)
    public void addMenu(Menu menu) {
        menus.add(menu);
    }

    // 카페 정보 업데이트
    public void updateDetails(String cafeName, Location location, double rating, String description, Category category) {
        this.cafeName = cafeName;
        this.location = location;
        this.rating = rating;
        this.description = description;
        this.category = category;
    }

    @Override
    public String toString() {
        return "Cafe{" +
            "cafeId=" + cafeId +
            ", cafeName='" + cafeName + '\'' +
            ", location='" + location + '\'' +
            ", category='" + category + '\'' +
            ", rating=" + rating +
            ", description='" + description + '\'' +
            ", menus=" + menus.size() +
            '}';
    }

    public Long getId() {
        return cafeId;
    }

    public Category getCategory() {
        return category;
    }

    //수정
    // Getter/Setter
    public Set<Hashtag> getHashtags() {
        return hashtags;
    }

    public void addHashtag(Hashtag hashtag) {
        this.hashtags.add(hashtag);
    }
}
