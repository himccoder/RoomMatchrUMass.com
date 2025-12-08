package com.cs520group8.roommatematcher.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private String password;
    
    // Profile Information
    private Integer age;
    
    private String gender;
    
    private String major;
    
    @Column(length = 1000)
    private String bio;
    
    private String graduationYear;
    
    // Living Preferences
    private String sleepSchedule;  // "early_bird", "night_owl", "flexible"
    
    private String cleanlinessLevel;  // "very_clean", "moderate", "relaxed"
    
    private String noisePreference;  // "quiet", "moderate", "social"
    
    private Boolean smokingAllowed;
    
    private Boolean petsAllowed;
    
    private Boolean guestsAllowed;
    
    // Housing Preferences
    private Double budgetMin;
    
    private Double budgetMax;
    
    private String preferredLocation;
    
    private String moveInDate;
    
    // Profile Status
    private Boolean profileComplete = false;
    
    private Boolean lookingForRoommate = true;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
