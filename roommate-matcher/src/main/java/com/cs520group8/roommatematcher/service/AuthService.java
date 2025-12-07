package com.cs520group8.roommatematcher.service;

import com.cs520group8.roommatematcher.dto.ApiResponse;
import com.cs520group8.roommatematcher.dto.LoginRequest;
import com.cs520group8.roommatematcher.dto.RegisterRequest;

public interface AuthService {
    ApiResponse registerUser(RegisterRequest request);
    ApiResponse loginUser(LoginRequest request);
}
