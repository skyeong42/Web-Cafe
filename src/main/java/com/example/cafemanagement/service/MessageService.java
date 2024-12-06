package com.example.cafemanagement.service;

import com.example.cafemanagement.domain.Message;
import com.example.cafemanagement.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final FriendService friendService;

    public MessageService(MessageRepository messageRepository, FriendService friendService) {
        this.messageRepository = messageRepository;
        this.friendService = friendService;
    }

    public void sendMessage(String sender, String recipient, String content) {
        System.out.println("Saving message: sender=" + sender + ", recipient=" + recipient + ", content=" + content);

        Message message = new Message();
        message.setSender(sender);
        message.setRecipient(recipient);
        message.setContent(content);
        message.setTimestamp(LocalDateTime.now());

        messageRepository.save(message); // 메시지를 단방향으로만 저장
        System.out.println("Message saved successfully");
    }



    public List<Message> getConversation(String user1, String user2) {
        System.out.println("Fetching conversation between: " + user1 + " and " + user2);

        try {
            List<Message> messagesSent = messageRepository.findBySenderAndRecipient(user1, user2);
            System.out.println("Messages sent from " + user1 + " to " + user2 + ": " + messagesSent);

            List<Message> messagesReceived = messageRepository.findBySenderAndRecipient(user2, user1);
            System.out.println("Messages received by " + user1 + " from " + user2 + ": " + messagesReceived);

            List<Message> conversation = new ArrayList<>();
            conversation.addAll(messagesSent);
            conversation.addAll(messagesReceived);

            conversation.sort(Comparator.comparing(Message::getTimestamp));
            System.out.println("Final sorted conversation: " + conversation);

            return conversation;
        } catch (Exception e) {
            System.err.println("Error in getConversation: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>(); // 빈 리스트 반환
        }
    }





}
