package com.cs520group8.roommatematcher.controller;

import com.cs520group8.roommatematcher.dto.ApiResponse;
import com.cs520group8.roommatematcher.dto.LoginRequest;
import com.cs520group8.roommatematcher.dto.RegisterRequest;
import com.cs520group8.roommatematcher.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for AuthController.
 * 
 * These are the tests for /api/auth/register and /api/auth/login endpoints.
 * Uses MockMvc to simulate HTTP requests without starting a full server.
 */

@WebMvcTest(AuthController.class)
@DisplayName("AuthController Tests")
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc; // This is used to simulate HTTP requests without starting a full server.

    @MockBean
    private AuthService authService; // This is used to mock the AuthService interface.

    @Autowired
    private ObjectMapper objectMapper; //  This is used to convert JSON to Java objects and vice versa

    //  Register API Tests

    @Test
    @DisplayName("POST /api/auth/register - Success: Should register a new user")
    void testRegisterUser_Success() throws Exception {
         // Prepare request and expected response
        RegisterRequest request = new RegisterRequest();
        request.setName("Test User");
        request.setEmail("test@example.com");
        request.setPassword("password123");

        ApiResponse expectedResponse = new ApiResponse(true, "User registered successfully");

        when(authService.registerUser(any(RegisterRequest.class))) 
                .thenReturn(ResponseEntity.ok(expectedResponse));

        // Send request and verify response
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("User registered successfully"));
    }

    //  Login API Tests

    @Test
    @DisplayName("POST /api/auth/login - Success: Should login existing user")
    void testLoginUser_Success() throws Exception {
        // Prepare request and expected response
        LoginRequest request = new LoginRequest();
        request.setEmail("test@example.com");
        request.setPassword("password123");

        ApiResponse expectedResponse = new ApiResponse(true, "Login successful");

        when(authService.loginUser(any(LoginRequest.class)))
                .thenReturn(ResponseEntity.ok(expectedResponse));

        // Send request and verify response
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Login successful"));
    }

    @Test
    @DisplayName("POST /api/auth/login - Failure: Should reject invalid credentials")
    void testLoginUser_InvalidCredentials() throws Exception {
        // ARRANGE: Prepare request with wrong password
        LoginRequest request = new LoginRequest();
        request.setEmail("test@example.com");
        request.setPassword("wrongpassword");

        ApiResponse expectedResponse = new ApiResponse(false, "Invalid email or password");

        when(authService.loginUser(any(LoginRequest.class)))
                .thenReturn(ResponseEntity.status(401).body(expectedResponse));

        // ACT & ASSERT: Send request and verify 401 status
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.success").value(false));
    }
}