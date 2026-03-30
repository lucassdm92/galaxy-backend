package com.galaxybck.api;

import com.galaxybck.model.dto.LoginRequest;
import com.galaxybck.model.dto.LoginResponse;
import com.galaxybck.model.dto.UserRequest;
import com.galaxybck.model.dto.UserResponse;
import com.galaxybck.model.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/user")
public class UserServiceApi {

    private static final Logger log = LoggerFactory.getLogger(UserServiceApi.class);

    private final UserService userService;

    public UserServiceApi(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRequest request) {
        log.info("POST /api/user/register called - username: {}", request.getUsername());
        return ResponseEntity.ok(userService.register(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable Integer id) {
        log.info("GET /api/user/{} called", id);
        return ResponseEntity.ok(userService.getById(id));
    }
}
