package com.cs520group8.roommatematcher.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cs520group8.roommatematcher.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);
}
