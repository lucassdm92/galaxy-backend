package com.galaxybck.api;

import com.galaxybck.model.dto.DeliveryRequest;
import com.galaxybck.model.entity.Delivery;
import com.galaxybck.model.service.DeliveryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/delivery")
public class DeliveryServiceApi {

    private static final Logger log = LoggerFactory.getLogger(DeliveryServiceApi.class);

    private final DeliveryService deliveryService;

    public DeliveryServiceApi(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @PostMapping("/request")
    public ResponseEntity<Delivery> requestDelivery(@RequestBody DeliveryRequest request) {
        log.info("POST /api/delivery/request called - customer: {}", request.getCustomerName());
        Delivery delivery = deliveryService.requestDelivery(request);
        return ResponseEntity.ok(delivery);
    }
}
