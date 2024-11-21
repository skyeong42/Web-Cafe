package com.example.cafemanagement.dto;

import com.example.cafemanagement.domain.Location;

public class LocationDto {

    private Double latitude; // 위도
    private Double longitude; // 경도
    private String address; // 주소

    // 기본 생성자
    public LocationDto() {
    }

    // 사용자 정의 생성자
    public LocationDto(Double latitude, Double longitude, String address) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }

    public static LocationDto of(Location location) {
        return new LocationDto(location.getLatitude(), location.getLongitude(), location.getAddress());
    }

    // Getters
    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getAddress() {
        return address;
    }

    // Setters
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "LocationDto{" +
            "latitude=" + latitude +
            ", longitude=" + longitude +
            ", address='" + address + '\'' +
            '}';
    }
}
