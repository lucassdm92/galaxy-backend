package com.galaxybck.model.service;

import com.galaxybck.exception.EmailAlreadyInUseException;
import com.galaxybck.exception.UserAlreadyInUseException;
import com.galaxybck.exception.UserNotFoundExcpetion;
import com.galaxybck.model.dto.UserRequest;
import com.galaxybck.model.dto.UserResponse;
import com.galaxybck.model.entity.User;
import com.galaxybck.model.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserResponse register(UserRequest request) {

        userRepository.findByUsername(request.getUsername()).ifPresent(u -> {
            throw new UserAlreadyInUseException(request.getUsername());
        });

        userRepository.findByEmail(request.getEmail()).ifPresent(u -> {
            throw new EmailAlreadyInUseException(request.getEmail());
        });

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCreatedBy(request.getCreatedBy());
        user.setRole(request.getRole());

        User saved = userRepository.save(user);
        log.info("User registered with id: {}", saved.getUsername());
        return UserResponse.from(saved);
    }

    @Transactional(readOnly = true)
    public User getById(Integer id) {
        log.info("getById user account - searching for user id: {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundExcpetion(id));
    }

    public UserResponse findUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found: " + email));
        return UserResponse.from(user);
    }

    public Optional<User> findUserByUserName(String username) {
        log.info("findUserByUserName - searching for username: {}", username);
        User user = userRepository.findByUsername(username).orElseThrow(() -> {
            log.warn("findUserByUserName - user not found for username: {}", username);
            return new UserNotFoundExcpetion("User not found: " + username);
        });
        log.info("findUserByUserName - user found: id={}, username={}", user.getId(), user.getUsername());
        return Optional.of(user);
    }
}
