package com.cs520group8.roommatematcher.controller;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cs520group8.roommatematcher.dto.SurveyRequest;
import com.cs520group8.roommatematcher.dto.SurveyResponse;
import com.cs520group8.roommatematcher.service.SurveyService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/survey")
public class SurveyController {
    private final SurveyService surveyService;
    
    public SurveyController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @PostMapping("/submit")
    public ResponseEntity<SurveyResponse> submitSurvey(@RequestBody SurveyRequest surveyRequest) {
        return surveyService.submitSurvey(surveyRequest);
    }
}
