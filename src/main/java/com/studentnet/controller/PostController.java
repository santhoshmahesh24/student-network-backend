package com.studentnet.controller;

import com.studentnet.dto.PostDto;
import com.studentnet.model.Post;
import com.studentnet.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired private PostService postService;

    /** Create a new post */
    @PostMapping
    public ResponseEntity<PostDto.PostResponse> createPost(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody PostDto.CreatePostRequest request) {
        return ResponseEntity.ok(postService.createPost(userDetails.getUsername(), request));
    }

    /** Your personalized feed (connections + own posts) */
    @GetMapping("/feed")
    public ResponseEntity<Page<PostDto.PostResponse>> getFeed(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(postService.getFeed(userDetails.getUsername(), page, size));
    }

    /** All posts - discovery */
    @GetMapping
    public ResponseEntity<Page<PostDto.PostResponse>> getAllPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(postService.getAllPosts(page, size));
    }

    /** Filter by type: GENERAL, RESOURCE, ACHIEVEMENT */
    @GetMapping("/type/{type}")
    public ResponseEntity<Page<PostDto.PostResponse>> getByType(
            @PathVariable Post.PostType type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(postService.getPostsByType(type, page, size));
    }

    /** Posts by a specific user */
    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<PostDto.PostResponse>> getUserPosts(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(postService.getUserPosts(userId, page, size));
    }

    /** Search posts */
    @GetMapping("/search")
    public ResponseEntity<Page<PostDto.PostResponse>> searchPosts(
            @RequestParam String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(postService.searchPosts(q, page, size));
    }

    /** Like a post */
    @PostMapping("/{postId}/like")
    public ResponseEntity<PostDto.PostResponse> likePost(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.likePost(postId));
    }

    /** Delete your own post */
    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long postId) {
        try {
            postService.deletePost(userDetails.getUsername(), postId);
            return ResponseEntity.ok("Post deleted");
        } catch (RuntimeException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }

    /** Add comment */
    @PostMapping("/{postId}/comments")
    public ResponseEntity<PostDto.CommentResponse> addComment(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long postId,
            @Valid @RequestBody PostDto.CommentRequest request) {
        return ResponseEntity.ok(postService.addComment(userDetails.getUsername(), postId, request));
    }

    /** Get comments on a post */
    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<PostDto.CommentResponse>> getComments(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.getComments(postId));
    }
}
