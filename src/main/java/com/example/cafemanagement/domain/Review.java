package com.example.cafemanagement.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId; // 리뷰 고유 ID

    @Column(nullable = false)
    private String title; // 리뷰 제목

    @Column(nullable = false, length = 1000)
    private String content; // 리뷰 내용

    @Column(nullable = false)
    private int rating; // 리뷰 평가 점수 (정수)

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 리뷰 작성자

    public Cafe getCafe() {
        return cafe;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_id", nullable = false)
    private Cafe cafe;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attachment> attachments = new ArrayList<>(); // 리뷰 첨부파일 리스트

    // 기본 생성자
    protected Review() {}

    public Review(String title, String content, int rating, User user, Cafe cafe) {
        this.title = title;
        this.content = content;
        this.rating = rating;
        this.user = user;
        this.cafe = cafe;
    }

    // Getters
    public Long getReviewId() {
        return reviewId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public int getRating() {
        return rating;
    }

    public User getUser() {
        return user;
    }

    public Long getUserId() {
        return user.getId();
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    // Attachment 관련 메서드
    public void addAttachment(Attachment attachment) {
        this.attachments.add(attachment);
        attachment.setReview(this); // 양방향 관계 설정
    }

    public void removeAttachment(Attachment attachment) {
        this.attachments.remove(attachment);
        attachment.setReview(null); // 양방향 관계 해제
    }

    public Long getId() {
        return reviewId;
    }

    @Override
    public String toString() {
        return "Review{" +
                "reviewId=" + reviewId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", rating=" + rating +
                ", user=" + user.getId() + // 유저 ID만 표시
                ", attachments=" + attachments.size() + " files" +
                '}';
    }
}
