package com.galaxybck.api;

import com.galaxybck.model.dto.DeliveryHistoryResponse;
import com.galaxybck.model.dto.DeliveryRequest;
import com.galaxybck.model.dto.DeliveryResponse;
import com.galaxybck.model.service.DeliveryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/delivery")
public class DeliveryServiceApi {

    private static final Logger log = LoggerFactory.getLogger(DeliveryServiceApi.class);

    private final DeliveryService deliveryService;

    public DeliveryServiceApi(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @GetMapping("/client/{userName}")
    @PreAuthorize("hasAnyRole('CLIENT','ADMIN')")
    public ResponseEntity<List<DeliveryResponse>> listDeliveryByClient(@PathVariable String userName) {
        log.info("GET /api/delivery/client/{} called", userName);
        List<DeliveryResponse> deliveries = deliveryService.searchDeliveryClientByUserName(userName);
        log.info("GET /api/delivery/client/{} returning {} deliveries", userName, deliveries.toString());
        return ResponseEntity.ok(deliveries);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<DeliveryResponse>> listPending() {
        log.info("GET /api/delivery/pending called");
        return ResponseEntity.ok(deliveryService.findPending());
    }

    @PostMapping("/request")
    public ResponseEntity<DeliveryResponse> requestDelivery(@RequestBody DeliveryRequest request) {
        log.info("POST /api/delivery/request called - customer: {}", request.customerName());
        return ResponseEntity.ok(deliveryService.requestDelivery(request));
    }

    @GetMapping("/rider/{userName}")
    public ResponseEntity<List<DeliveryResponse>> listDeliveryByRiderId(@PathVariable String userName) {
        log.info("GET /api/myDelivery/listDeliveryByRiderId called");
        return ResponseEntity.ok(deliveryService.findDeliveryByRiderId(userName));
    }

    @PostMapping("/accept")
    @PreAuthorize("hasAnyRole('RIDER','ADMIN')")
    public ResponseEntity<DeliveryResponse> accept(@RequestBody DeliveryRequest request) {
        log.info("POST /api/delivery/accept {} called", request.toString());
        return ResponseEntity.ok(deliveryService.acceptDelivery(request));
    }

    @PostMapping("/collect")
    @PreAuthorize("hasAnyRole('RIDER','ADMIN')")
    public ResponseEntity<DeliveryResponse> collectDelivery(@RequestBody DeliveryRequest request) {
        log.info("POST /api/delivery/collect {} called", request.toString());
        return ResponseEntity.ok(deliveryService.collectDelivery(request));
    }

    @PostMapping("/finalize")
    @PreAuthorize("hasAnyRole('RIDER','ADMIN')")
    public ResponseEntity<DeliveryResponse> finalizeDelivery(@RequestBody DeliveryRequest request) {
        log.info("POST /api/delivery/finalize {} called", request.toString());
        return ResponseEntity.ok(deliveryService.finalizeDelivery(request));
    }


    @PostMapping("/reject")
    @PreAuthorize("hasAnyRole('RIDER','ADMIN')")
    public ResponseEntity<DeliveryResponse> reject(@RequestBody DeliveryRequest request) {
        log.info("POST /api/delivery/reject {} called", request.toString());
        return ResponseEntity.ok(deliveryService.rejectDelivery(request));
    }
}
