package com.cs520group8.roommatematcher.service;

import com.cs520group8.roommatematcher.dto.RoommateRequestDTO;

public interface RoommateRequestService {
    RoommateRequestDTO sendRequest(Long senderId, Long receiverId);
}
