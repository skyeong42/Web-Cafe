package com.example.cafemanagement.domain;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categories", uniqueConstraints = @UniqueConstraint(columnNames = "categoryName"))
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId; // 카테고리 ID


    @Column(nullable = false, unique = true)
    private String categoryName; // 카테고리 이름

    //추가
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Cafe> cafes = new HashSet<>();

    // 기본 생성자
    protected Category() {
    }

    // 사용자 정의 생성자
    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    // Getter
    public Long getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    // Setter
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }


    @Override
    public String toString() {
        return "Category{" +
            "categoryId=" + categoryId +
            ", categoryName='" + categoryName + '\'' +
            '}';
    }
}
