package com.example.cafemanagement.domain;

import jakarta.persistence.*;

@Entity
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long friendshipid;

    @ManyToOne
    private User user;

    @ManyToOne
    private User friend;

    public Friend(User user, User friend) {
        this.user=user;
        this.friend=friend;
    }

    public Long getFriendshipid() {
        return friendshipid;
    }

    public User getUser() {
        return user;
    }

    public User getFriend() {
        return friend;
    }

    public void setFriendshipid(Long friendshipid) {
        this.friendshipid = friendshipid;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setFriend(User friend) {
        this.friend = friend;
    }
}
