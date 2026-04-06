package com.galaxybck.model.repository;

import com.galaxybck.model.dto.DeliveryResponse;
import com.galaxybck.model.entity.ClientEntity;
import com.galaxybck.model.entity.Delivery;
import com.galaxybck.model.enums.DeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Integer> {

    List<Delivery> findByClientEntityId(Integer clientId);

    List<Delivery> findByStatus(DeliveryStatus status);

    List<Delivery> findByRiderId(Integer riderId);
    Boolean existsByExternalDeliveryCodeAndClientEntityId(String externalCodeId, Integer clientId);

    List<Delivery> findDeliveriesByClientEntity(ClientEntity clientEntity);
}
