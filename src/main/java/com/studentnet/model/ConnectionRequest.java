package com.studentnet.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "connection_requests",
       uniqueConstraints = @UniqueConstraint(columnNames = {"sender_id", "receiver_id"}))
public class ConnectionRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime respondedAt;

    public enum Status { PENDING, ACCEPTED, REJECTED }

    public ConnectionRequest() {}

    // Getters
    public Long getId() { return id; }
    public User getSender() { return sender; }
    public User getReceiver() { return receiver; }
    public Status getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getRespondedAt() { return respondedAt; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setSender(User sender) { this.sender = sender; }
    public void setReceiver(User receiver) { this.receiver = receiver; }
    public void setStatus(Status status) { this.status = status; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setRespondedAt(LocalDateTime respondedAt) { this.respondedAt = respondedAt; }

    // Builder
    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private final ConnectionRequest cr = new ConnectionRequest();
        public Builder sender(User v) { cr.sender = v; return this; }
        public Builder receiver(User v) { cr.receiver = v; return this; }
        public Builder status(Status v) { cr.status = v; return this; }
        public ConnectionRequest build() { return cr; }
    }
}
