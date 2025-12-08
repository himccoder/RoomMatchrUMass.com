package com.cs520group8.roommatematcher.dto;

import java.time.LocalDateTime;

public class RoommateRequestDTO {
    private Long requestId;
    private Long senderId;
    private Long receiverId;
    private String status;
    private LocalDateTime init_time;

}
