package com.studentnet.service;

import com.studentnet.dto.PostDto;
import com.studentnet.model.Comment;
import com.studentnet.model.Post;
import com.studentnet.model.User;
import com.studentnet.repository.CommentRepository;
import com.studentnet.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PostService {

    @Autowired private PostRepository postRepository;
    @Autowired private CommentRepository commentRepository;
    @Autowired private UserService userService;

    @Transactional
    public PostDto.PostResponse createPost(String email, PostDto.CreatePostRequest request) {
        User author = userService.getCurrentUser(email);
        Post post = Post.builder()
                .author(author)
                .content(request.getContent())
                .type(request.getType())
                .resourceUrl(request.getResourceUrl())
                .resourceTitle(request.getResourceTitle())
                .tags(request.getTags())
                .build();
        return PostDto.PostResponse.from(postRepository.save(post));
    }

    public Page<PostDto.PostResponse> getFeed(String email, int page, int size) {
        User user = userService.getCurrentUser(email);

        // Include own posts + connection posts
        List<Long> userIds = Stream.concat(
                Stream.of(user.getId()),
                user.getConnections().stream().map(User::getId)
        ).collect(Collectors.toList());

        Pageable pageable = PageRequest.of(page, size);
        return postRepository.findFeedPosts(userIds, pageable)
                .map(PostDto.PostResponse::from);
    }

    public Page<PostDto.PostResponse> getAllPosts(int page, int size) {
        return postRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(page, size))
                .map(PostDto.PostResponse::from);
    }

    public Page<PostDto.PostResponse> getPostsByType(Post.PostType type, int page, int size) {
        return postRepository.findByTypeOrderByCreatedAtDesc(type, PageRequest.of(page, size))
                .map(PostDto.PostResponse::from);
    }

    public Page<PostDto.PostResponse> getUserPosts(Long userId, int page, int size) {
        return postRepository.findByAuthorIdOrderByCreatedAtDesc(userId, PageRequest.of(page, size))
                .map(PostDto.PostResponse::from);
    }

    public Page<PostDto.PostResponse> searchPosts(String query, int page, int size) {
        return postRepository.searchPosts(query, PageRequest.of(page, size))
                .map(PostDto.PostResponse::from);
    }

    @Transactional
    public PostDto.PostResponse likePost(Long postId) {
        Post post = getPostById(postId);
        post.setLikeCount(post.getLikeCount() + 1);
        return PostDto.PostResponse.from(postRepository.save(post));
    }

    @Transactional
    public void deletePost(String email, Long postId) {
        Post post = getPostById(postId);
        User user = userService.getCurrentUser(email);
        if (!post.getAuthor().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized: you can only delete your own posts");
        }
        postRepository.delete(post);
    }

    @Transactional
    public PostDto.CommentResponse addComment(String email, Long postId,
                                               PostDto.CommentRequest request) {
        User author = userService.getCurrentUser(email);
        Post post = getPostById(postId);

        Comment comment = Comment.builder()
                .post(post)
                .author(author)
                .content(request.getContent())
                .build();
        return PostDto.CommentResponse.from(commentRepository.save(comment));
    }

    public List<PostDto.CommentResponse> getComments(Long postId) {
        return commentRepository.findByPostIdOrderByCreatedAtAsc(postId).stream()
                .map(PostDto.CommentResponse::from)
                .collect(Collectors.toList());
    }

    private Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found: " + id));
    }
}
