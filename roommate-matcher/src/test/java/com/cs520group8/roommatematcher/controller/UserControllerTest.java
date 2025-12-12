package com.cs520group8.roommatematcher.controller;

import com.cs520group8.roommatematcher.dto.UserDTO;
import com.cs520group8.roommatematcher.dto.UserFilterRequest;
import com.cs520group8.roommatematcher.dto.UserWithScoreDTO;
import com.cs520group8.roommatematcher.model.SleepSchedule;
import com.cs520group8.roommatematcher.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for UserController.
 * 
 * Tests the /api/users endpoints.
 * Uses MockMvc to simulate HTTP requests without starting a full server.
 */
@WebMvcTest(UserController.class)
@DisplayName("UserController Tests")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    // Get All Users Tests

    @Test
    @DisplayName("POST /api/users/getAllUsers - Success: Should return all users")
    void testGetAllUsers_Success() throws Exception {
        UserDTO user1 = createUserDTO(1L, "Alice", "alice@example.com");
        UserDTO user2 = createUserDTO(2L, "Bob", "bob@example.com");

        when(userService.getAllUsers()).thenReturn(Arrays.asList(user1, user2));

        mockMvc.perform(post("/api/users/getAllUsers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Alice"))
                .andExpect(jsonPath("$[1].name").value("Bob"));
    }

    @Test
    @DisplayName("POST /api/users/getAllUsers - Success: Should return empty list")
    void testGetAllUsers_EmptyList() throws Exception {
        when(userService.getAllUsers()).thenReturn(Collections.emptyList());

        mockMvc.perform(post("/api/users/getAllUsers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    // Get Filtered Users Tests

    @Test
    @DisplayName("POST /api/users/getFilteredUsers - Success: Should return filtered users")
    void testGetFilteredUsers_Success() throws Exception {
        UserFilterRequest filters = new UserFilterRequest();
        filters.setSleepSchedule(SleepSchedule.EARLY_BIRD);

        UserDTO user1 = createUserDTO(1L, "Alice", "alice@example.com");

        when(userService.getFilteredUsers(any(UserFilterRequest.class)))
                .thenReturn(Arrays.asList(user1));

        mockMvc.perform(post("/api/users/getFilteredUsers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(filters)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Alice"));
    }

    // Get Recommended Users Tests

    @Test
    @DisplayName("POST /api/users/getRecommendedUsers - Success: Should return recommended users")
    void testGetRecommendedUsers_Success() throws Exception {
        UserWithScoreDTO user1 = createUserWithScoreDTO(1L, "Alice", "alice@example.com", 85.0);

        when(userService.getRecommendedUsers(1L)).thenReturn(Arrays.asList(user1));

        mockMvc.perform(post("/api/users/getRecommendedUsers")
                        .param("userId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Alice"));
    }

    // Helper methods

    private UserDTO createUserDTO(Long id, String name, String email) {
        UserDTO user = new UserDTO();
        user.setId(id);
        user.setName(name);
        user.setEmail(email);
        return user;
    }

    private UserWithScoreDTO createUserWithScoreDTO(Long id, String name, String email, double score) {
        UserWithScoreDTO user = new UserWithScoreDTO();
        user.setId(id);
        user.setName(name);
        user.setEmail(email);
        user.setPercentMatch(score);
        return user;
    }
}

