package com.cs520group8.roommatematcher.service;

import com.cs520group8.roommatematcher.dto.ProfileUpdateRequest;
import com.cs520group8.roommatematcher.dto.UserProfileResponse;
import com.cs520group8.roommatematcher.entity.User;
import com.cs520group8.roommatematcher.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfileService {
    
    private final UserRepository userRepository;
    
    public ProfileService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    // Get user profile by ID
    public UserProfileResponse getProfile(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        return UserProfileResponse.fromUser(user);
    }
    
    // Update user profile
    public UserProfileResponse updateProfile(Long userId, ProfileUpdateRequest request) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Update fields if provided
        if (request.getName() != null) user.setName(request.getName());
        if (request.getAge() != null) user.setAge(request.getAge());
        if (request.getGender() != null) user.setGender(request.getGender());
        if (request.getMajor() != null) user.setMajor(request.getMajor());
        if (request.getBio() != null) user.setBio(request.getBio());
        if (request.getGraduationYear() != null) user.setGraduationYear(request.getGraduationYear());
        
        // Living Preferences
        if (request.getSleepSchedule() != null) user.setSleepSchedule(request.getSleepSchedule());
        if (request.getCleanlinessLevel() != null) user.setCleanlinessLevel(request.getCleanlinessLevel());
        if (request.getNoisePreference() != null) user.setNoisePreference(request.getNoisePreference());
        if (request.getSmokingAllowed() != null) user.setSmokingAllowed(request.getSmokingAllowed());
        if (request.getPetsAllowed() != null) user.setPetsAllowed(request.getPetsAllowed());
        if (request.getGuestsAllowed() != null) user.setGuestsAllowed(request.getGuestsAllowed());
        
        // Housing Preferences
        if (request.getBudgetMin() != null) user.setBudgetMin(request.getBudgetMin());
        if (request.getBudgetMax() != null) user.setBudgetMax(request.getBudgetMax());
        if (request.getPreferredLocation() != null) user.setPreferredLocation(request.getPreferredLocation());
        if (request.getMoveInDate() != null) user.setMoveInDate(request.getMoveInDate());
        
        if (request.getLookingForRoommate() != null) user.setLookingForRoommate(request.getLookingForRoommate());
        
        // Check if profile is complete
        user.setProfileComplete(isProfileComplete(user));
        
        userRepository.save(user);
        return UserProfileResponse.fromUser(user);
    }
    
    // Get all users looking for roommates (browse marketplace)
    public List<UserProfileResponse> getBrowseProfiles(Long currentUserId) {
        List<User> users = userRepository.findAll();
        
        User currentUser = userRepository.findById(currentUserId).orElse(null);
        
        return users.stream()
            .filter(u -> !u.getId().equals(currentUserId))  // Exclude current user
            .filter(u -> Boolean.TRUE.equals(u.getLookingForRoommate()))  // Only looking for roommates
            .map(u -> {
                int matchPercent = calculateMatchPercent(currentUser, u);
                return UserProfileResponse.fromUserWithMatch(u, matchPercent);
            })
            .sorted((a, b) -> b.getMatchPercent() - a.getMatchPercent())  // Sort by match %
            .collect(Collectors.toList());
    }
    
    // Calculate match percentage between two users using weighted scoring
    private int calculateMatchPercent(User currentUser, User otherUser) {
        if (currentUser == null) return 50;  // Default if not logged in
        
        double totalWeight = 0;
        double earnedScore = 0;
        
        // ===== LIFESTYLE COMPATIBILITY (60% of total score) =====
        
        // Sleep schedule match - CRITICAL for roommates (weight: 20)
        if (currentUser.getSleepSchedule() != null && otherUser.getSleepSchedule() != null) {
            totalWeight += 20;
            if (currentUser.getSleepSchedule().equals(otherUser.getSleepSchedule())) {
                earnedScore += 20;  // Perfect match
            } else if ("flexible".equals(currentUser.getSleepSchedule()) || "flexible".equals(otherUser.getSleepSchedule())) {
                earnedScore += 15;  // Flexible is compatible with anything
            } else {
                earnedScore += 0;   // Mismatch (early_bird vs night_owl)
            }
        }
        
        // Cleanliness match - CRITICAL for roommates (weight: 20)
        if (currentUser.getCleanlinessLevel() != null && otherUser.getCleanlinessLevel() != null) {
            totalWeight += 20;
            if (currentUser.getCleanlinessLevel().equals(otherUser.getCleanlinessLevel())) {
                earnedScore += 20;  // Perfect match
            } else if (isAdjacentCleanliness(currentUser.getCleanlinessLevel(), otherUser.getCleanlinessLevel())) {
                earnedScore += 12;  // Adjacent levels (e.g., very_clean & moderate)
            } else {
                earnedScore += 0;   // Extreme mismatch (very_clean vs relaxed)
            }
        }
        
        // Noise preference match - Important (weight: 15)
        if (currentUser.getNoisePreference() != null && otherUser.getNoisePreference() != null) {
            totalWeight += 15;
            if (currentUser.getNoisePreference().equals(otherUser.getNoisePreference())) {
                earnedScore += 15;  // Perfect match
            } else if ("moderate".equals(currentUser.getNoisePreference()) || "moderate".equals(otherUser.getNoisePreference())) {
                earnedScore += 10;  // Moderate is somewhat compatible
            } else {
                earnedScore += 0;   // Mismatch (quiet vs social)
            }
        }
        
        // ===== DEAL-BREAKERS (25% of total score) =====
        
        // Smoking preference - Deal-breaker for many (weight: 10)
        if (currentUser.getSmokingAllowed() != null && otherUser.getSmokingAllowed() != null) {
            totalWeight += 10;
            if (currentUser.getSmokingAllowed().equals(otherUser.getSmokingAllowed())) {
                earnedScore += 10;  // Match
            } else if (Boolean.FALSE.equals(currentUser.getSmokingAllowed()) && Boolean.TRUE.equals(otherUser.getSmokingAllowed())) {
                earnedScore += 0;   // User doesn't want smoking, other smokes - bad
            } else {
                earnedScore += 5;   // User allows smoking, other doesn't - acceptable
            }
        }
        
        // Pets preference (weight: 8)
        if (currentUser.getPetsAllowed() != null && otherUser.getPetsAllowed() != null) {
            totalWeight += 8;
            if (currentUser.getPetsAllowed().equals(otherUser.getPetsAllowed())) {
                earnedScore += 8;
            } else if (Boolean.FALSE.equals(currentUser.getPetsAllowed()) && Boolean.TRUE.equals(otherUser.getPetsAllowed())) {
                earnedScore += 0;   // User doesn't want pets, other has pets - bad
            } else {
                earnedScore += 5;
            }
        }
        
        // Guests preference (weight: 7)
        if (currentUser.getGuestsAllowed() != null && otherUser.getGuestsAllowed() != null) {
            totalWeight += 7;
            if (currentUser.getGuestsAllowed().equals(otherUser.getGuestsAllowed())) {
                earnedScore += 7;
            } else {
                earnedScore += 3;   // Guest preferences are more flexible
            }
        }
        
        // ===== PRACTICAL FACTORS (15% of total score) =====
        
        // Budget overlap - Practical necessity (weight: 10)
        if (hasBudgetInfo(currentUser) && hasBudgetInfo(otherUser)) {
            totalWeight += 10;
            if (budgetsOverlap(currentUser, otherUser)) {
                // Calculate how much they overlap
                double overlapScore = calculateBudgetOverlapScore(currentUser, otherUser);
                earnedScore += 10 * overlapScore;
            }
        }
        
        // Same graduation year - Nice to have (weight: 5)
        if (currentUser.getGraduationYear() != null && otherUser.getGraduationYear() != null) {
            totalWeight += 5;
            if (currentUser.getGraduationYear().equals(otherUser.getGraduationYear())) {
                earnedScore += 5;   // Same year
            } else {
                // Check if within 1 year
                try {
                    int year1 = Integer.parseInt(currentUser.getGraduationYear());
                    int year2 = Integer.parseInt(otherUser.getGraduationYear());
                    if (Math.abs(year1 - year2) == 1) {
                        earnedScore += 3;   // Adjacent year
                    }
                } catch (NumberFormatException e) {
                    // Ignore
                }
            }
        }
        
        // If no factors to compare, return default
        if (totalWeight == 0) return 50;
        
        // Calculate percentage
        int matchPercent = (int) Math.round((earnedScore / totalWeight) * 100);
        return Math.max(0, Math.min(100, matchPercent));
    }
    
    // Check if cleanliness levels are adjacent (e.g., very_clean & moderate)
    private boolean isAdjacentCleanliness(String level1, String level2) {
        if (level1 == null || level2 == null) return false;
        
        // Order: very_clean -> moderate -> relaxed
        if ("moderate".equals(level1) || "moderate".equals(level2)) {
            return true;  // moderate is adjacent to both
        }
        return false;  // very_clean and relaxed are not adjacent
    }
    
    private boolean hasBudgetInfo(User user) {
        return user.getBudgetMin() != null || user.getBudgetMax() != null;
    }
    
    // Calculate how well budgets overlap (0.0 to 1.0)
    private double calculateBudgetOverlapScore(User u1, User u2) {
        double min1 = u1.getBudgetMin() != null ? u1.getBudgetMin() : 0;
        double max1 = u1.getBudgetMax() != null ? u1.getBudgetMax() : 5000;
        double min2 = u2.getBudgetMin() != null ? u2.getBudgetMin() : 0;
        double max2 = u2.getBudgetMax() != null ? u2.getBudgetMax() : 5000;
        
        double overlapStart = Math.max(min1, min2);
        double overlapEnd = Math.min(max1, max2);
        
        if (overlapStart > overlapEnd) return 0.0;  // No overlap
        
        double overlapSize = overlapEnd - overlapStart;
        double smallerRange = Math.min(max1 - min1, max2 - min2);
        
        if (smallerRange <= 0) return 1.0;  // Both have same budget point
        
        return Math.min(1.0, overlapSize / smallerRange);
    }
    
    private boolean budgetsOverlap(User u1, User u2) {
        double min1 = u1.getBudgetMin() != null ? u1.getBudgetMin() : 0;
        double max1 = u1.getBudgetMax() != null ? u1.getBudgetMax() : Double.MAX_VALUE;
        double min2 = u2.getBudgetMin() != null ? u2.getBudgetMin() : 0;
        double max2 = u2.getBudgetMax() != null ? u2.getBudgetMax() : Double.MAX_VALUE;
        
        return min1 <= max2 && min2 <= max1;
    }
    
    private boolean isProfileComplete(User user) {
        return user.getName() != null && !user.getName().isEmpty()
            && user.getAge() != null
            && user.getMajor() != null && !user.getMajor().isEmpty()
            && user.getBio() != null && !user.getBio().isEmpty();
    }
}

