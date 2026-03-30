package com.galaxybck.model.service;

import com.galaxybck.model.dto.ClientResponse;
import com.galaxybck.model.dto.LoginRequest;
import com.galaxybck.model.dto.LoginResponse;
import com.galaxybck.model.dto.UserResponse;
import com.galaxybck.model.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginServices {

    private UserService userService;
    private JwtService jwtService;
    private ClientService clientService;

    private static final Logger log = LoggerFactory.getLogger(LoginServices.class);

    public LoginServices(UserService userService, JwtService jwtService, ClientService clientService) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.clientService = clientService;
    }

    public LoginResponse login(LoginRequest loginRequest) {

        UserResponse user = userService.findUserByUserName(loginRequest.getUsername());

        if (!loginRequest.getPassword().equals(loginRequest.getPassword())) {
            return null;
        }

        String token = this.jwtService.generateToken(user.getUsername(), user.getId());
        ClientResponse clientResponse = this.clientService.searchClientById(user.getId());

        log.info("User logged in: {}", user.getUsername());
        log.info("Token: {}", token);
        log.info("Client response: {}", clientResponse.toString());
        return new LoginResponse(user.getId(), user.getUsername(), token, clientResponse);
    }
}
