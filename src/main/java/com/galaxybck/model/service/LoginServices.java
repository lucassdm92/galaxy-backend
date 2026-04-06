package com.galaxybck.model.service;

import com.galaxybck.exception.InvalidCredentialUserException;
import com.galaxybck.exception.UserNotFoundExcpetion;
import com.galaxybck.model.dto.*;
import com.galaxybck.model.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginServices {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    private static final Logger log = LoggerFactory.getLogger(LoginServices.class);

    public LoginServices(UserService userService, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponse login(LoginRequest loginRequest) {

        User user = userService.findUserByUserName(loginRequest.getUsername())
                .orElseThrow(() -> new UserNotFoundExcpetion(loginRequest.getUsername()));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidCredentialUserException("Bad credentials");
        }

        String token = this.jwtService.generateToken(user.getUsername());

        log.info("User logged in: {}", user.getUsername());
        log.info("Token: {}", token);
        return new LoginResponse(user.getUsername(), token, user.getRole());
    }
}
