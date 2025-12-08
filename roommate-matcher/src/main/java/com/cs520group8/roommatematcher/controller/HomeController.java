package com.cs520group8.roommatematcher.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HomeController {
    
    @GetMapping("/")
    public Map<String, Object> home() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "🏠 Welcome to RoomMatchr API!");
        response.put("status", "running");
        response.put("version", "1.0.0");
        
        Map<String, String> endpoints = new HashMap<>();
        endpoints.put("register", "POST /api/auth/register");
        endpoints.put("login", "POST /api/auth/login");
        endpoints.put("getProfile", "GET /api/profiles/{userId}");
        endpoints.put("updateProfile", "PUT /api/profiles/{userId}");
        endpoints.put("browseProfiles", "GET /api/profiles/browse/{currentUserId}");
        endpoints.put("sendRequest", "POST /api/requests/send/{senderId}");
        endpoints.put("receivedRequests", "GET /api/requests/received/{userId}");
        endpoints.put("acceptRequest", "POST /api/requests/accept/{requestId}/{userId}");
        endpoints.put("sendMessage", "POST /api/messages/send/{senderId}");
        endpoints.put("getConversation", "GET /api/messages/conversation/{userId}/{otherUserId}");
        endpoints.put("getConversations", "GET /api/messages/conversations/{userId}");
        
        response.put("availableEndpoints", endpoints);
        return response;
    }
    
    @GetMapping("/health")
    public Map<String, String> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("message", "RoomMatchr API is healthy!");
        return response;
    }
}

