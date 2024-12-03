package com.example.cafemanagement.repository;

import com.example.cafemanagement.domain.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Integer> {

    @Query("SELECT f FROM Friend f WHERE f.fromUsername = :fromUsername AND f.toUsername = :toUsername")
    Friend findByName(@Param("fromUsername") String fromUsername, @Param("toUsername") String toUsername);

    List<Friend> findByToUsername(String myname);

}
