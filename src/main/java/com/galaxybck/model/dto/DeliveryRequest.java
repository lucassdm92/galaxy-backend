package com.galaxybck.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record DeliveryRequest(
        @JsonProperty("price_calculation_id") Integer priceCalculationId,
        @JsonProperty("origin") String origin,
        @JsonProperty("destination") String destination,
        @JsonProperty("customer_name") String customerName,
        @JsonProperty("customer_phone") String customerPhone,
        @JsonProperty("customer_note") String customerNote,
        @JsonProperty("user_name") String userName,
        @JsonProperty("delivery_id") Integer deliveryId,
        @JsonProperty("password_to_collect") Integer password_to_collect,
        @JsonProperty("password_to_finalize") Integer password_to_finalize,
        @JsonProperty("customer_email") String customerEmail,
        @JsonProperty("origin_latitude") BigDecimal origemLatitude,
        @JsonProperty("origin_longitude") BigDecimal origemLongitude,
        @JsonProperty("dest_latitude") BigDecimal destinoLatitude,
        @JsonProperty("dest_longitude") BigDecimal destinoLongitude
) {
}
