package com.cs520group8.roommatematcher.repository;

import com.cs520group8.roommatematcher.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    
    // Get conversation between two users (ordered by time)
    @Query("SELECT m FROM Message m WHERE " +
           "(m.sender.id = :userId1 AND m.receiver.id = :userId2) OR " +
           "(m.sender.id = :userId2 AND m.receiver.id = :userId1) " +
           "ORDER BY m.createdAt ASC")
    List<Message> findConversation(@Param("userId1") Long userId1, @Param("userId2") Long userId2);
    
    // Get all messages sent by a user
    List<Message> findBySenderId(Long senderId);
    
    // Get all messages received by a user
    List<Message> findByReceiverId(Long receiverId);
    
    // Get unread messages for a user
    List<Message> findByReceiverIdAndIsReadFalse(Long receiverId);
    
    // Get count of unread messages for a user
    Long countByReceiverIdAndIsReadFalse(Long receiverId);
    
    // Get all unique conversation partners for a user
    @Query("SELECT DISTINCT CASE WHEN m.sender.id = :userId THEN m.receiver.id ELSE m.sender.id END " +
           "FROM Message m WHERE m.sender.id = :userId OR m.receiver.id = :userId")
    List<Long> findConversationPartnerIds(@Param("userId") Long userId);
    
    // Get the last message between two users
    @Query("SELECT m FROM Message m WHERE " +
           "(m.sender.id = :userId1 AND m.receiver.id = :userId2) OR " +
           "(m.sender.id = :userId2 AND m.receiver.id = :userId1) " +
           "ORDER BY m.createdAt DESC LIMIT 1")
    Message findLastMessage(@Param("userId1") Long userId1, @Param("userId2") Long userId2);
}

