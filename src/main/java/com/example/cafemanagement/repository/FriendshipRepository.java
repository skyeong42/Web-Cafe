package com.example.cafemanagement.repository;

import com.example.cafemanagement.domain.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Integer> {
    Friendship findByUsername(String username);
}
