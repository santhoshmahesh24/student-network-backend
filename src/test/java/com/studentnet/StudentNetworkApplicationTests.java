package com.studentnet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.studentnet.dto.AuthDto;
import com.studentnet.dto.PostDto;
import com.studentnet.model.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class StudentNetworkApplicationTests {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @Test
    void contextLoads() {
        // App starts successfully
    }

    @Test
    void testRegisterAndLogin() throws Exception {
        // Register
        AuthDto.RegisterRequest reg = new AuthDto.RegisterRequest();
        reg.setFullName("Test User");
        reg.setEmail("test@test.com");
        reg.setPassword("password123");
        reg.setUniversity("Test University");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reg)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.email").value("test@test.com"));

        // Login
        AuthDto.LoginRequest login = new AuthDto.LoginRequest();
        login.setEmail("test@test.com");
        login.setPassword("password123");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    void testCreatePost() throws Exception {
        // Register and get token
        AuthDto.RegisterRequest reg = new AuthDto.RegisterRequest();
        reg.setFullName("Post Tester");
        reg.setEmail("poster@test.com");
        reg.setPassword("password123");

        MvcResult result = mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reg)))
                .andReturn();

        String token = objectMapper.readTree(result.getResponse().getContentAsString())
                .get("token").asText();

        // Create post
        PostDto.CreatePostRequest postReq = new PostDto.CreatePostRequest();
        postReq.setContent("Test post content about Java Spring Boot!");
        postReq.setType(Post.PostType.GENERAL);
        postReq.setTags("Java, Spring Boot");

        mockMvc.perform(post("/api/posts")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postReq)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Test post content about Java Spring Boot!"))
                .andExpect(jsonPath("$.author.fullName").value("Post Tester"));
    }

    @Test
    void testUnauthenticatedAccessBlocked() throws Exception {
        mockMvc.perform(get("/api/posts/feed"))
                .andExpect(status().isForbidden());
    }
}
