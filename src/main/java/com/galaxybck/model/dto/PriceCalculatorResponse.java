package com.galaxybck.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

public class PriceCalculatorResponse {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("total_price")
    private BigDecimal totalPrice;

    @JsonProperty("distance_km")
    private BigDecimal distanceKm;

    @JsonProperty("calculated_at")
    private LocalDateTime calculatedAt;

    public PriceCalculatorResponse(Integer id, BigDecimal totalPrice, BigDecimal distanceKm) {
        this.id = id;
        this.totalPrice = totalPrice.setScale(2, RoundingMode.HALF_UP);
        this.distanceKm = distanceKm.setScale(2, RoundingMode.HALF_UP);
        this.calculatedAt = LocalDateTime.now();
    }

    public Integer getId() { return id; }
    public BigDecimal getTotalPrice() { return totalPrice; }
    public BigDecimal getDistanceKm() { return distanceKm; }
    public LocalDateTime getCalculatedAt() { return calculatedAt; }
}
