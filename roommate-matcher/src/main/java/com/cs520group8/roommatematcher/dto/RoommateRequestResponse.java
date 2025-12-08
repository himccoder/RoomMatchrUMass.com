package com.cs520group8.roommatematcher.dto;

import com.cs520group8.roommatematcher.entity.RoommateRequest;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RoommateRequestResponse {
    private Long id;
    private Long senderId;
    private String senderName;
    private String senderMajor;
    private Integer senderAge;
    private Long receiverId;
    private String receiverName;
    private String status;
    private String message;
    private LocalDateTime createdAt;
    
    public static RoommateRequestResponse fromEntity(RoommateRequest request) {
        RoommateRequestResponse response = new RoommateRequestResponse();
        response.setId(request.getId());
        response.setSenderId(request.getSender().getId());
        response.setSenderName(request.getSender().getName());
        response.setSenderMajor(request.getSender().getMajor());
        response.setSenderAge(request.getSender().getAge());
        response.setReceiverId(request.getReceiver().getId());
        response.setReceiverName(request.getReceiver().getName());
        response.setStatus(request.getStatus().name());
        response.setMessage(request.getMessage());
        response.setCreatedAt(request.getCreatedAt());
        return response;
    }
}

