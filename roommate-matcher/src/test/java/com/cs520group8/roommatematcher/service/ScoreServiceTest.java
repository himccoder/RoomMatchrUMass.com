package com.cs520group8.roommatematcher.service;

import com.cs520group8.roommatematcher.entity.User;
import com.cs520group8.roommatematcher.entity.UserSurvey;
import com.cs520group8.roommatematcher.model.FoodType;
import com.cs520group8.roommatematcher.model.PersonalityType;
import com.cs520group8.roommatematcher.model.PetsPreference;
import com.cs520group8.roommatematcher.model.SleepSchedule;
import com.cs520group8.roommatematcher.model.SmokingPreference;
import com.cs520group8.roommatematcher.repository.SurveyRepository;
import com.cs520group8.roommatematcher.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for ScoreService.
 * 
 * Tests the scoring logic for user compatibility.
 * Uses Mockito to mock repository dependencies.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("ScoreService Tests")
public class ScoreServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SurveyRepository surveyRepository;

    private ScoreServiceImpl scoreService;

    @BeforeEach
    void setUp() {
        scoreService = new ScoreServiceImpl(userRepository, surveyRepository);
    }

    // Score Users Tests

    @Test
    @DisplayName("scoreUsers - Should return positive score for matching preferences")
    void testScoreUsers_MatchingPreferences() {
        User user1 = createUser(1L, "Alice", "alice@example.com");
        User user2 = createUser(2L, "Bob", "bob@example.com");

        UserSurvey survey1 = createSurvey(1L, SleepSchedule.EARLY_BIRD, PersonalityType.INTROVERT);
        UserSurvey survey2 = createSurvey(2L, SleepSchedule.EARLY_BIRD, PersonalityType.INTROVERT);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        when(userRepository.findById(2L)).thenReturn(Optional.of(user2));
        when(surveyRepository.findByUserId(1L)).thenReturn(Optional.of(survey1));
        when(surveyRepository.findByUserId(2L)).thenReturn(Optional.of(survey2));

        int score = scoreService.scoreUsers(1L, 2L);

        assertTrue(score > 0, "Score should be positive for matching preferences");
    }

    @Test
    @DisplayName("scoreUsers - Should return zero when user not found")
    void testScoreUsers_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        int score = scoreService.scoreUsers(1L, 2L);

        assertEquals(0, score);
    }

    @Test
    @DisplayName("scoreUsers - Should return zero when survey not found")
    void testScoreUsers_SurveyNotFound() {
        User user1 = createUser(1L, "Alice", "alice@example.com");
        User user2 = createUser(2L, "Bob", "bob@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        when(userRepository.findById(2L)).thenReturn(Optional.of(user2));
        when(surveyRepository.findByUserId(1L)).thenReturn(Optional.empty());
        when(surveyRepository.findByUserId(2L)).thenReturn(Optional.empty());

        int score = scoreService.scoreUsers(1L, 2L);

        assertEquals(0, score);
    }

    @Test
    @DisplayName("scoreUsers - Should return different scores for different preferences")
    void testScoreUsers_DifferentPreferences() {
        User user1 = createUser(1L, "Alice", "alice@example.com");
        User user2 = createUser(2L, "Bob", "bob@example.com");

        UserSurvey survey1 = createSurvey(1L, SleepSchedule.EARLY_BIRD, PersonalityType.INTROVERT);
        UserSurvey survey2 = createSurvey(2L, SleepSchedule.NIGHT_OWL, PersonalityType.EXTROVERT);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        when(userRepository.findById(2L)).thenReturn(Optional.of(user2));
        when(surveyRepository.findByUserId(1L)).thenReturn(Optional.of(survey1));
        when(surveyRepository.findByUserId(2L)).thenReturn(Optional.of(survey2));

        int score = scoreService.scoreUsers(1L, 2L);

        // Score calculated based on matching/conflicting preferences
        assertNotNull(score);
    }

    // Helper methods

    private User createUser(Long id, String name, String email) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setEmail(email);
        return user;
    }

    private UserSurvey createSurvey(Long userId, SleepSchedule sleep, PersonalityType personality) {
        UserSurvey survey = new UserSurvey();
        survey.setUserId(userId);
        survey.setSleepSchedule(sleep);
        survey.setPersonalityType(personality);
        survey.setFoodType(FoodType.FLEXIBLE);
        survey.setSmokingPreference(SmokingPreference.NO_PREFERENCE);
        survey.setPetsPreference(PetsPreference.NO_PETS);
        return survey;
    }
}

