package com.cs520group8.roommatematcher.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cs520group8.roommatematcher.dto.ApiResponse;
import com.cs520group8.roommatematcher.dto.RoommateRequestDTO;
import com.cs520group8.roommatematcher.service.AuthService;
import com.cs520group8.roommatematcher.service.RoommateRequestService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cs520group8.roommatematcher.dto.LoginRequest;
import com.cs520group8.roommatematcher.dto.RegisterRequest;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/roommate_request")
public class RoommateRequestController {

    private final RoommateRequestService rrservice;

    public RoommateRequestController(RoommateRequestService rrservice) {
        this.rrservice = rrservice;
    }

    // @PostMapping("/send")
    // public ResponseEntity<RoommateRequestDTO> send(Long senderId, Long
    // receiverId) {
    // return ResponseEntity.ok(
    // new RoommateRequestDTO(rrservice.getRequest_id(), "Request sent
    // successfully"));
    // }

}
