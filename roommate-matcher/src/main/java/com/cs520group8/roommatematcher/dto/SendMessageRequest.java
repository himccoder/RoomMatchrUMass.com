package com.cs520group8.roommatematcher.dto;

import lombok.Data;

@Data
public class SendMessageRequest {
    private Long receiverId;
    private String content;
}

