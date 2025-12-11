package com.cs520group8.roommatematcher.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.cs520group8.roommatematcher.dto.SurveyRequest;
import com.cs520group8.roommatematcher.dto.SurveyResponse;
import com.cs520group8.roommatematcher.model.FoodType;
import com.cs520group8.roommatematcher.model.PersonalityType;
import com.cs520group8.roommatematcher.model.PetsPreference;
import com.cs520group8.roommatematcher.model.SleepSchedule;
import com.cs520group8.roommatematcher.model.SmokingPreference;
import com.cs520group8.roommatematcher.service.SurveyService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(SurveyController.class)
@DisplayName("SurveyController Tests")
public class SurveyControllerTest {
    @Autowired
    private MockMvc mockMvc; // This is used to simulate HTTP requests without starting a full server.

    @MockBean
    private SurveyService surveyService; // This is used to mock the SurveyService interface.

    @Autowired
    private ObjectMapper objectMapper; //  This is used to convert JSON to Java objects and vice versa
    
    // Submit Survey - Success Case
    @Test
    @DisplayName("POST /api/survey/submit - Success: Should submit survey responses")
    void testSubmitSurvey_Success() throws Exception {
        SurveyRequest request = new SurveyRequest();
        // Set up the request object as needed for the test
        request.setSleepSchedule(SleepSchedule.EARLY_BIRD);
        request.setPersonalityType(PersonalityType.INTROVERT);
        request.setFoodType(FoodType.FLEXIBLE);
        request.setPetsPreference(PetsPreference.NO_PETS);
        request.setSmokingPreference(SmokingPreference.NO_PREFERENCE);
        request.setUserId(1L);
        SurveyResponse expectedResponse = new SurveyResponse(true, "Survey submitted successfully.");
        when(surveyService.submitSurvey(request)).thenReturn(ResponseEntity.ok(expectedResponse));
        mockMvc.perform(post("/api/survey/submit")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    // Submit Survey - Failure Case - Survey Already Submitted
    @Test
    @DisplayName("POST /api/survey/submit - Failure: Should return error if survey already submitted")
    void testSubmitSurvey_Failure_SurveyAlreadySubmitted() throws Exception {
        SurveyRequest request = new SurveyRequest();
        // Set up the request object as needed for the test
        request.setSleepSchedule(SleepSchedule.EARLY_BIRD);
        request.setPersonalityType(PersonalityType.INTROVERT);
        request.setFoodType(FoodType.FLEXIBLE);
        request.setPetsPreference(PetsPreference.NO_PETS);
        request.setSmokingPreference(SmokingPreference.NO_PREFERENCE);
        request.setUserId(1L);
        SurveyResponse expectedResponse = new SurveyResponse(false, "Survey already submitted.");
        when(surveyService.submitSurvey(request)).thenReturn(ResponseEntity.status(409).body(expectedResponse));
        mockMvc.perform(post("/api/survey/submit")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());
    }
}
