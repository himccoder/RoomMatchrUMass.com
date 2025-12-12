package com.cs520group8.roommatematcher.service;

import com.cs520group8.roommatematcher.dto.ApiResponse;
import com.cs520group8.roommatematcher.dto.LoginRequest;
import com.cs520group8.roommatematcher.dto.RegisterRequest;
import com.cs520group8.roommatematcher.entity.User;
import com.cs520group8.roommatematcher.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for AuthService.
 * 
 * Tests the business logic for user registration and login.
 * Uses Mockito to mock repository dependencies.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("AuthService Tests")
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        authService = new AuthServiceImpl(userRepository);
    }

    // Register User Tests

    @Test
    @DisplayName("registerUser - Should register user successfully")
    void testRegisterUser_Success() {
        RegisterRequest request = new RegisterRequest();
        request.setName("Test User");
        request.setEmail("test@umass.edu");
        request.setPassword("password123");

        when(userRepository.findByEmail("test@umass.edu")).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(new User());

        ResponseEntity<ApiResponse> response = authService.registerUser(request);

        assertEquals(200, response.getStatusCode().value());
        assertTrue(response.getBody().isSuccess());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("registerUser - Should reject non-umass email")
    void testRegisterUser_InvalidEmail() {
        RegisterRequest request = new RegisterRequest();
        request.setName("Test User");
        request.setEmail("test@gmail.com");
        request.setPassword("password123");

        ResponseEntity<ApiResponse> response = authService.registerUser(request);

        assertEquals(400, response.getStatusCode().value());
        assertFalse(response.getBody().isSuccess());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("registerUser - Should reject existing user")
    void testRegisterUser_UserExists() {
        RegisterRequest request = new RegisterRequest();
        request.setName("Test User");
        request.setEmail("test@umass.edu");
        request.setPassword("password123");

        User existingUser = new User();
        existingUser.setEmail("test@umass.edu");

        when(userRepository.findByEmail("test@umass.edu")).thenReturn(Optional.of(existingUser));

        ResponseEntity<ApiResponse> response = authService.registerUser(request);

        assertEquals(400, response.getStatusCode().value());
        assertFalse(response.getBody().isSuccess());
        verify(userRepository, never()).save(any(User.class));
    }

    // Login User Tests

    @Test
    @DisplayName("loginUser - Should login successfully")
    void testLoginUser_Success() {
        LoginRequest request = new LoginRequest();
        request.setEmail("test@umass.edu");
        request.setPassword("password123");

        User user = new User();
        user.setEmail("test@umass.edu");
        user.setPassword("password123");

        when(userRepository.findByEmail("test@umass.edu")).thenReturn(Optional.of(user));

        ResponseEntity<ApiResponse> response = authService.loginUser(request);

        assertEquals(200, response.getStatusCode().value());
        assertTrue(response.getBody().isSuccess());
    }

    @Test
    @DisplayName("loginUser - Should reject invalid credentials")
    void testLoginUser_InvalidCredentials() {
        LoginRequest request = new LoginRequest();
        request.setEmail("test@umass.edu");
        request.setPassword("wrongpassword");

        User user = new User();
        user.setEmail("test@umass.edu");
        user.setPassword("password123");

        when(userRepository.findByEmail("test@umass.edu")).thenReturn(Optional.of(user));

        ResponseEntity<ApiResponse> response = authService.loginUser(request);

        assertEquals(401, response.getStatusCode().value());
        assertFalse(response.getBody().isSuccess());
    }

    @Test
    @DisplayName("loginUser - Should reject non-existent user")
    void testLoginUser_UserNotFound() {
        LoginRequest request = new LoginRequest();
        request.setEmail("notfound@umass.edu");
        request.setPassword("password123");

        when(userRepository.findByEmail("notfound@umass.edu")).thenReturn(Optional.empty());

        ResponseEntity<ApiResponse> response = authService.loginUser(request);

        assertEquals(401, response.getStatusCode().value());
        assertFalse(response.getBody().isSuccess());
    }
}

