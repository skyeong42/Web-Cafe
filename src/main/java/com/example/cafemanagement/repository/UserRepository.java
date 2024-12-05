package com.example.cafemanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.cafemanagement.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    @Query("SELECT u FROM User u WHERE (LOWER(u.nickname) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(u.username) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND u.username <> :myname")
    List<User> findByKeyword(@Param("keyword") String keyword, @Param("myname") String myname);

    Optional<User> findByEmail(String email);
    Optional<User> findByUsernameAndEmail(String username, String email);

}
