 package com.example.cafemanagement.domain;

 import java.util.HashSet;
 import java.util.Set;

 import jakarta.persistence.*;

 @Entity
 @Table(name = "hashtags", uniqueConstraints = @UniqueConstraint(columnNames = "tagName"))
 public class Hashtag {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long hashTagId; // 해시태그 ID

     @Column(nullable = false, unique = true)
     private String tagName; // 해시태그 이름

     @ManyToMany(mappedBy = "hashtags", fetch = FetchType.LAZY)
     private Set<Cafe> cafes = new HashSet<>(); // 해시태그와 연결된 카페들

     // 생성자
     public Hashtag(String tagName) {
         this.tagName = tagName;
     }

     public Hashtag() {
     }

     // Getters
     public Long getHashTagId() {
         return hashTagId;
     }

     public String getTagName() {
         return tagName;
     }

     // Setters
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


     public Set<Cafe> getCafes() {
         return cafes;
     }
 }
