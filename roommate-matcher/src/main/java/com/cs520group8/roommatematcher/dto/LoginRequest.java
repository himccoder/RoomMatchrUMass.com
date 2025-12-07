package com.cs520group8.roommatematcher.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
