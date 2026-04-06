package com.galaxybck.api;

import com.galaxybck.model.dto.PriceCalculatorResponse;
import com.galaxybck.model.dto.RequestPrice;
import com.galaxybck.model.service.PriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/prices")
public class PriceServicesApi {

    private static final Logger log = LoggerFactory.getLogger(PriceServicesApi.class);

    private final PriceService priceService;

    public PriceServicesApi(PriceService priceService) {
        this.priceService = priceService;
    }

    @PostMapping("/current")
    @PreAuthorize("hasAnyRole('CLIENT','ADMIN')")
    public ResponseEntity<PriceCalculatorResponse> getCurrentPrice(@RequestBody RequestPrice request) {
        log.info("POST /api/prices/current called - origin: {}, destination: {}", request.origin(), request.destination());
        PriceCalculatorResponse response = priceService.getPriceConfig(request);
        return ResponseEntity.ok(response);
    }
}
