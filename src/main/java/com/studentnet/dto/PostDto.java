package com.studentnet.dto;

import com.studentnet.model.Comment;
import com.studentnet.model.Post;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

public class PostDto {

    public static class CreatePostRequest {
        @NotBlank(message = "Content cannot be empty")
        private String content;
        private Post.PostType type = Post.PostType.GENERAL;
        private String resourceUrl;
        private String resourceTitle;
        private String tags;

        public String getContent() { return content; }
        public Post.PostType getType() { return type; }
        public String getResourceUrl() { return resourceUrl; }
        public String getResourceTitle() { return resourceTitle; }
        public String getTags() { return tags; }
        public void setContent(String v) { content = v; }
        public void setType(Post.PostType v) { type = v; }
        public void setResourceUrl(String v) { resourceUrl = v; }
        public void setResourceTitle(String v) { resourceTitle = v; }
        public void setTags(String v) { tags = v; }
    }

    public static class PostResponse {
        private Long id;
        private UserDto.UserSummary author;
        private String content;
        private Post.PostType type;
        private String resourceUrl;
        private String resourceTitle;
        private String tags;
        private int likeCount;
        private int commentCount;
        private LocalDateTime createdAt;

        public static PostResponse from(Post p) {
            PostResponse r = new PostResponse();
            r.id = p.getId();
            r.author = UserDto.UserSummary.from(p.getAuthor());
            r.content = p.getContent();
            r.type = p.getType();
            r.resourceUrl = p.getResourceUrl();
            r.resourceTitle = p.getResourceTitle();
            r.tags = p.getTags();
            r.likeCount = p.getLikeCount();
            r.commentCount = p.getComments().size();
            r.createdAt = p.getCreatedAt();
            return r;
        }

        public Long getId() { return id; }
        public UserDto.UserSummary getAuthor() { return author; }
        public String getContent() { return content; }
        public Post.PostType getType() { return type; }
        public String getResourceUrl() { return resourceUrl; }
        public String getResourceTitle() { return resourceTitle; }
        public String getTags() { return tags; }
        public int getLikeCount() { return likeCount; }
        public int getCommentCount() { return commentCount; }
        public LocalDateTime getCreatedAt() { return createdAt; }
    }

    public static class CommentRequest {
        @NotBlank(message = "Comment cannot be empty")
        private String content;

        public String getContent() { return content; }
        public void setContent(String v) { content = v; }
    }

    public static class CommentResponse {
        private Long id;
        private UserDto.UserSummary author;
        private String content;
        private LocalDateTime createdAt;

        public static CommentResponse from(Comment c) {
            CommentResponse r = new CommentResponse();
            r.id = c.getId();
            r.author = UserDto.UserSummary.from(c.getAuthor());
            r.content = c.getContent();
            r.createdAt = c.getCreatedAt();
            return r;
        }

        public Long getId() { return id; }
        public UserDto.UserSummary getAuthor() { return author; }
        public String getContent() { return content; }
        public LocalDateTime getCreatedAt() { return createdAt; }
    }
}
