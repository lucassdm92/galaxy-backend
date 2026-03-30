package com.galaxybck.model.dto;

import com.galaxybck.model.entity.PriceConfig;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class PriceConfigResponse {

    private Integer id;

    @JsonProperty("base_delivery_price")
    private BigDecimal baseDeliveryPrice;

    @JsonProperty("price_per_km")
    private BigDecimal pricePerKm;

    @JsonProperty("min_km")
    private BigDecimal minKm;

    @JsonProperty("service_fee")
    private BigDecimal serviceFee;

    @JsonProperty("commission_rate")
    private BigDecimal commissionRate;

    @JsonProperty("boost_multiplier")
    private BigDecimal boostMultiplier;

    private Boolean active;

    public static PriceConfigResponse from(PriceConfig entity) {
        PriceConfigResponse dto = new PriceConfigResponse();
        dto.id = entity.getId();
        dto.baseDeliveryPrice = entity.getBaseDeliveryPrice();
        dto.pricePerKm = entity.getPricePerKm();
        dto.minKm = entity.getMinKm();
        dto.serviceFee = entity.getServiceFee();
        dto.commissionRate = entity.getCommissionRate();
        dto.boostMultiplier = entity.getBoostMultiplier();
        dto.active = entity.getActive();
        return dto;
    }

    public Integer getId() { return id; }
    public BigDecimal getBaseDeliveryPrice() { return baseDeliveryPrice; }
    public BigDecimal getPricePerKm() { return pricePerKm; }
    public BigDecimal getMinKm() { return minKm; }
    public BigDecimal getServiceFee() { return serviceFee; }
    public BigDecimal getCommissionRate() { return commissionRate; }
    public BigDecimal getBoostMultiplier() { return boostMultiplier; }
    public Boolean getActive() { return active; }
}
