package com.cs520group8.roommatematcher.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cs520group8.roommatematcher.dto.SurveyRequest;
import com.cs520group8.roommatematcher.dto.SurveyResponse;
import com.cs520group8.roommatematcher.entity.UserSurvey;
import com.cs520group8.roommatematcher.repository.SurveyRepository;

@Service
public class SurveyServiceImpl implements SurveyService {
    private final SurveyRepository surveyRepository;

    public SurveyServiceImpl(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    @Override
    public ResponseEntity<SurveyResponse> submitSurvey(SurveyRequest surveyRequest) {
        if(surveyRepository.findByUserId(surveyRequest.getUserId()).isPresent()) {
            return ResponseEntity.status(409).body(new SurveyResponse(false, "Survey already submitted by this user."));
        }

        UserSurvey userSurvey = new UserSurvey();
        userSurvey.setUserId(surveyRequest.getUserId());
        userSurvey.setSleepSchedule(surveyRequest.getSleepSchedule());
        userSurvey.setPersonalityType(surveyRequest.getPersonalityType());
        userSurvey.setFoodType(surveyRequest.getFoodType());
        userSurvey.setPetsPreference(surveyRequest.getPetsPreference());
        userSurvey.setSmokingPreference(surveyRequest.getSmokingPreference());
        surveyRepository.save(userSurvey);
        return ResponseEntity.ok(new SurveyResponse(true, "Survey submitted successfully."));
    }
}