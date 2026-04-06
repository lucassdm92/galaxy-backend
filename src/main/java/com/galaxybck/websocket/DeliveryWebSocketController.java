package com.galaxybck.websocket;

import com.galaxybck.model.dto.DeliveryHistoryResponse;
import com.galaxybck.model.dto.DeliveryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Component
public class DeliveryWebSocketController {

    private static final Logger log = LoggerFactory.getLogger(DeliveryWebSocketController.class);

    private final SimpMessagingTemplate messagingTemplate;

    public DeliveryWebSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void notifyRiderDeliveryAvailable(String riderUsername, DeliveryResponse delivery) {
        log.info("notifyRiderDeliveryAvailable - riderUsername={} externalCode={} origin={} destination={}", riderUsername, delivery.getExternalDeliveryCode(), delivery.getOrigin(), delivery.getDestination());
        messagingTemplate.convertAndSendToUser(riderUsername, "/queue/delivery-available", delivery);
    }

    public void notifyDeliveryAccepted(DeliveryHistoryResponse history) {
        log.info("Broadcasting accepted delivery to /topic/delivery-accepted - deliveryId: {}", history.getDeliveryId());
        messagingTemplate.convertAndSend("/topic/delivery-accepted", history);
    }

    public void notifyDeliveryCollected(DeliveryResponse delivery) {
        log.info("Broadcasting collected delivery to /topic/delivery-collected - deliveryId: {}", delivery );
        messagingTemplate.convertAndSend("/topic/delivery-collected", delivery);
    }

    public void notifyDeliveryDelivered(DeliveryHistoryResponse history) {
        log.info("Broadcasting delivered delivery to /topic/delivery-delivered - deliveryId: {}", history.getDeliveryId());
        messagingTemplate.convertAndSend("/topic/delivery-delivered", history);
    }
}
