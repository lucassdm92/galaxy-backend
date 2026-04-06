package com.galaxybck.event;

import com.galaxybck.model.dto.DeliveryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeliveryEventPublisher {
    private final ApplicationEventPublisher eventPublisher;

    public void publishDeliveryAvailable(String riderUsername, DeliveryResponse response) {
        eventPublisher.publishEvent(new DeliveryAvailableEvent(riderUsername, response));
    }
}
