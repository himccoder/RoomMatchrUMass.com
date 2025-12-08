package com.cs520group8.roommatematcher.service;

import org.springframework.http.ResponseEntity;

import com.cs520group8.roommatematcher.dto.ApiResponse;
import com.cs520group8.roommatematcher.dto.LoginRequest;
import com.cs520group8.roommatematcher.dto.LoginResponse;
import com.cs520group8.roommatematcher.dto.RegisterRequest;

public interface AuthService {
    ResponseEntity<ApiResponse> registerUser(RegisterRequest request);
    ResponseEntity<LoginResponse> loginUser(LoginRequest request);
}
