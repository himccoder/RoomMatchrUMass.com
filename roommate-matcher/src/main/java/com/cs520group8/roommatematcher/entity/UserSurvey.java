package com.cs520group8.roommatematcher.entity;

import com.cs520group8.roommatematcher.model.FoodType;
import com.cs520group8.roommatematcher.model.PersonalityType;
import com.cs520group8.roommatematcher.model.PetsPreference;
import com.cs520group8.roommatematcher.model.SleepSchedule;
import com.cs520group8.roommatematcher.model.SmokingPreference;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_surveys")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSurvey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    private SleepSchedule sleepSchedule;

    @Enumerated(EnumType.STRING)
    private FoodType foodType;    

    @Enumerated(EnumType.STRING)
    private PersonalityType personalityType;

    @Enumerated(EnumType.STRING)
    private PetsPreference petsPreference;

    @Enumerated(EnumType.STRING)
    private SmokingPreference smokingPreference;
}
