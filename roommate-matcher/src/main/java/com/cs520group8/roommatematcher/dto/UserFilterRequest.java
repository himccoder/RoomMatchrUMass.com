package com.cs520group8.roommatematcher.dto;

import com.cs520group8.roommatematcher.model.FoodType;
import com.cs520group8.roommatematcher.model.PersonalityType;
import com.cs520group8.roommatematcher.model.PetsPreference;
import com.cs520group8.roommatematcher.model.SleepSchedule;
import com.cs520group8.roommatematcher.model.SmokingPreference;
import lombok.Data;

@Data
public class UserFilterRequest {

    private SleepSchedule sleepSchedule;
    private FoodType foodType;
    private PersonalityType personalityType;
    private PetsPreference petsPreference;
    private SmokingPreference smokingPreference;
}
