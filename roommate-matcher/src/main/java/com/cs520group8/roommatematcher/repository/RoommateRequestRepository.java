package com.cs520group8.roommatematcher.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cs520group8.roommatematcher.entity.Request;

public interface RoommateRequestRepository extends JpaRepository<Request, Long> {
    boolean existsBySenderIdAndReceiverId(Long senderId, Long receiverId);
}
