package com.cs520group8.roommatematcher.service;

import com.cs520group8.roommatematcher.dto.UserDTO;
import com.cs520group8.roommatematcher.dto.UserFilterRequest;
import com.cs520group8.roommatematcher.entity.User;
import com.cs520group8.roommatematcher.entity.UserSurvey;
import com.cs520group8.roommatematcher.model.SleepSchedule;
import com.cs520group8.roommatematcher.repository.SurveyRepository;
import com.cs520group8.roommatematcher.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for UserService.
 * 
 * Tests the business logic for user retrieval and filtering.
 * Uses Mockito to mock repository dependencies.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("UserService Tests")
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SurveyRepository surveyRepository;

    @Mock
    private ScoreService scoreService;

    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository, surveyRepository, scoreService);
    }

    //  GET ALL USERS TESTS ------------------

    @Test
    @DisplayName("getAllUsers - Should return all users as DTOs")
    void testGetAllUsers_ReturnsAllUsers() {
        // ARRANGE: Create mock users
        User alice = createUser(1L, "Alice", "alice@example.com");
        User bob = createUser(2L, "Bob", "bob@example.com");

        when(userRepository.findAll()).thenReturn(Arrays.asList(alice, bob));

        //  Call the service method
        List<UserDTO> result = userService.getAllUsers();

        // Verify correct number and data
        assertEquals(2, result.size());
        assertEquals("Alice", result.get(0).getName());
        assertEquals("alice@example.com", result.get(0).getEmail());
        assertEquals("Bob", result.get(1).getName());
        assertEquals("bob@example.com", result.get(1).getEmail());

        // Verify repository was called exactly once
        verify(userRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("getAllUsers - Should return empty list when no users exist")
    void testGetAllUsers_ReturnsEmptyList() {
        //  Return empty list from repository
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

    
        List<UserDTO> result = userService.getAllUsers();

        
        assertTrue(result.isEmpty());
    }

    //  GET FILTERED USERS TESTS ------------------

    @Test
    @DisplayName("getFilteredUsers - Should filter users by sleep schedule")
    void testGetFilteredUsers_BySleepSchedule() {
        // Create users and surveys
        User alice = createUser(1L, "Alice", "alice@example.com");
        User bob = createUser(2L, "Bob", "bob@example.com");

        UserSurvey aliceSurvey = new UserSurvey();
        aliceSurvey.setUserId(1L);
        aliceSurvey.setSleepSchedule(SleepSchedule.EARLY_BIRD);

        UserSurvey bobSurvey = new UserSurvey();
        bobSurvey.setUserId(2L);
        bobSurvey.setSleepSchedule(SleepSchedule.NIGHT_OWL);

        UserFilterRequest filters = new UserFilterRequest();
        filters.setSleepSchedule(SleepSchedule.EARLY_BIRD);

        when(userRepository.findAll()).thenReturn(Arrays.asList(alice, bob));
        when(surveyRepository.findByUserId(1L)).thenReturn(Optional.of(aliceSurvey));
        when(surveyRepository.findByUserId(2L)).thenReturn(Optional.of(bobSurvey));

    
        List<UserDTO> result = userService.getFilteredUsers(filters);

        //  Only Alice should match (early bird)
        assertEquals(1, result.size());
        assertEquals("Alice", result.get(0).getName());
    }

    @Test
    @DisplayName("getFilteredUsers - Should exclude users without survey")
    void testGetFilteredUsers_ExcludesUsersWithoutSurvey() {
        //  User without survey
        User alice = createUser(1L, "Alice", "alice@example.com");

        UserFilterRequest filters = new UserFilterRequest();
        filters.setSleepSchedule(SleepSchedule.EARLY_BIRD);

        when(userRepository.findAll()).thenReturn(Arrays.asList(alice));
        when(surveyRepository.findByUserId(1L)).thenReturn(Optional.empty());

    
        List<UserDTO> result = userService.getFilteredUsers(filters);

        //  No users should match
        assertTrue(result.isEmpty());
    }

    //  HELPER METHODS ------------------

    /**
     * Helper method to create a User entity for testing.
     */
    private User createUser(Long id, String name, String email) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setEmail(email);
        return user;
    }
}
