package com.example.cafemanagement.domain;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Hashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // 해시태그 이름

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private Set<Cafe> cafes = new HashSet<>(); // 이 해시태그를 사용하는 카페들

    // 기본 생성자
    protected Hashtag() {}

    // 생성자
    public Hashtag(String name) {
        this.name = name;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Cafe> getCafes() {
        return cafes;
    }
}
