package com.example.cafemanagement.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "hashtags")
public class Hashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hashTagId; // 해시태그 ID

    @Column(nullable = false, unique = true)
    private String tagName; // 해시태그 이름

    // 기본 생성자
    protected Hashtag() {
    }

    // 사용자 정의 생성자
    public Hashtag(String tagName) {
        this.tagName = tagName;
    }

    // Getter and Setter
    public Long getHashTagId() {
        return hashTagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public String toString() {
        return "Hashtag{" +
                "hashTagId=" + hashTagId +
                ", tagName='" + tagName + '\'' +
                '}';
    }
}
