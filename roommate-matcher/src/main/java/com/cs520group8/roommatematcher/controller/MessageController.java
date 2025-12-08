package com.cs520group8.roommatematcher.controller;

import com.cs520group8.roommatematcher.dto.ApiResponse;
import com.cs520group8.roommatematcher.dto.ConversationResponse;
import com.cs520group8.roommatematcher.dto.MessageResponse;
import com.cs520group8.roommatematcher.dto.SendMessageRequest;
import com.cs520group8.roommatematcher.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    
    private final MessageService messageService;
    
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }
    
    // Send a message
    @PostMapping("/send/{senderId}")
    public ResponseEntity<?> sendMessage(
            @PathVariable Long senderId,
            @RequestBody SendMessageRequest request) {
        try {
            MessageResponse response = messageService.sendMessage(senderId, request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }
    
    // Get conversation with a specific user
    @GetMapping("/conversation/{userId}/{otherUserId}")
    public ResponseEntity<List<MessageResponse>> getConversation(
            @PathVariable Long userId,
            @PathVariable Long otherUserId) {
        List<MessageResponse> messages = messageService.getConversation(userId, otherUserId);
        return ResponseEntity.ok(messages);
    }
    
    // Get all conversations for a user
    @GetMapping("/conversations/{userId}")
    public ResponseEntity<List<ConversationResponse>> getConversations(@PathVariable Long userId) {
        List<ConversationResponse> conversations = messageService.getConversations(userId);
        return ResponseEntity.ok(conversations);
    }
    
    // Mark messages as read
    @PostMapping("/read/{userId}/{senderId}")
    public ResponseEntity<?> markAsRead(
            @PathVariable Long userId,
            @PathVariable Long senderId) {
        messageService.markMessagesAsRead(userId, senderId);
        return ResponseEntity.ok(new ApiResponse(true, "Messages marked as read"));
    }
    
    // Get unread message count
    @GetMapping("/unread/{userId}")
    public ResponseEntity<Long> getUnreadCount(@PathVariable Long userId) {
        Long count = messageService.getUnreadCount(userId);
        return ResponseEntity.ok(count);
    }
}

