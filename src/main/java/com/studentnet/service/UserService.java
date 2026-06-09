package com.studentnet.service;

import com.studentnet.dto.UserDto;
import com.studentnet.model.ConnectionRequest;
import com.studentnet.model.User;
import com.studentnet.repository.ConnectionRequestRepository;
import com.studentnet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired private UserRepository userRepository;
    @Autowired private ConnectionRequestRepository connectionRequestRepository;

    public User getCurrentUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
    }

    public UserDto.ProfileResponse getProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));
        return UserDto.ProfileResponse.from(user);
    }

    @Transactional
    public UserDto.ProfileResponse updateProfile(String email,
                                                  UserDto.UpdateProfileRequest request) {
        User user = getCurrentUser(email);
        if (request.getFullName() != null)       user.setFullName(request.getFullName());
        if (request.getUniversity() != null)     user.setUniversity(request.getUniversity());
        if (request.getDepartment() != null)     user.setDepartment(request.getDepartment());
        if (request.getYearOfStudy() != null)    user.setYearOfStudy(request.getYearOfStudy());
        if (request.getBio() != null)            user.setBio(request.getBio());
        if (request.getSkills() != null)         user.setSkills(request.getSkills());
        if (request.getProfilePictureUrl() != null) user.setProfilePictureUrl(request.getProfilePictureUrl());
        if (request.getLinkedinUrl() != null)    user.setLinkedinUrl(request.getLinkedinUrl());
        if (request.getGithubUrl() != null)      user.setGithubUrl(request.getGithubUrl());

        return UserDto.ProfileResponse.from(userRepository.save(user));
    }

    public List<UserDto.UserSummary> searchUsers(String query) {
        return userRepository.searchUsers(query).stream()
                .map(UserDto.UserSummary::from)
                .collect(Collectors.toList());
    }

    public List<UserDto.UserSummary> getConnections(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getConnections().stream()
                .map(UserDto.UserSummary::from)
                .collect(Collectors.toList());
    }

    // --- Connection Management ---

    @Transactional
    public String sendConnectionRequest(String senderEmail, Long receiverId) {
        User sender = getCurrentUser(senderEmail);
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new RuntimeException("Target user not found"));

        if (sender.getId().equals(receiverId)) {
            throw new IllegalArgumentException("You cannot connect with yourself.");
        }
        if (sender.getConnections().contains(receiver)) {
            throw new IllegalArgumentException("Already connected.");
        }
        if (connectionRequestRepository.existsBySenderIdAndReceiverId(sender.getId(), receiverId)) {
            throw new IllegalArgumentException("Connection request already sent.");
        }

        ConnectionRequest cr = ConnectionRequest.builder()
                .sender(sender)
                .receiver(receiver)
                .build();
        connectionRequestRepository.save(cr);
        return "Connection request sent to " + receiver.getFullName();
    }

    @Transactional
    public String respondToConnectionRequest(String receiverEmail, Long requestId,
                                              boolean accept) {
        User receiver = getCurrentUser(receiverEmail);
        ConnectionRequest cr = connectionRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        if (!cr.getReceiver().getId().equals(receiver.getId())) {
            throw new RuntimeException("Unauthorized: not your request to respond to");
        }
        if (cr.getStatus() != ConnectionRequest.Status.PENDING) {
            throw new IllegalArgumentException("Request already responded to");
        }

        cr.setRespondedAt(LocalDateTime.now());

        if (accept) {
            cr.setStatus(ConnectionRequest.Status.ACCEPTED);
            // Bidirectional connection
            receiver.getConnections().add(cr.getSender());
            cr.getSender().getConnections().add(receiver);
            userRepository.save(receiver);
            userRepository.save(cr.getSender());
            connectionRequestRepository.save(cr);
            return "Connected with " + cr.getSender().getFullName();
        } else {
            cr.setStatus(ConnectionRequest.Status.REJECTED);
            connectionRequestRepository.save(cr);
            return "Connection request rejected";
        }
    }

    public List<ConnectionRequest> getPendingRequests(String email) {
        User user = getCurrentUser(email);
        return connectionRequestRepository
                .findByReceiverIdAndStatus(user.getId(), ConnectionRequest.Status.PENDING);
    }

    @Transactional
    public String removeConnection(String email, Long targetUserId) {
        User user = getCurrentUser(email);
        User target = userRepository.findById(targetUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.getConnections().remove(target);
        target.getConnections().remove(user);
        userRepository.save(user);
        userRepository.save(target);
        return "Connection removed";
    }
}
