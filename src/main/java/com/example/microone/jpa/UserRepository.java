package com.example.microone.jpa;

import com.example.microone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    @Modifying
    @Query("UPDATE User u SET u.balance = u.balance + :amount WHERE u.uuid = :userId")
    void addBalance(@Param("userId") UUID userId, Double amount);

    Optional<User> findByUsername(String username);
    Optional<User> findByUuid(@Param("uuid") UUID uuid);
}
