package com.studentnet.dto;

import com.studentnet.model.User;
import java.time.LocalDateTime;

public class UserDto {

    public static class ProfileResponse {
        private Long id;
        private String fullName;
        private String email;
        private String university;
        private String department;
        private String yearOfStudy;
        private String bio;
        private String skills;
        private String profilePictureUrl;
        private String linkedinUrl;
        private String githubUrl;
        private String role;
        private LocalDateTime createdAt;
        private int connectionCount;

        public static ProfileResponse from(User u) {
            ProfileResponse r = new ProfileResponse();
            r.id = u.getId();
            r.fullName = u.getFullName();
            r.email = u.getEmail();
            r.university = u.getUniversity();
            r.department = u.getDepartment();
            r.yearOfStudy = u.getYearOfStudy();
            r.bio = u.getBio();
            r.skills = u.getSkills();
            r.profilePictureUrl = u.getProfilePictureUrl();
            r.linkedinUrl = u.getLinkedinUrl();
            r.githubUrl = u.getGithubUrl();
            r.role = u.getRole().name();
            r.createdAt = u.getCreatedAt();
            r.connectionCount = u.getConnections().size();
            return r;
        }

        public Long getId() { return id; }
        public String getFullName() { return fullName; }
        public String getEmail() { return email; }
        public String getUniversity() { return university; }
        public String getDepartment() { return department; }
        public String getYearOfStudy() { return yearOfStudy; }
        public String getBio() { return bio; }
        public String getSkills() { return skills; }
        public String getProfilePictureUrl() { return profilePictureUrl; }
        public String getLinkedinUrl() { return linkedinUrl; }
        public String getGithubUrl() { return githubUrl; }
        public String getRole() { return role; }
        public LocalDateTime getCreatedAt() { return createdAt; }
        public int getConnectionCount() { return connectionCount; }
    }

    public static class UpdateProfileRequest {
        private String fullName;
        private String university;
        private String department;
        private String yearOfStudy;
        private String bio;
        private String skills;
        private String profilePictureUrl;
        private String linkedinUrl;
        private String githubUrl;

        public String getFullName() { return fullName; }
        public String getUniversity() { return university; }
        public String getDepartment() { return department; }
        public String getYearOfStudy() { return yearOfStudy; }
        public String getBio() { return bio; }
        public String getSkills() { return skills; }
        public String getProfilePictureUrl() { return profilePictureUrl; }
        public String getLinkedinUrl() { return linkedinUrl; }
        public String getGithubUrl() { return githubUrl; }
        public void setFullName(String v) { fullName = v; }
        public void setUniversity(String v) { university = v; }
        public void setDepartment(String v) { department = v; }
        public void setYearOfStudy(String v) { yearOfStudy = v; }
        public void setBio(String v) { bio = v; }
        public void setSkills(String v) { skills = v; }
        public void setProfilePictureUrl(String v) { profilePictureUrl = v; }
        public void setLinkedinUrl(String v) { linkedinUrl = v; }
        public void setGithubUrl(String v) { githubUrl = v; }
    }

    public static class UserSummary {
        private Long id;
        private String fullName;
        private String university;
        private String department;
        private String yearOfStudy;
        private String skills;
        private String profilePictureUrl;

        public static UserSummary from(User u) {
            UserSummary s = new UserSummary();
            s.id = u.getId();
            s.fullName = u.getFullName();
            s.university = u.getUniversity();
            s.department = u.getDepartment();
            s.yearOfStudy = u.getYearOfStudy();
            s.skills = u.getSkills();
            s.profilePictureUrl = u.getProfilePictureUrl();
            return s;
        }

        public Long getId() { return id; }
        public String getFullName() { return fullName; }
        public String getUniversity() { return university; }
        public String getDepartment() { return department; }
        public String getYearOfStudy() { return yearOfStudy; }
        public String getSkills() { return skills; }
        public String getProfilePictureUrl() { return profilePictureUrl; }
    }
}
