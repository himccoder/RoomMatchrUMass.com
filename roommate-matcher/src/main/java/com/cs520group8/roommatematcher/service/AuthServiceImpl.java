package com.cs520group8.roommatematcher.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cs520group8.roommatematcher.dto.ApiResponse;
import com.cs520group8.roommatematcher.dto.LoginRequest;
import com.cs520group8.roommatematcher.dto.LoginResponse;
import com.cs520group8.roommatematcher.dto.RegisterRequest;
import com.cs520group8.roommatematcher.entity.User;
import com.cs520group8.roommatematcher.repository.UserRepository;
import com.cs520group8.roommatematcher.security.JwtUtil;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    
    public AuthServiceImpl(UserRepository userRepository, 
                          PasswordEncoder passwordEncoder,
                          JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public ResponseEntity<ApiResponse> registerUser(RegisterRequest request) {
        // validate if user has umass.edu email
        if (!request.getEmail().endsWith("@umass.edu")) {
            return ResponseEntity.badRequest()
                .body(new ApiResponse(false, "Email must be a valid umass.edu address"));
        }
        
        // checking user already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest()
                .body(new ApiResponse(false, "User with this email already exists. Login instead."));
        }
        
        // Create new user with hashed password
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // Hash password!
        user.setLookingForRoommate(true);
        user.setProfileComplete(false);
        userRepository.save(user);
        
        return ResponseEntity.ok(new ApiResponse(true, "User registered successfully"));
    }

    @Override
    public ResponseEntity<LoginResponse> loginUser(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElse(null);
        
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new LoginResponse(false, "Invalid credentials", null, null, null, null));
        }
        
        // Verify password using password encoder
        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            // Generate JWT token
            String token = jwtUtil.generateToken(user.getId(), user.getEmail());
            
            return ResponseEntity.ok(new LoginResponse(
                true, 
                "Login successful", 
                user.getId(), 
                user.getName(), 
                user.getEmail(),
                token  // Include JWT token in response
            ));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new LoginResponse(false, "Invalid credentials", null, null, null, null));
        }
    }
}
