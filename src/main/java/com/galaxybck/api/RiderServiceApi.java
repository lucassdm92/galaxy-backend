package com.galaxybck.api;

import com.galaxybck.model.dto.RiderRequest;
import com.galaxybck.model.dto.RiderResponse;
import com.galaxybck.model.records.RiderStatusRequest;
import com.galaxybck.model.service.RiderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/rider")
public class RiderServiceApi {

    private static final Logger log = LoggerFactory.getLogger(RiderServiceApi.class);

    private final RiderService riderService;

    public RiderServiceApi(RiderService riderService) {
        this.riderService = riderService;
    }

    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RiderResponse> register(@RequestBody RiderRequest request) {
        log.info("POST /api/rider/register called - name: {}", request.name());
        return ResponseEntity.ok(riderService.register(request));
    }

    @GetMapping("/user/{userName}")
    @PreAuthorize("hasRole('RIDER')")
    public ResponseEntity<RiderResponse> informationRiderByUserName(@PathVariable String userName) {
        log.info("GET /api/rider/user{} called", userName);
        return ResponseEntity.ok(riderService.getRiderInformationByUserName(userName));
    }

    @GetMapping
    public ResponseEntity<List<RiderResponse>> listAll() {
        log.info("GET /api/rider called");
        return ResponseEntity.ok(riderService.listAll());
    }


    @PatchMapping("{userName}/status")
    @PreAuthorize("hasRole('RIDER')")
    public ResponseEntity<Void> updateRiderStatus(@PathVariable String userName, @RequestBody RiderStatusRequest riderStatusRequest) {
        log.info("PATCH /api/rider/{}/status called - newStatus={}", userName, riderStatusRequest.riderStatus());
        this.riderService.updateStatus(userName, riderStatusRequest.riderStatus());
        return ResponseEntity.ok().build();
    }
}
