package com.cs520group8.roommatematcher.service;

import com.cs520group8.roommatematcher.dto.RoommateRequestDTO;
import com.cs520group8.roommatematcher.dto.RoommateRequestResponse;
import com.cs520group8.roommatematcher.entity.RoommateRequest;
import com.cs520group8.roommatematcher.entity.RoommateRequest.RequestStatus;
import com.cs520group8.roommatematcher.entity.User;
import com.cs520group8.roommatematcher.repository.RoommateRequestRepository;
import com.cs520group8.roommatematcher.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoommateRequestService {
    
    private final RoommateRequestRepository requestRepository;
    private final UserRepository userRepository;
    
    public RoommateRequestService(RoommateRequestRepository requestRepository, UserRepository userRepository) {
        this.requestRepository = requestRepository;
        this.userRepository = userRepository;
    }
    
    // Send a roommate request
    public RoommateRequestResponse sendRequest(Long senderId, RoommateRequestDTO dto) {
        // Check if users exist
        User sender = userRepository.findById(senderId)
            .orElseThrow(() -> new RuntimeException("Sender not found"));
        User receiver = userRepository.findById(dto.getReceiverId())
            .orElseThrow(() -> new RuntimeException("Receiver not found"));
        
        // Check if request already exists
        if (requestRepository.findPendingRequestBetweenUsers(senderId, dto.getReceiverId()).isPresent()) {
            throw new RuntimeException("A pending request already exists between these users");
        }
        
        // Can't send request to yourself
        if (senderId.equals(dto.getReceiverId())) {
            throw new RuntimeException("Cannot send request to yourself");
        }
        
        RoommateRequest request = new RoommateRequest();
        request.setSender(sender);
        request.setReceiver(receiver);
        request.setMessage(dto.getMessage());
        request.setStatus(RequestStatus.PENDING);
        
        requestRepository.save(request);
        return RoommateRequestResponse.fromEntity(request);
    }
    
    // Get all pending requests received by user
    public List<RoommateRequestResponse> getReceivedRequests(Long userId) {
        return requestRepository.findByReceiverIdAndStatus(userId, RequestStatus.PENDING)
            .stream()
            .map(RoommateRequestResponse::fromEntity)
            .collect(Collectors.toList());
    }
    
    // Get all requests sent by user
    public List<RoommateRequestResponse> getSentRequests(Long userId) {
        return requestRepository.findBySenderId(userId)
            .stream()
            .map(RoommateRequestResponse::fromEntity)
            .collect(Collectors.toList());
    }
    
    // Accept a roommate request
    public RoommateRequestResponse acceptRequest(Long requestId, Long userId) {
        RoommateRequest request = requestRepository.findById(requestId)
            .orElseThrow(() -> new RuntimeException("Request not found"));
        
        // Only receiver can accept
        if (!request.getReceiver().getId().equals(userId)) {
            throw new RuntimeException("Only the receiver can accept this request");
        }
        
        if (request.getStatus() != RequestStatus.PENDING) {
            throw new RuntimeException("Request is no longer pending");
        }
        
        request.setStatus(RequestStatus.ACCEPTED);
        requestRepository.save(request);
        
        return RoommateRequestResponse.fromEntity(request);
    }
    
    // Reject a roommate request
    public RoommateRequestResponse rejectRequest(Long requestId, Long userId) {
        RoommateRequest request = requestRepository.findById(requestId)
            .orElseThrow(() -> new RuntimeException("Request not found"));
        
        // Only receiver can reject
        if (!request.getReceiver().getId().equals(userId)) {
            throw new RuntimeException("Only the receiver can reject this request");
        }
        
        if (request.getStatus() != RequestStatus.PENDING) {
            throw new RuntimeException("Request is no longer pending");
        }
        
        request.setStatus(RequestStatus.REJECTED);
        requestRepository.save(request);
        
        return RoommateRequestResponse.fromEntity(request);
    }
    
    // Cancel a sent request
    public void cancelRequest(Long requestId, Long userId) {
        RoommateRequest request = requestRepository.findById(requestId)
            .orElseThrow(() -> new RuntimeException("Request not found"));
        
        // Only sender can cancel
        if (!request.getSender().getId().equals(userId)) {
            throw new RuntimeException("Only the sender can cancel this request");
        }
        
        if (request.getStatus() != RequestStatus.PENDING) {
            throw new RuntimeException("Can only cancel pending requests");
        }
        
        requestRepository.delete(request);
    }
}

