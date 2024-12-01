package com.example.cafemanagement.service;

import com.example.cafemanagement.repository.FriendRepository;
import org.springframework.stereotype.Service;

@Service
public class FriendService {

    private final FriendRepository friendRepository;

    public FriendService(FriendRepository friendRepository){
        this.friendRepository=friendRepository;
    }

}
