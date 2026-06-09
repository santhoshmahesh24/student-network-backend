package com.studentnet.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = {
    @UniqueConstraint(columnNames = "email")
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String fullName;

    @Email @NotBlank
    private String email;

    @NotBlank
    private String password;

    private String university;
    private String department;
    private String yearOfStudy;
    private String bio;

    @Column(length = 1000)
    private String skills;

    private String profilePictureUrl;
    private String linkedinUrl;
    private String githubUrl;

    @Enumerated(EnumType.STRING)
    private Role role = Role.STUDENT;

    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "connections",
               joinColumns = @JoinColumn(name = "user_id"),
               inverseJoinColumns = @JoinColumn(name = "connected_user_id"))
    private Set<User> connections = new HashSet<>();

    public enum Role { STUDENT, ADMIN }

    // Constructors
    public User() {}

    // Getters
    public Long getId() { return id; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getUniversity() { return university; }
    public String getDepartment() { return department; }
    public String getYearOfStudy() { return yearOfStudy; }
    public String getBio() { return bio; }
    public String getSkills() { return skills; }
    public String getProfilePictureUrl() { return profilePictureUrl; }
    public String getLinkedinUrl() { return linkedinUrl; }
    public String getGithubUrl() { return githubUrl; }
    public Role getRole() { return role; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public Set<User> getConnections() { return connections; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setUniversity(String university) { this.university = university; }
    public void setDepartment(String department) { this.department = department; }
    public void setYearOfStudy(String yearOfStudy) { this.yearOfStudy = yearOfStudy; }
    public void setBio(String bio) { this.bio = bio; }
    public void setSkills(String skills) { this.skills = skills; }
    public void setProfilePictureUrl(String profilePictureUrl) { this.profilePictureUrl = profilePictureUrl; }
    public void setLinkedinUrl(String linkedinUrl) { this.linkedinUrl = linkedinUrl; }
    public void setGithubUrl(String githubUrl) { this.githubUrl = githubUrl; }
    public void setRole(Role role) { this.role = role; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setConnections(Set<User> connections) { this.connections = connections; }

    // Builder
    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private final User user = new User();
        public Builder fullName(String v) { user.fullName = v; return this; }
        public Builder email(String v) { user.email = v; return this; }
        public Builder password(String v) { user.password = v; return this; }
        public Builder university(String v) { user.university = v; return this; }
        public Builder department(String v) { user.department = v; return this; }
        public Builder yearOfStudy(String v) { user.yearOfStudy = v; return this; }
        public Builder bio(String v) { user.bio = v; return this; }
        public Builder skills(String v) { user.skills = v; return this; }
        public Builder profilePictureUrl(String v) { user.profilePictureUrl = v; return this; }
        public Builder linkedinUrl(String v) { user.linkedinUrl = v; return this; }
        public Builder githubUrl(String v) { user.githubUrl = v; return this; }
        public Builder role(Role v) { user.role = v; return this; }
        public User build() { return user; }
    }
}
