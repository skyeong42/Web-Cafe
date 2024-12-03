package com.example.cafemanagement.controller;

import com.example.cafemanagement.domain.Friend;
import com.example.cafemanagement.service.FriendService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
public class FriendController {

    private final FriendService friendService;

    public FriendController(FriendService friendService) {
        this.friendService=friendService;
    }

    @GetMapping("/friendship")
    @ResponseBody
    public Integer isFriendship(@RequestParam("userid") Long userid, Authentication authentication) {
        return this.friendService.isFriendship(userid, authentication.getName());
    }

    @GetMapping("/friend")
    @ResponseBody
    public Integer isFriend(@RequestParam("username") String username, Authentication authentication) {
        return this.friendService.isFriend(username, authentication.getName());
    }

    @GetMapping("/friend/add")
    @ResponseBody
    public Integer addFriend(@RequestParam("username") String username, Authentication authentication) {
        this.friendService.addFriend(username, authentication.getName());
        return 0;
    }

    @GetMapping("/friend/request")
    @ResponseBody
    public List<Friend> getFriend(Authentication authentication) {
        return this.friendService.getFriend(authentication.getName());
    }

    @GetMapping("/friendship/add")
    @ResponseBody
    public Integer addFriendship(@RequestParam("username") String username, Authentication authentication) {
        this.friendService.addFriendship(username, authentication.getName());
        return 0;
    }
}
