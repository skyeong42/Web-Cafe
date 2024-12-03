package com.example.cafemanagement.service;

import com.example.cafemanagement.domain.Friend;
import com.example.cafemanagement.domain.Friendship;
import com.example.cafemanagement.domain.User;
import com.example.cafemanagement.repository.FriendRepository;
import com.example.cafemanagement.repository.FriendshipRepository;
import com.example.cafemanagement.repository.UserRepository;
import org.springframework.stereotype.Service;

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
        Friendship friendship = this.friendshipRepository.findByUser_Username(myname);
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
        if(friend==null){
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

        this.friendshipRepository
    }
}
