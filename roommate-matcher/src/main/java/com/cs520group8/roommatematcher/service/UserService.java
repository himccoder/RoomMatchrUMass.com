package com.cs520group8.roommatematcher.service;

import com.cs520group8.roommatematcher.dto.UserDTO;
import com.cs520group8.roommatematcher.dto.UserFilterRequest;
import com.cs520group8.roommatematcher.dto.UserWithScoreDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();

    List<UserDTO> getFilteredUsers(UserFilterRequest filters);

    List<UserWithScoreDTO> getRecommendedUsers(Long userId);
}
