package com.cs520group8.roommatematcher.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SurveyResponse {
    private boolean success;
    private String message;
}
