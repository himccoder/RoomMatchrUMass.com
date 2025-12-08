package com.cs520group8.roommatematcher.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConversationResponse {
    private Long partnerId;
    private String partnerName;
    private String partnerMajor;
    private String lastMessage;
    private LocalDateTime lastMessageTime;
    private Long unreadCount;
}

