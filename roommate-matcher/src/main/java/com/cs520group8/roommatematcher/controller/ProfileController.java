package com.cs520group8.roommatematcher.controller;

import com.cs520group8.roommatematcher.dto.ProfileUpdateRequest;
import com.cs520group8.roommatematcher.dto.UserProfileResponse;
import com.cs520group8.roommatematcher.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {
    
    private final ProfileService profileService;
    
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }
    
    // Get current user's profile
    @GetMapping("/{userId}")
    public ResponseEntity<UserProfileResponse> getProfile(@PathVariable Long userId) {
        try {
            UserProfileResponse profile = profileService.getProfile(userId);
            return ResponseEntity.ok(profile);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Update profile
    @PutMapping("/{userId}")
    public ResponseEntity<UserProfileResponse> updateProfile(
            @PathVariable Long userId,
            @RequestBody ProfileUpdateRequest request) {
        try {
            UserProfileResponse profile = profileService.updateProfile(userId, request);
            return ResponseEntity.ok(profile);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    // Browse all profiles (marketplace)
    @GetMapping("/browse/{currentUserId}")
    public ResponseEntity<List<UserProfileResponse>> browseProfiles(@PathVariable Long currentUserId) {
        List<UserProfileResponse> profiles = profileService.getBrowseProfiles(currentUserId);
        return ResponseEntity.ok(profiles);
    }
}

