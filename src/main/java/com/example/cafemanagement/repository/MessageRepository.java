package com.example.cafemanagement.repository;

import com.example.cafemanagement.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderAndRecipient(String sender, String recipient);
    List<Message> findByRecipientAndSender(String recipient, String sender);
}
