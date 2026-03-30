package com.galaxybck.model.service;

import com.galaxybck.model.dto.LoginRequest;
import com.galaxybck.model.dto.LoginResponse;
import com.galaxybck.model.dto.UserRequest;
import com.galaxybck.model.dto.UserResponse;
import com.galaxybck.model.entity.Client;
import com.galaxybck.model.entity.User;
import com.galaxybck.model.repository.ClientRepository;
import com.galaxybck.model.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final JwtService jwtService;

    public UserService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Transactional
    public UserResponse register(UserRequest request) {
        userRepository.findByUsername(request.getUsername()).ifPresent(u -> {
            throw new RuntimeException("Username already in use: " + request.getUsername());
        });

        userRepository.findByEmail(request.getEmail()).ifPresent(u -> {
            throw new RuntimeException("Email already in use: " + request.getEmail());
        });

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setCreatedBy(request.getCreatedBy());

        User saved = userRepository.save(user);
        log.info("User registered with id: {}", saved.getUsername());
        return UserResponse.from(saved);
    }

    @Transactional(readOnly = true)
    public UserResponse getById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found: " + id));
        return UserResponse.from(user);
    }

    public UserResponse findUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found: " + email));
        return UserResponse.from(user);
    }

    public UserResponse findUserByUserName(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found: " + username));
        return UserResponse.from(user);
    }
}
