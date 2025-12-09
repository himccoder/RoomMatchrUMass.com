package com.cs520group8.roommatematcher.service;

import org.springframework.http.ResponseEntity;

import com.cs520group8.roommatematcher.dto.SurveyRequest;
import com.cs520group8.roommatematcher.dto.SurveyResponse;

public interface SurveyService {
    ResponseEntity<SurveyResponse> submitSurvey(SurveyRequest surveyRequest);
}
