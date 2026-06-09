package com.studentnet.repository;

import com.studentnet.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    // Search by name, university, or skills (case-insensitive)
    @Query("SELECT u FROM User u WHERE " +
           "LOWER(u.fullName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(u.university) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(u.skills) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(u.department) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<User> searchUsers(@Param("query") String query);

    List<User> findByUniversity(String university);

    List<User> findByDepartment(String department);

    @Query("SELECT u FROM User u WHERE LOWER(u.skills) LIKE LOWER(CONCAT('%', :skill, '%'))")
    List<User> findBySkill(@Param("skill") String skill);
}
