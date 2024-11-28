package com.example.cafemanagement.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "hashtags")
public class Hashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 해시태그 ID

    @Column(nullable = false, unique = true)
    private String name; // 해시태그 이름

    // 기본 생성자
    protected Hashtag() {
    }

    // 사용자 정의 생성자
    public Hashtag(String name) {
        this.name = name;
    }

    // Getter and Setter
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Hashtag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
