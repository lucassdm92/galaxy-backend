package com.galaxybck.model.service;

import com.galaxybck.model.dto.RiderRequest;
import com.galaxybck.model.dto.RiderResponse;
import com.galaxybck.model.dto.DeliveryHistoryResponse;
import com.galaxybck.model.entity.Delivery;
import com.galaxybck.model.entity.DeliveryHistory;
import com.galaxybck.model.entity.Rider;
import com.galaxybck.model.entity.User;
import com.galaxybck.model.enums.DeliveryStatus;
import com.galaxybck.model.enums.RiderStatus;
import com.galaxybck.exception.DeliveryNotFoundException;
import com.galaxybck.exception.RiderNotFoundException;
import com.galaxybck.model.repository.DeliveryHistoryRepository;
import com.galaxybck.model.repository.DeliveryRepository;
import com.galaxybck.model.repository.RiderRepository;
import com.galaxybck.websocket.DeliveryWebSocketController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

@Service
public class RiderService {

    private static final Logger log = LoggerFactory.getLogger(RiderService.class);

    private final RiderRepository riderRepository;
    private final DeliveryRepository deliveryRepository;
    private final DeliveryHistoryRepository deliveryHistoryRepository;
    private final DeliveryWebSocketController deliveryWebSocketController;

    private final UserService userService;

    public RiderService(RiderRepository riderRepository, DeliveryRepository deliveryRepository, DeliveryHistoryRepository deliveryHistoryRepository, DeliveryWebSocketController deliveryWebSocketController, UserService userService) {
        this.riderRepository = riderRepository;
        this.deliveryRepository = deliveryRepository;
        this.deliveryHistoryRepository = deliveryHistoryRepository;
        this.deliveryWebSocketController = deliveryWebSocketController;
        this.userService = userService;
    }

    @Transactional
    public RiderResponse register(RiderRequest request) {

        User userAccount = this.userService.getById(request.userId());
        Rider rider = Rider.builder()
                .user(userAccount)
                .uniqueCode(generateUniqueCode())
                .name(request.name())
                .phone(request.phone())
                .address(request.address())
                .vehicleType(request.vehicleType())
                .vehiclePlate(request.vehiclePlate())
                .identificationDocument(request.identificationDocument())
                .driveLicense(request.driveLicense())
                .vatNumber(request.vatNumber())
                .vat(request.vat())
                .riderType(request.riderType())
                .createdBy(request.createdBy())
                .build();

        Rider saved = riderRepository.save(rider);
        log.info("Rider registered with id: {}", saved.getId());
        return RiderResponse.from(saved);
    }

    private Integer generateUniqueCode() {
        SecureRandom random = new SecureRandom();
        Integer code;
        do {
            code = random.nextInt(10001);
        } while (riderRepository.existsByUniqueCode(code));
        return code;
    }

    @Transactional(readOnly = true)
    public RiderResponse getById(Integer id) {
        Rider rider = riderRepository.findById(id)
                .orElseThrow(() -> new RiderNotFoundException(id));
        return RiderResponse.from(rider);
    }

    @Transactional(readOnly = true)
    public List<RiderResponse> listAll() {
        return riderRepository.findAll().stream()
                .map(RiderResponse::from)
                .toList();
    }

    @Transactional
    public DeliveryHistoryResponse refuseDelivery(Integer riderId, Integer deliveryId) {
        Rider rider = riderRepository.findById(riderId)
                .orElseThrow(() -> new RiderNotFoundException(riderId));
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new DeliveryNotFoundException(deliveryId));

        DeliveryHistory history = new DeliveryHistory();
        history.setDelivery(delivery);
        history.setRider(rider);
        history.setAction(DeliveryStatus.REFUSED);
        DeliveryHistory saved = deliveryHistoryRepository.save(history);

        log.info("Rider {} refused delivery {}", riderId, deliveryId);
        return DeliveryHistoryResponse.from(saved);
    }

    @Transactional
    public DeliveryHistoryResponse deliverDelivery(Integer riderId, Integer deliveryId) {
        Rider rider = riderRepository.findById(riderId)
                .orElseThrow(() -> new RiderNotFoundException(riderId));
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new DeliveryNotFoundException(deliveryId));

        delivery.setStatus(DeliveryStatus.DELIVERED);
        deliveryRepository.save(delivery);

        DeliveryHistory history = new DeliveryHistory();
        history.setDelivery(delivery);
        history.setRider(rider);
        history.setAction(DeliveryStatus.DELIVERED);
        DeliveryHistory saved = deliveryHistoryRepository.save(history);

        log.info("Rider {} delivered delivery {}", riderId, deliveryId);

        DeliveryHistoryResponse response = DeliveryHistoryResponse.from(saved);
        deliveryWebSocketController.notifyDeliveryDelivered(response);
        return response;
    }

    @Transactional(readOnly = true)
    public List<DeliveryHistoryResponse> getHistoryByRider(Integer riderId) {
        return deliveryHistoryRepository.findByRiderId(riderId).stream()
                .map(DeliveryHistoryResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public RiderResponse getRiderInformationByUserName(final String userName) {

        Optional<User> userAccount = userService.findUserByUserName(userName);

        Rider rider = this.riderRepository.findRiderByUser(userAccount.get())
                .orElseThrow(() -> new RiderNotFoundException(userName));

        return RiderResponse.from(rider);
    }

    @Transactional(readOnly = true)
    public Rider retriveRiderByUserName(final String userName) {
        Optional<User> userAccount = userService.findUserByUserName(userName);

        Rider rider = this.riderRepository.findRiderByUser(userAccount.get())
                .orElseThrow(() -> new RiderNotFoundException(userName));

        return rider;
    }

    @Transactional
    public void updateStatus(String userName, RiderStatus status) {

        log.info("updateStatus - userName={} newStatus={}", userName, status);

        Optional<User> user = this.userService.findUserByUserName(userName);

        Rider rider = riderRepository.findRiderByUser(user.get()).orElseThrow(() -> new RiderNotFoundException(userName));
        log.info("updateStatus - riderId={} currentStatus={}", rider.getId(), rider.getActive());

        rider.setActive(status);

        Rider saved = riderRepository.save(rider);
        log.info("updateStatus - riderId={} status updated to={}", saved.getId(), saved.getActive());
    }

    @Transactional(readOnly = true)
    public Optional<Rider> findRiderByStatus(RiderStatus status) {
        log.info("findRiderByStatus - searching rider with status={}", status);
        return this.riderRepository.findRandomByStatus(status);
    }

    @Transactional(readOnly = true)
    public Boolean isOnline(String userName) {
        Rider rider = retriveRiderByUserName(userName);
        return rider.getActive() == RiderStatus.ONLINE;
    }
}
