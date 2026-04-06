package com.galaxybck.model.repository;

import com.galaxybck.model.entity.DeliveryHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryHistoryRepository extends JpaRepository<DeliveryHistory, Integer> {

    List<DeliveryHistory> findByRiderId(Integer riderId);

    List<DeliveryHistory> findByDeliveryId(Integer deliveryId);
}
