package com.galaxybck.event;

import com.galaxybck.model.service.DeliveryService;
import com.galaxybck.websocket.DeliveryWebSocketController;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class DeliveryEventListener {

    private static final Logger log = LoggerFactory.getLogger(DeliveryService.class);
    private final DeliveryWebSocketController deliveryWebSocketController;

    public DeliveryEventListener(DeliveryWebSocketController deliveryWebSocketController) {
        this.deliveryWebSocketController = deliveryWebSocketController;
    }
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onDeliveryAvailable(DeliveryAvailableEvent event) {
        log.info("DeliveryEventListener.onDeliveryAvailable {}", event);
        this.deliveryWebSocketController.notifyRiderDeliveryAvailable(
                event.riderUsername(),
                event.response()
        );
    }
}
