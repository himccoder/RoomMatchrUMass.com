package com.cs520group8.roommatematcher.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cs520group8.roommatematcher.dto.ApiResponse;
import com.cs520group8.roommatematcher.dto.LoginRequest;
import com.cs520group8.roommatematcher.dto.RegisterRequest;
import com.cs520group8.roommatematcher.service.AuthService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody RegisterRequest request) {
        return authService.registerUser(request);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest request) {
        return authService.loginUser(request);
    }
}
