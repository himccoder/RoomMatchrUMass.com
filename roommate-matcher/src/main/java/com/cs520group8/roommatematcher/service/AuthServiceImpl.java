package com.cs520group8.roommatematcher.service;

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
    public ApiResponse registerUser(RegisterRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        userRepository.save(user);
        return new ApiResponse(true, "User registered successfully");
    }

    @Override
    public ApiResponse loginUser(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));
        if (user.getPassword().equals(request.getPassword())) {
            return new ApiResponse(true, "Login successful");
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }
}
