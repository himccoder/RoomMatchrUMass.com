package com.cs520group8.roommatematcher.service;

import com.cs520group8.roommatematcher.dto.SurveyRequest;
import com.cs520group8.roommatematcher.dto.SurveyResponse;
import com.cs520group8.roommatematcher.entity.UserSurvey;
import com.cs520group8.roommatematcher.model.FoodType;
import com.cs520group8.roommatematcher.model.PersonalityType;
import com.cs520group8.roommatematcher.model.PetsPreference;
import com.cs520group8.roommatematcher.model.SleepSchedule;
import com.cs520group8.roommatematcher.model.SmokingPreference;
import com.cs520group8.roommatematcher.repository.SurveyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for SurveyService.
 * 
 * Tests the business logic for survey submission.
 * Uses Mockito to mock repository dependencies.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("SurveyService Tests")
public class SurveyServiceTest {

    @Mock
    private SurveyRepository surveyRepository;

    private SurveyServiceImpl surveyService;

    @BeforeEach
    void setUp() {
        surveyService = new SurveyServiceImpl(surveyRepository);
    }

    // Submit Survey Tests

    @Test
    @DisplayName("submitSurvey - Should submit survey successfully")
    void testSubmitSurvey_Success() {
        SurveyRequest request = createSurveyRequest(1L);

        when(surveyRepository.findByUserId(1L)).thenReturn(Optional.empty());
        when(surveyRepository.save(any(UserSurvey.class))).thenReturn(new UserSurvey());

        ResponseEntity<SurveyResponse> response = surveyService.submitSurvey(request);

        assertEquals(200, response.getStatusCode().value());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Survey submitted successfully.", response.getBody().getMessage());
        verify(surveyRepository, times(1)).save(any(UserSurvey.class));
    }

    @Test
    @DisplayName("submitSurvey - Should return conflict when survey already exists")
    void testSubmitSurvey_AlreadyExists() {
        SurveyRequest request = createSurveyRequest(1L);
        UserSurvey existingSurvey = new UserSurvey();
        existingSurvey.setUserId(1L);

        when(surveyRepository.findByUserId(1L)).thenReturn(Optional.of(existingSurvey));

        ResponseEntity<SurveyResponse> response = surveyService.submitSurvey(request);

        assertEquals(409, response.getStatusCode().value());
        assertFalse(response.getBody().isSuccess());
        assertEquals("Survey already submitted by this user.", response.getBody().getMessage());
        verify(surveyRepository, never()).save(any(UserSurvey.class));
    }

    // Helper method

    private SurveyRequest createSurveyRequest(Long userId) {
        SurveyRequest request = new SurveyRequest();
        request.setUserId(userId);
        request.setSleepSchedule(SleepSchedule.EARLY_BIRD);
        request.setPersonalityType(PersonalityType.INTROVERT);
        request.setFoodType(FoodType.FLEXIBLE);
        request.setPetsPreference(PetsPreference.NO_PETS);
        request.setSmokingPreference(SmokingPreference.NO_PREFERENCE);
        return request;
    }
}

