package com.example.cafemanagement.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Friendship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="user_username", nullable = false)
    private User user;

    @OneToMany(fetch = FetchType.LAZY)
    private List<User> friendList;

    public void addFriend(User user){
        this.friendList.add(user);
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public List<User> getFriendList() {
        return friendList;
    }

    public void setFriendList(List<User> friendList) {
        this.friendList = friendList;
    }
}
