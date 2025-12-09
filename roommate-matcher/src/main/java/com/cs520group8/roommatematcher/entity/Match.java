package com.cs520group8.roommatematcher.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

// Create table for requests. 
@Data
@Entity
@Table(name = "matches")

// Signifies that two users are matched.
public class Match {

    // Auto generated primary id for the table.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long request_id;

    // Sender id foreign key
    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    // Receiver id foreign key
    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    // Date and Time
    @Column(name = "init_time", nullable = false)
    private LocalDateTime init_time = LocalDateTime.now();
}