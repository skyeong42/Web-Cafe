package com.example.cafemanagement.dto;

public class KakaoCafeDto {
    private String placeName;     // 장소 이름
    private String addressName;   // 주소
    private String phone;         // 전화번호
    private String x;             // 경도
    private String y;             // 위도

    // 기본 생성자
    public KakaoCafeDto() {
    }

    // 전체 필드를 받는 생성자
    public KakaoCafeDto(String placeName, String addressName, String phone, String x, String y) {
        this.placeName = placeName;
        this.addressName = addressName;
        this.phone = phone;
        this.x = x;
        this.y = y;
    }

    // Getter와 Setter
    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    // toString 메서드
    @Override
    public String toString() {
        return "KakaoCafeDto{" +
                "placeName='" + placeName + '\'' +
                ", addressName='" + addressName + '\'' +
                ", phone='" + phone + '\'' +
                ", x='" + x + '\'' +
                ", y='" + y + '\'' +
                '}';
    }
}
