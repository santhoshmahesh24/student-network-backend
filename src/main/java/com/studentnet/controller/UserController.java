package com.studentnet.controller;

import com.studentnet.dto.UserDto;
import com.studentnet.model.ConnectionRequest;
import com.studentnet.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired private UserService userService;

    /** Get your own profile */
    @GetMapping("/me")
    public ResponseEntity<UserDto.ProfileResponse> getMyProfile(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
                userService.getProfile(
                        userService.getCurrentUser(userDetails.getUsername()).getId()));
    }

    /** Get any user's profile */
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto.ProfileResponse> getProfile(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getProfile(userId));
    }

    /** Update your own profile */
    @PutMapping("/me")
    public ResponseEntity<UserDto.ProfileResponse> updateProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody UserDto.UpdateProfileRequest request) {
        return ResponseEntity.ok(userService.updateProfile(userDetails.getUsername(), request));
    }

    /** Search users by name, university, skills, department */
    @GetMapping("/search")
    public ResponseEntity<List<UserDto.UserSummary>> searchUsers(
            @RequestParam String q) {
        return ResponseEntity.ok(userService.searchUsers(q));
    }

    /** Get connections of a user */
    @GetMapping("/{userId}/connections")
    public ResponseEntity<List<UserDto.UserSummary>> getConnections(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getConnections(userId));
    }

    /** Send a connection request */
    @PostMapping("/{targetUserId}/connect")
    public ResponseEntity<String> sendConnectionRequest(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long targetUserId) {
        try {
            return ResponseEntity.ok(
                    userService.sendConnectionRequest(userDetails.getUsername(), targetUserId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /** Get your pending connection requests */
    @GetMapping("/me/connection-requests")
    public ResponseEntity<List<ConnectionRequest>> getPendingRequests(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(userService.getPendingRequests(userDetails.getUsername()));
    }

    /** Accept or reject a connection request */
    @PutMapping("/me/connection-requests/{requestId}")
    public ResponseEntity<String> respondToRequest(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long requestId,
            @RequestParam boolean accept) {
        try {
            return ResponseEntity.ok(
                    userService.respondToConnectionRequest(
                            userDetails.getUsername(), requestId, accept));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /** Remove a connection */
    @DeleteMapping("/me/connections/{targetUserId}")
    public ResponseEntity<String> removeConnection(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long targetUserId) {
        return ResponseEntity.ok(
                userService.removeConnection(userDetails.getUsername(), targetUserId));
    }
}
