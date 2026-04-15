package com.galaxybck.model.service;

import com.galaxybck.event.DeliveryEventPublisher;
import com.galaxybck.exception.DeliveryNotFoundException;
import com.galaxybck.exception.RiderNotFoundException;
import com.galaxybck.exception.WrongPasswordException;
import com.galaxybck.model.dto.DeliveryHistoryResponse;
import com.galaxybck.model.dto.DeliveryRequest;
import com.galaxybck.model.dto.DeliveryResponse;
import com.galaxybck.model.entity.*;
import com.galaxybck.model.enums.DeliveryStatus;
import com.galaxybck.model.enums.PriceCalculationStatus;
import com.galaxybck.model.enums.RiderStatus;
import com.galaxybck.model.repository.DeliveryHistoryRepository;
import com.galaxybck.model.repository.DeliveryRepository;
import com.galaxybck.model.repository.PriceCalculationRepository;
import com.galaxybck.exception.PriceCalculationNotFoundException;
import com.galaxybck.websocket.DeliveryWebSocketController;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private static final Logger log = LoggerFactory.getLogger(DeliveryService.class);

    private final DeliveryRepository deliveryRepository;
    private final PriceCalculationRepository priceCalculationRepository;
    private final ClientService clientService;
    private final DeliveryWebSocketController deliveryWebSocketController;
    private final UserService userService;

    private final RiderService riderService;

    private final DeliveryHistoryRepository deliveryHistoryRepository;

    private final DeliveryEventPublisher deliveryEventPublisher;

    private final PriceService  priceService;

    @Transactional(readOnly = true)
    public List<DeliveryResponse> searchDeliveryClientByUserName(String userName) {
        log.info("searchDeliveryClientByUserName - searching deliveries for userName: {}", userName);

        Optional<User> userAccount =  userService.findUserByUserName(userName);
        ClientEntity clientEntity = clientService.searchClientByUserId(userAccount.get().getId());
        List<DeliveryResponse> deliveries = deliveryRepository.findDeliveriesByClientEntity((clientEntity))
                .stream()
                .map(DeliveryResponse::from)
                .toList();

        log.info("findByClientId - found {} deliveries for clientId: {}", deliveries.size(), clientEntity.getId());
        return deliveries;
    }


    @Transactional(readOnly = true)
    public List<DeliveryResponse> findPending() {
        return deliveryRepository.findByStatus(DeliveryStatus.PENDING)
                .stream()
                .map(DeliveryResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<DeliveryResponse> findDeliveryByRiderIdStatus(String userName , DeliveryStatus status) {
       Rider rider =  this.riderService.retriveRiderByUserName(userName);
       return deliveryRepository.findDeliveryByRiderIdAndStatus(rider.getId(),status)
                .stream()
                .map(DeliveryResponse::from)
                .toList();
    }

    @Transactional
    public DeliveryResponse requestDelivery(DeliveryRequest request) {

        SecureRandom random = new SecureRandom();

        PriceCalculation priceCalculation = priceCalculationRepository.findById(request.priceCalculationId())
                .orElseThrow(() -> new PriceCalculationNotFoundException(request.priceCalculationId()));

        Optional<User> userAccount = this.userService.findUserByUserName(request.userName());
        ClientEntity clientEntity = clientService.searchClientByUserId(userAccount.get().getId());

        priceCalculation.setStatus(PriceCalculationStatus.ACCEPTED);
        priceCalculationRepository.save(priceCalculation);



        Delivery delivery = Delivery.builder().origin(request.origin())
                .destination(request.destination())
                .customerName(request.customerName())
                .customerPhone(request.customerPhone())
                .customerNote(request.customerNote())
                .priceCalculation(priceCalculation)
                .externalDeliveryCode(this.generateUniqueCode(clientEntity))
                .status(DeliveryStatus.PENDING)
                .clientEntity(clientEntity)
                .origemLatitude(request.origemLatitude())
                .origemLongitude(request.origemLongitude())
                .destinoLatitude(request.destinoLatitude())
                .destinoLongitude(request.destinoLongitude())
                .passwordToCollect(1000 + random.nextInt(9000))
                .passwordToDelivery(1000 + random.nextInt(9000))
                .customerEmail(request.customerEmail())
                .build();

        Delivery saved = deliveryRepository.save(delivery);
        log.info("Delivery criado com id: {}", saved.getId());

        DeliveryResponse deliveryResponse = DeliveryResponse.from(saved);

        this.riderService.findRiderByStatus(RiderStatus.ONLINE).ifPresent(rider -> {
            deliveryEventPublisher.publishDeliveryAvailable(rider.getUser().getUsername(), deliveryResponse);
        });

        return deliveryResponse;
    }

    @Transactional
    public DeliveryResponse acceptDelivery(DeliveryRequest request) {

        Rider rider = this.riderService.retriveRiderByUserName(request.userName());

        Delivery delivery = deliveryRepository.findById(request.deliveryId())
                .orElseThrow(() -> new DeliveryNotFoundException(request.deliveryId()));

        delivery.setRider(rider);
        delivery.setStatus(DeliveryStatus.ACCEPTED);
        deliveryRepository.save(delivery);

        DeliveryHistory history = new DeliveryHistory();
        history.setDelivery(delivery);
        history.setRider(rider);
        history.setAction(DeliveryStatus.ACCEPTED);
        deliveryHistoryRepository.save(history);

        log.info("Rider {} accepted delivery {}", rider.getId(), delivery.getId());

        return DeliveryResponse.from(delivery);
    }

    @Transactional
    public DeliveryResponse rejectDelivery(DeliveryRequest request) {
        log.info("rejectDelivery - userName={} deliveryId={}", request.userName(), request.deliveryId());

        Rider rider = this.riderService.retriveRiderByUserName(request.userName());

        Delivery delivery = deliveryRepository.findById(request.deliveryId())
                .orElseThrow(() -> new DeliveryNotFoundException(request.deliveryId()));

        DeliveryHistory history = new DeliveryHistory();
        history.setDelivery(delivery);
        history.setRider(rider);
        history.setAction(DeliveryStatus.REFUSED);
        deliveryHistoryRepository.save(history);

        log.info("rejectDelivery - riderId={} rejected deliveryId={} externalCode={}", rider.getId(), delivery.getId(), delivery.getExternalDeliveryCode());

        return DeliveryResponse.from(delivery);
    }
    @Transactional
    public DeliveryResponse collectDelivery(DeliveryRequest request) {

       Rider riderEntity = this.riderService.retriveRiderByUserName(request.userName());

       Delivery delivery = deliveryRepository.findById(request.deliveryId())
                .orElseThrow(() -> new DeliveryNotFoundException(request.deliveryId()));

       if(!request.password_to_collect().equals(delivery.getPasswordToCollect())) {
           throw new WrongPasswordException(request.password_to_collect(), delivery.getPasswordToCollect());
       }

       delivery.setStatus(DeliveryStatus.COLLECTED);

       DeliveryHistory deliveryHistory = DeliveryHistory.builder()
               .delivery(delivery).rider(riderEntity)
               .action(DeliveryStatus.COLLECTED).build();

        deliveryRepository.save(delivery);

        deliveryHistoryRepository.save(deliveryHistory);

        log.info("Rider {} collected delivery {}", riderEntity.getId(), delivery.getId());

        deliveryWebSocketController.notifyDeliveryCollected(DeliveryResponse.from(delivery));

        return DeliveryResponse.from(delivery);
    }

    @Transactional
    public DeliveryResponse finalizeDelivery(DeliveryRequest request) {

        Rider riderEntity = this.riderService.retriveRiderByUserName(request.userName());

        Delivery delivery = deliveryRepository.findById(request.deliveryId())
                .orElseThrow(() -> new DeliveryNotFoundException(request.deliveryId()));

        if(!request.password_to_finalize().equals(delivery.getPasswordToDelivery())){
            throw new WrongPasswordException(request.password_to_finalize(), delivery.getPasswordToDelivery());
        }

        delivery.setStatus(DeliveryStatus.DELIVERED);

        DeliveryHistory deliveryHistory = DeliveryHistory.builder()
                .delivery(delivery).rider(riderEntity)
                .action(DeliveryStatus.DELIVERED).build();

        deliveryRepository.save(delivery);

        deliveryHistoryRepository.save(deliveryHistory);

        log.info("Rider {} finalized delivery {}", riderEntity.getId(), delivery.getId());

        return DeliveryResponse.from(delivery);
    }
    private String generateUniqueCode(ClientEntity clientEntity) {
        String code;
        do {
            code = "#G" + String.format("%05d", new SecureRandom().nextInt(90000) + 10000);
        } while (deliveryRepository.existsByExternalDeliveryCodeAndClientEntityId(code, clientEntity.getId())); // 👈 gera até ser único
        return code;
    }

    public record DeliveryCollectedEvent(DeliveryResponse response) {}
}
