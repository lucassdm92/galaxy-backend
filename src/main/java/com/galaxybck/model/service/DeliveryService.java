package com.galaxybck.model.service;

import com.galaxybck.model.dto.DeliveryRequest;
import com.galaxybck.model.entity.Delivery;
import com.galaxybck.model.entity.PriceCalculation;
import com.galaxybck.model.enums.DeliveryStatus;
import com.galaxybck.model.repository.DeliveryRepository;
import com.galaxybck.model.repository.PriceCalculationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeliveryService {

    private static final Logger log = LoggerFactory.getLogger(DeliveryService.class);

    private final DeliveryRepository deliveryRepository;
    private final PriceCalculationRepository priceCalculationRepository;

    public DeliveryService(DeliveryRepository deliveryRepository, PriceCalculationRepository priceCalculationRepository) {
        this.deliveryRepository = deliveryRepository;
        this.priceCalculationRepository = priceCalculationRepository;
    }

    @Transactional
    public Delivery requestDelivery(DeliveryRequest request) {
        PriceCalculation priceCalculation = priceCalculationRepository.findById(request.getPriceCalculationId())
                .orElseThrow(() -> new RuntimeException("PriceCalculation not found: " + request.getPriceCalculationId()));

        priceCalculation.setStatus(DeliveryStatus.ACCEPTED);
        priceCalculationRepository.save(priceCalculation);

        Delivery delivery = new Delivery();
        delivery.setOrigin(request.getOrigin());
        delivery.setDestination(request.getDestination());
        delivery.setCustomerName(request.getCustomerName());
        delivery.setCustomerPhone(request.getCustomerPhone());
        delivery.setCustomerNote(request.getCustomerNote());
        delivery.setPriceCalculation(priceCalculation);

        Delivery saved = deliveryRepository.save(delivery);
        log.info("Delivery criado com id: {}", saved.getId());
        return saved;
    }
}
