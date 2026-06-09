package com.studentnet.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @Column(nullable = false, length = 2000)
    private String content;

    private LocalDateTime createdAt = LocalDateTime.now();

    public Comment() {}

    // Getters
    public Long getId() { return id; }
    public Post getPost() { return post; }
    public User getAuthor() { return author; }
    public String getContent() { return content; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setPost(Post post) { this.post = post; }
    public void setAuthor(User author) { this.author = author; }
    public void setContent(String content) { this.content = content; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    // Builder
    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private final Comment comment = new Comment();
        public Builder post(Post v) { comment.post = v; return this; }
        public Builder author(User v) { comment.author = v; return this; }
        public Builder content(String v) { comment.content = v; return this; }
        public Comment build() { return comment; }
    }
}
