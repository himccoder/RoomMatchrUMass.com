package com.cs520group8.roommatematcher.service;

import com.cs520group8.roommatematcher.dto.ConversationResponse;
import com.cs520group8.roommatematcher.dto.MessageResponse;
import com.cs520group8.roommatematcher.dto.SendMessageRequest;
import com.cs520group8.roommatematcher.entity.Message;
import com.cs520group8.roommatematcher.entity.User;
import com.cs520group8.roommatematcher.repository.MessageRepository;
import com.cs520group8.roommatematcher.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {
    
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    
    public MessageService(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }
    
    // Send a message
    public MessageResponse sendMessage(Long senderId, SendMessageRequest request) {
        User sender = userRepository.findById(senderId)
            .orElseThrow(() -> new RuntimeException("Sender not found"));
        User receiver = userRepository.findById(request.getReceiverId())
            .orElseThrow(() -> new RuntimeException("Receiver not found"));
        
        if (senderId.equals(request.getReceiverId())) {
            throw new RuntimeException("Cannot send message to yourself");
        }
        
        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(request.getContent());
        message.setIsRead(false);
        
        messageRepository.save(message);
        return MessageResponse.fromEntity(message);
    }
    
    // Get conversation between two users
    public List<MessageResponse> getConversation(Long userId, Long otherUserId) {
        return messageRepository.findConversation(userId, otherUserId)
            .stream()
            .map(MessageResponse::fromEntity)
            .collect(Collectors.toList());
    }
    
    // Get all conversations for a user
    public List<ConversationResponse> getConversations(Long userId) {
        List<Long> partnerIds = messageRepository.findConversationPartnerIds(userId);
        List<ConversationResponse> conversations = new ArrayList<>();
        
        for (Long partnerId : partnerIds) {
            User partner = userRepository.findById(partnerId).orElse(null);
            if (partner == null) continue;
            
            Message lastMessage = messageRepository.findLastMessage(userId, partnerId);
            Long unreadCount = messageRepository.countByReceiverIdAndIsReadFalse(userId);
            
            ConversationResponse conv = new ConversationResponse();
            conv.setPartnerId(partnerId);
            conv.setPartnerName(partner.getName());
            conv.setPartnerMajor(partner.getMajor());
            conv.setLastMessage(lastMessage != null ? lastMessage.getContent() : "");
            conv.setLastMessageTime(lastMessage != null ? lastMessage.getCreatedAt() : null);
            conv.setUnreadCount(unreadCount);
            
            conversations.add(conv);
        }
        
        // Sort by last message time (most recent first)
        conversations.sort((a, b) -> {
            if (a.getLastMessageTime() == null) return 1;
            if (b.getLastMessageTime() == null) return -1;
            return b.getLastMessageTime().compareTo(a.getLastMessageTime());
        });
        
        return conversations;
    }
    
    // Mark messages as read
    @Transactional
    public void markMessagesAsRead(Long userId, Long senderId) {
        List<Message> messages = messageRepository.findConversation(userId, senderId);
        for (Message message : messages) {
            if (message.getReceiver().getId().equals(userId) && !message.getIsRead()) {
                message.setIsRead(true);
                messageRepository.save(message);
            }
        }
    }
    
    // Get unread message count
    public Long getUnreadCount(Long userId) {
        return messageRepository.countByReceiverIdAndIsReadFalse(userId);
    }
}

