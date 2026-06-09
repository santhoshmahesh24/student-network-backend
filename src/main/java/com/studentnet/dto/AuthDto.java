package com.studentnet.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AuthDto {

    public static class RegisterRequest {
        @NotBlank(message = "Full name is required")
        private String fullName;
        @Email(message = "Valid email required")
        @NotBlank(message = "Email is required")
        private String email;
        @NotBlank(message = "Password is required")
        @Size(min = 8, message = "Password must be at least 8 characters")
        private String password;
        private String university;
        private String department;
        private String yearOfStudy;

        public String getFullName() { return fullName; }
        public String getEmail() { return email; }
        public String getPassword() { return password; }
        public String getUniversity() { return university; }
        public String getDepartment() { return department; }
        public String getYearOfStudy() { return yearOfStudy; }
        public void setFullName(String v) { fullName = v; }
        public void setEmail(String v) { email = v; }
        public void setPassword(String v) { password = v; }
        public void setUniversity(String v) { university = v; }
        public void setDepartment(String v) { department = v; }
        public void setYearOfStudy(String v) { yearOfStudy = v; }
    }

    public static class LoginRequest {
        @Email @NotBlank
        private String email;
        @NotBlank
        private String password;

        public String getEmail() { return email; }
        public String getPassword() { return password; }
        public void setEmail(String v) { email = v; }
        public void setPassword(String v) { password = v; }
    }

    public static class AuthResponse {
        private String token;
        private String type = "Bearer";
        private Long userId;
        private String fullName;
        private String email;
        private String role;

        public AuthResponse(String token, Long userId, String fullName, String email, String role) {
            this.token = token;
            this.userId = userId;
            this.fullName = fullName;
            this.email = email;
            this.role = role;
        }

        public String getToken() { return token; }
        public String getType() { return type; }
        public Long getUserId() { return userId; }
        public String getFullName() { return fullName; }
        public String getEmail() { return email; }
        public String getRole() { return role; }
    }
}
