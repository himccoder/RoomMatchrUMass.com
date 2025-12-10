package com.cs520group8.roommatematcher.dto;

import lombok.Data;

@Data
public class UserWithScoreDTO {
    private Long id;
    private String name;
    private String email;
    private double percentMatch;
}
