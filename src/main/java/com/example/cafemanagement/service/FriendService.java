package com.example.cafemanagement.service;

import com.example.cafemanagement.domain.Friend;
import com.example.cafemanagement.domain.Friendship;
import com.example.cafemanagement.domain.User;
import com.example.cafemanagement.repository.FriendRepository;
import com.example.cafemanagement.repository.FriendshipRepository;
import com.example.cafemanagement.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class FriendService {

    private final FriendRepository friendRepository;
    private final FriendshipRepository friendshipRepository;
    private final UserRepository userRepository;

    public FriendService(FriendRepository friendRepository, FriendshipRepository friendshipRepository, UserRepository userRepository){
        this.friendRepository=friendRepository;
        this.friendshipRepository=friendshipRepository;
        this.userRepository=userRepository;
    }

    public Integer isFriendship(Long userid, String myname) {
        Friendship friendship = this.friendshipRepository.findByUsername(myname);
        if(friendship==null){
            return 0;
        }
        for(User user: friendship.getFriendList()){
            if(Objects.equals(user.getId(), userid)){
                return 1;
            }
        }
        return 0;
    }

    public Integer isFriend(String username, String myname) {
        Friend friend = this.friendRepository.findByName(myname, username);
        Friend friend2 = this.friendRepository.findByName(username, myname);
        if(friend==null && friend2==null){
            return 0;
        }
        return 2;
    }

    public void addFriend(String username, String myname) {
        Friend friend = new Friend();
        friend.setFromUsername(myname);
        friend.setToUsername(username);
        this.friendRepository.save(friend);
    }

    public List<Friend> getFriend(String myname) {
        return this.friendRepository.findByToUsername(myname);
    }

    public void addFriendship(String username, String myname) {
        User user1 = this.userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
        User user2 = this.userRepository.findByUsername(myname).orElseThrow(() -> new IllegalArgumentException("User not found: " + myname));


        // username에 해당하는 Friendship을 찾고, 없으면 새로 생성합니다.
        Friendship user1Friendship = this.friendshipRepository.findByUsername(username);
        if (user1Friendship == null) {
            user1Friendship = new Friendship();
            user1Friendship.setUsername(username);
        }

        // 중복된 친구 관계가 없으면 추가합니다.
        if (!user1Friendship.getFriendList().contains(user2)) {
            user1Friendship.addFriend(user2);
            this.friendshipRepository.save(user1Friendship);
        }

        // myname에 해당하는 Friendship을 찾고, 없으면 새로 생성합니다.
        Friendship user2Friendship = this.friendshipRepository.findByUsername(myname);
        if (user2Friendship == null) {
            user2Friendship = new Friendship();
            user2Friendship.setUsername(myname);
        }

        // 중복된 친구 관계가 없으면 추가합니다.
        if (!user2Friendship.getFriendList().contains(user1)) {
            user2Friendship.addFriend(user1);
            this.friendshipRepository.save(user2Friendship);
        }

    }

    public void removeFriend(String username, String myname) {
        Friend friend = this.friendRepository.findByName(username, myname);
        this.friendRepository.delete(friend);
    }

    public List<User> getFriendshipList(String myname) {
        if(this.friendshipRepository.findByUsername(myname)==null) {
            return new ArrayList<>();
        } else {
            Friendship friendship = this.friendshipRepository.findByUsername(myname);
            return friendship.getFriendList();
        }
    }
}
