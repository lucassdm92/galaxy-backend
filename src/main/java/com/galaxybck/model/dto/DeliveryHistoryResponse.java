package com.galaxybck.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.galaxybck.model.entity.DeliveryHistory;
import com.galaxybck.model.enums.DeliveryStatus;

import java.time.LocalDateTime;

public class DeliveryHistoryResponse {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("delivery_id")
    private Integer deliveryId;

    @JsonProperty("rider_id")
    private Integer riderId;

    @JsonProperty("rider_name")
    private String riderName;

    @JsonProperty("action")
    private DeliveryStatus action;

    @JsonProperty("date_created")
    private LocalDateTime dateCreated;

    public static DeliveryHistoryResponse from(DeliveryHistory entity) {
        DeliveryHistoryResponse dto = new DeliveryHistoryResponse();
        dto.id = entity.getId();
        dto.deliveryId = entity.getDelivery().getId();
        dto.riderId = entity.getRider().getId();
        dto.riderName = entity.getRider().getName();
        dto.action = entity.getAction();
        dto.dateCreated = entity.getDateCreated();
        return dto;
    }

    public Integer getId() { return id; }
    public Integer getDeliveryId() { return deliveryId; }
    public Integer getRiderId() { return riderId; }
    public String getRiderName() { return riderName; }
    public DeliveryStatus getAction() { return action; }
    public LocalDateTime getDateCreated() { return dateCreated; }
}
