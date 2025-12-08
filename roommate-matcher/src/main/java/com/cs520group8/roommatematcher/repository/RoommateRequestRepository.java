package com.cs520group8.roommatematcher.repository;

import com.cs520group8.roommatematcher.entity.RoommateRequest;
import com.cs520group8.roommatematcher.entity.RoommateRequest.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoommateRequestRepository extends JpaRepository<RoommateRequest, Long> {
    
    // Find all requests sent by a user
    List<RoommateRequest> findBySenderId(Long senderId);
    
    // Find all requests received by a user
    List<RoommateRequest> findByReceiverId(Long receiverId);
    
    // Find all pending requests received by a user
    List<RoommateRequest> findByReceiverIdAndStatus(Long receiverId, RequestStatus status);
    
    // Find all pending requests sent by a user
    List<RoommateRequest> findBySenderIdAndStatus(Long senderId, RequestStatus status);
    
    // Check if a request already exists between two users
    @Query("SELECT r FROM RoommateRequest r WHERE " +
           "(r.sender.id = :userId1 AND r.receiver.id = :userId2) OR " +
           "(r.sender.id = :userId2 AND r.receiver.id = :userId1)")
    Optional<RoommateRequest> findExistingRequest(@Param("userId1") Long userId1, @Param("userId2") Long userId2);
    
    // Check if there's already a pending request between two users
    @Query("SELECT r FROM RoommateRequest r WHERE " +
           "((r.sender.id = :userId1 AND r.receiver.id = :userId2) OR " +
           "(r.sender.id = :userId2 AND r.receiver.id = :userId1)) " +
           "AND r.status = 'PENDING'")
    Optional<RoommateRequest> findPendingRequestBetweenUsers(@Param("userId1") Long userId1, @Param("userId2") Long userId2);
}

