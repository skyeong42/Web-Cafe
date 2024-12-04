package com.example.cafemanagement.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Friendship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String username;

    @ManyToMany
    @JoinTable(
            name = "friendship_user",
            joinColumns = @JoinColumn(name = "friendship_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> friendList = new ArrayList<>();




    public void addFriend(User friend) {
        if (!this.friendList.contains(friend)) {
            this.friendList.add(friend);
        }
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<User> getFriendList() {
        return friendList;
    }

}
