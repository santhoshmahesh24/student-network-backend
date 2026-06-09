package com.studentnet.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @Column(nullable = false, length = 5000)
    private String content;

    @Enumerated(EnumType.STRING)
    private PostType type = PostType.GENERAL;

    private String resourceUrl;
    private String resourceTitle;

    @Column(length = 500)
    private String tags;

    private int likeCount = 0;

    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public enum PostType { GENERAL, RESOURCE, ACHIEVEMENT }

    public Post() {}

    // Getters
    public Long getId() { return id; }
    public User getAuthor() { return author; }
    public String getContent() { return content; }
    public PostType getType() { return type; }
    public String getResourceUrl() { return resourceUrl; }
    public String getResourceTitle() { return resourceTitle; }
    public String getTags() { return tags; }
    public int getLikeCount() { return likeCount; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public List<Comment> getComments() { return comments; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setAuthor(User author) { this.author = author; }
    public void setContent(String content) { this.content = content; }
    public void setType(PostType type) { this.type = type; }
    public void setResourceUrl(String resourceUrl) { this.resourceUrl = resourceUrl; }
    public void setResourceTitle(String resourceTitle) { this.resourceTitle = resourceTitle; }
    public void setTags(String tags) { this.tags = tags; }
    public void setLikeCount(int likeCount) { this.likeCount = likeCount; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setComments(List<Comment> comments) { this.comments = comments; }

    // Builder
    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private final Post post = new Post();
        public Builder author(User v) { post.author = v; return this; }
        public Builder content(String v) { post.content = v; return this; }
        public Builder type(PostType v) { post.type = v; return this; }
        public Builder resourceUrl(String v) { post.resourceUrl = v; return this; }
        public Builder resourceTitle(String v) { post.resourceTitle = v; return this; }
        public Builder tags(String v) { post.tags = v; return this; }
        public Post build() { return post; }
    }
}
