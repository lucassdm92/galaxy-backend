package com.galaxybck.api;

import com.galaxybck.model.dto.ApiResponse;
import com.galaxybck.model.dto.LoginRequest;
import com.galaxybck.model.dto.LoginResponse;
import com.galaxybck.model.service.LoginServices;
import com.galaxybck.model.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class LoginServiceApi {

    private static final Logger log = LoggerFactory.getLogger(LoginServiceApi.class);

    private final LoginServices loginServices;

    public LoginServiceApi(LoginServices loginServices) {
        this.loginServices = loginServices;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest request) {
        log.info("POST /api/auth/login called - username: {}", request.getUsername());

        LoginResponse loginResponse = loginServices.login(request);

        return ResponseEntity.ok(new ApiResponse<>("200","Login Successful", loginResponse));
    }
}
