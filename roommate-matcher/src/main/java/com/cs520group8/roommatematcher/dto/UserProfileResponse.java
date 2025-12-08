package com.cs520group8.roommatematcher.dto;

import com.cs520group8.roommatematcher.entity.User;
import lombok.Data;

@Data
public class UserProfileResponse {
    private Long id;
    private String name;
    private String email;
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
    
    private Boolean profileComplete;
    private Boolean lookingForRoommate;
    
    // Match percentage (calculated when browsing)
    private Integer matchPercent;
    
    // Constructor from User entity
    public static UserProfileResponse fromUser(User user) {
        UserProfileResponse response = new UserProfileResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setAge(user.getAge());
        response.setGender(user.getGender());
        response.setMajor(user.getMajor());
        response.setBio(user.getBio());
        response.setGraduationYear(user.getGraduationYear());
        response.setSleepSchedule(user.getSleepSchedule());
        response.setCleanlinessLevel(user.getCleanlinessLevel());
        response.setNoisePreference(user.getNoisePreference());
        response.setSmokingAllowed(user.getSmokingAllowed());
        response.setPetsAllowed(user.getPetsAllowed());
        response.setGuestsAllowed(user.getGuestsAllowed());
        response.setBudgetMin(user.getBudgetMin());
        response.setBudgetMax(user.getBudgetMax());
        response.setPreferredLocation(user.getPreferredLocation());
        response.setMoveInDate(user.getMoveInDate());
        response.setProfileComplete(user.getProfileComplete());
        response.setLookingForRoommate(user.getLookingForRoommate());
        return response;
    }
    
    // Constructor with match percentage
    public static UserProfileResponse fromUserWithMatch(User user, int matchPercent) {
        UserProfileResponse response = fromUser(user);
        response.setMatchPercent(matchPercent);
        return response;
    }
}

