package com.cs520group8.roommatematcher.service;

import com.cs520group8.roommatematcher.dto.UserDTO;
import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();
}
