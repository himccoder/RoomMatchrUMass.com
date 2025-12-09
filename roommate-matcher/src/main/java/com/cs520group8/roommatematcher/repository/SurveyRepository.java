package com.cs520group8.roommatematcher.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cs520group8.roommatematcher.entity.UserSurvey;

public interface SurveyRepository extends JpaRepository<UserSurvey, Long> {
    Optional<UserSurvey> findByUserId(Long userId);
}
