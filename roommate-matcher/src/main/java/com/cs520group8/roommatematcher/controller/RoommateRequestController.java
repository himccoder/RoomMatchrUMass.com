package com.cs520group8.roommatematcher.controller;

import com.cs520group8.roommatematcher.dto.ApiResponse;
import com.cs520group8.roommatematcher.dto.RoommateRequestDTO;
import com.cs520group8.roommatematcher.dto.RoommateRequestResponse;
import com.cs520group8.roommatematcher.service.RoommateRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class RoommateRequestController {
    
    private final RoommateRequestService requestService;
    
    public RoommateRequestController(RoommateRequestService requestService) {
        this.requestService = requestService;
    }
    
    // Send a roommate request
    @PostMapping("/send/{senderId}")
    public ResponseEntity<?> sendRequest(
            @PathVariable Long senderId,
            @RequestBody RoommateRequestDTO request) {
        try {
            RoommateRequestResponse response = requestService.sendRequest(senderId, request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }
    
    // Get received requests
    @GetMapping("/received/{userId}")
    public ResponseEntity<List<RoommateRequestResponse>> getReceivedRequests(@PathVariable Long userId) {
        List<RoommateRequestResponse> requests = requestService.getReceivedRequests(userId);
        return ResponseEntity.ok(requests);
    }
    
    // Get sent requests
    @GetMapping("/sent/{userId}")
    public ResponseEntity<List<RoommateRequestResponse>> getSentRequests(@PathVariable Long userId) {
        List<RoommateRequestResponse> requests = requestService.getSentRequests(userId);
        return ResponseEntity.ok(requests);
    }
    
    // Accept a request
    @PostMapping("/accept/{requestId}/{userId}")
    public ResponseEntity<?> acceptRequest(
            @PathVariable Long requestId,
            @PathVariable Long userId) {
        try {
            RoommateRequestResponse response = requestService.acceptRequest(requestId, userId);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }
    
    // Reject a request
    @PostMapping("/reject/{requestId}/{userId}")
    public ResponseEntity<?> rejectRequest(
            @PathVariable Long requestId,
            @PathVariable Long userId) {
        try {
            RoommateRequestResponse response = requestService.rejectRequest(requestId, userId);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }
    
    // Cancel a sent request
    @DeleteMapping("/cancel/{requestId}/{userId}")
    public ResponseEntity<?> cancelRequest(
            @PathVariable Long requestId,
            @PathVariable Long userId) {
        try {
            requestService.cancelRequest(requestId, userId);
            return ResponseEntity.ok(new ApiResponse(true, "Request cancelled successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }
}

