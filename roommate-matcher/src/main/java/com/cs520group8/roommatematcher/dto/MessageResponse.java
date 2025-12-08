package com.cs520group8.roommatematcher.dto;

import com.cs520group8.roommatematcher.entity.Message;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageResponse {
    private Long id;
    private Long senderId;
    private String senderName;
    private Long receiverId;
    private String receiverName;
    private String content;
    private Boolean isRead;
    private LocalDateTime createdAt;
    
    public static MessageResponse fromEntity(Message message) {
        MessageResponse response = new MessageResponse();
        response.setId(message.getId());
        response.setSenderId(message.getSender().getId());
        response.setSenderName(message.getSender().getName());
        response.setReceiverId(message.getReceiver().getId());
        response.setReceiverName(message.getReceiver().getName());
        response.setContent(message.getContent());
        response.setIsRead(message.getIsRead());
        response.setCreatedAt(message.getCreatedAt());
        return response;
    }
}

