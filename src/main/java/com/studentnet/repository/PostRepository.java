package com.studentnet.repository;

import com.studentnet.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findByAuthorIdOrderByCreatedAtDesc(Long authorId, Pageable pageable);

    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Page<Post> findByTypeOrderByCreatedAtDesc(Post.PostType type, Pageable pageable);

    @Query("SELECT p FROM Post p WHERE " +
           "LOWER(p.content) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(p.tags) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(p.resourceTitle) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<Post> searchPosts(@Param("query") String query, Pageable pageable);

    // Feed: posts by connected users + own posts
    @Query("SELECT p FROM Post p WHERE p.author.id IN :userIds ORDER BY p.createdAt DESC")
    Page<Post> findFeedPosts(@Param("userIds") List<Long> userIds, Pageable pageable);
}
