package com.example.cafemanagement.domain;

import jakarta.persistence.*;

@Entity
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attachmentId; // 첨부파일 고유 ID

    @Column(nullable = false)
    private String fileName; // 첨부파일 이름

    @Column(nullable = false)
    private String fileUrl; // 첨부파일 경로

    @Column(nullable = false)
    private String fileType; // 첨부파일 타입 (예: 이미지, 문서)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false)
    private Review review; // 첨부파일이 속한 리뷰

    // 기본 생성자
    protected Attachment() {}

    public Attachment(String fileName, String fileUrl, String fileType, Review review) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.fileType = fileType;
        this.review = review;
    }

    // Getters
    public Long getAttachmentId() {
        return attachmentId;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public String getFileType() {
        return fileType;
    }

    public Review getReview() {
        return review;
    }

    // Setters
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "attachmentId=" + attachmentId +
                ", fileName='" + fileName + '\'' +
                ", fileUrl='" + fileUrl + '\'' +
                ", fileType='" + fileType + '\'' +
                '}';
    }
}
