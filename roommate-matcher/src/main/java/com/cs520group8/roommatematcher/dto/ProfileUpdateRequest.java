package com.cs520group8.roommatematcher.dto;

import lombok.Data;

@Data
public class ProfileUpdateRequest {
    private String name;
    private Integer age;
    private String gender;
    private String major;
    private String bio;
    private String graduationYear;
    
    // Living Preferences
    private String sleepSchedule;
    private String cleanlinessLevel;
    private String noisePreference;
    private Boolean smokingAllowed;
    private Boolean petsAllowed;
    private Boolean guestsAllowed;
    
    // Housing Preferences
    private Double budgetMin;
    private Double budgetMax;
    private String preferredLocation;
    private String moveInDate;
    
    private Boolean lookingForRoommate;
}

