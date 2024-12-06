package com.example.cafemanagement.controller;

import com.example.cafemanagement.domain.Friend;
import com.example.cafemanagement.domain.Message;
import com.example.cafemanagement.domain.User;
import com.example.cafemanagement.service.FriendService;
import com.example.cafemanagement.service.MessageService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Controller
public class FriendController {

    private final FriendService friendService;
    private final MessageService messageService;

    public FriendController(FriendService friendService, MessageService messageService) {
        this.friendService = friendService;
        this.messageService = messageService;
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
    public ResponseEntity<Void> addFriend(@RequestParam("username") String username, Authentication authentication) {
        this.friendService.addFriend(username, authentication.getName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/friend/request")
    @ResponseBody
    public List<Friend> getFriend(Authentication authentication) {
        return this.friendService.getFriend(authentication.getName());
    }

    @GetMapping("/friendship/add")
    @ResponseBody
    public ResponseEntity<Void> addFriendship(@RequestParam("username") String username, Authentication authentication) {
        this.friendService.addFriendship(username, authentication.getName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/friend/remove")
    @ResponseBody
    public ResponseEntity<Void> removeFriend(@RequestParam("username") String username, Authentication authentication) {
        this.friendService.removeFriend(username, authentication.getName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/friendship/list")
    @ResponseBody
    public List<User> getFriendshipList(Authentication authentication) {
        return this.friendService.getFriendshipList(authentication.getName());
    }

    @PostMapping("/message/send")
    public ResponseEntity<Void> sendMessage(
            @RequestParam("recipient") String recipient,
            @RequestParam("content") String content,
            Authentication authentication) {
        String sender = authentication.getName();

        // 입력값 검증
        if (recipient == null || recipient.isEmpty()) {
            throw new IllegalArgumentException("Recipient must not be null or empty");
        }
        if (content == null || content.isEmpty()) {
            throw new IllegalArgumentException("Content must not be null or empty");
        }

        messageService.sendMessage(sender, recipient, content);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/message/conversation")
    public ResponseEntity<List<Message>> getConversation(
            @RequestParam("with") String withUser,
            Authentication authentication) {
        String currentUser = authentication.getName();
        System.out.println("Fetching conversation for currentUser: " + currentUser + " with user: " + withUser);

        try {
            List<Message> conversation = messageService.getConversation(currentUser, withUser);
            System.out.println("Retrieved conversation: " + conversation);
            return ResponseEntity.ok(conversation);
        } catch (Exception e) {
            System.err.println("Error in /message/conversation API: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



}
