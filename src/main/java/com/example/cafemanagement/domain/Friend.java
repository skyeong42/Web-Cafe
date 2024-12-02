package com.example.cafemanagement.domain;

import jakarta.persistence.*;

@Entity
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long friendId;

    private String fromUsername;

    private String toUsername;

    public Long getFriendId() {
        return friendId;
    }

    public String getFromUsername() {
        return fromUsername;
    }

    public String getToUsername() {
        return toUsername;
    }


    public void setFromUsername(String fromUsername) {
        this.fromUsername = fromUsername;
    }

    public void setToUsername(String toUsername) {
        this.toUsername = toUsername;
    }
}
