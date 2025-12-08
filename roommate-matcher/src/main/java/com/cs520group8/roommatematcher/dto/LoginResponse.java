package com.cs520group8.roommatematcher.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private boolean success;
    private String message;
    private Long userId;
    private String name;
    private String email;
    private String token;  // JWT token
}
