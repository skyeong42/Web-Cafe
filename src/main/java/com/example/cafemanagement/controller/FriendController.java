package com.example.cafemanagement.controller;

import com.example.cafemanagement.service.FriendService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class FriendController {

    private final FriendService friendService;

    public FriendController(FriendService friendService) {
        this.friendService=friendService;
    }



}
