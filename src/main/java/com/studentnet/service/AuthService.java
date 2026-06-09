package com.studentnet.service;

import com.studentnet.dto.AuthDto;
import com.studentnet.model.User;
import com.studentnet.repository.UserRepository;
import com.studentnet.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private JwtUtils jwtUtils;

    @Transactional
    public AuthDto.AuthResponse register(AuthDto.RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already in use: " + request.getEmail());
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .university(request.getUniversity())
                .department(request.getDepartment())
                .yearOfStudy(request.getYearOfStudy())
                .role(User.Role.STUDENT)
                .build();

        userRepository.save(user);
        String token = jwtUtils.generateToken(user.getEmail());
        return new AuthDto.AuthResponse(token, user.getId(), user.getFullName(),
                                        user.getEmail(), user.getRole().name());
    }

    public AuthDto.AuthResponse login(AuthDto.LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtils.generateToken(request.getEmail());

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new AuthDto.AuthResponse(token, user.getId(), user.getFullName(),
                                        user.getEmail(), user.getRole().name());
    }
}
