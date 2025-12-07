package com.cs520group8.roommatematcher.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cs520group8.roommatematcher.dto.ApiResponse;
import com.cs520group8.roommatematcher.dto.LoginRequest;
import com.cs520group8.roommatematcher.dto.RegisterRequest;
import com.cs520group8.roommatematcher.entity.User;
import com.cs520group8.roommatematcher.repository.UserRepository;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    
    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<ApiResponse> registerUser(RegisterRequest request) {
        // validate if user has umass.edu email
        if (!request.getEmail().endsWith("@umass.edu")) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Email must be a valid umass.edu address"));
        }
        // checking user already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "User with this email already exists. Login instead."));
        }
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        userRepository.save(user);
        return ResponseEntity.ok(new ApiResponse(true, "User registered successfully"));
    }

    @Override
    public ResponseEntity<ApiResponse> loginUser(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(false, "Invalid credentials"));
        }
        if (user.getPassword().equals(request.getPassword())) {
            return ResponseEntity.ok(new ApiResponse(true, "Login successful"));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(false, "Invalid credentials"));
        }
    }
}
