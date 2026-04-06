package com.galaxybck.api;

import com.galaxybck.model.dto.ClientRequest;
import com.galaxybck.model.dto.ClientResponse;
import com.galaxybck.model.service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/client")
public class ClientServiceApi {

    private static final Logger log = LoggerFactory.getLogger(ClientServiceApi.class);

    private final ClientService clientService;

    public ClientServiceApi(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClientResponse> register(@RequestBody ClientRequest request) {
        log.info("POST /api/client/register called - email: {}", "teste");
        return ResponseEntity.ok(clientService.register(request));
    }

    @GetMapping("/{userName}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<ClientResponse> clientInformationByUserName(@PathVariable String userName) {
        log.info("GET /api/client/{} called", userName);
        return ResponseEntity.ok(clientService.retriveClientInfoByUserName(userName));
    }
}
