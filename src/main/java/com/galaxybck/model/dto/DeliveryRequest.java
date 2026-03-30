package com.galaxybck.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeliveryRequest {

    @JsonProperty("price_calculation_id")
    private Integer priceCalculationId;

    @JsonProperty("origin")
    private String origin;

    @JsonProperty("destination")
    private String destination;

    @JsonProperty("customer_name")
    private String customerName;

    @JsonProperty("customer_phone")
    private String customerPhone;

    @JsonProperty("customer_note")
    private String customerNote;

    public Integer getPriceCalculationId() { return priceCalculationId; }
    public void setPriceCalculationId(Integer priceCalculationId) { this.priceCalculationId = priceCalculationId; }

    public String getOrigin() { return origin; }
    public void setOrigin(String origin) { this.origin = origin; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getCustomerPhone() { return customerPhone; }
    public void setCustomerPhone(String customerPhone) { this.customerPhone = customerPhone; }

    public String getCustomerNote() { return customerNote; }
    public void setCustomerNote(String customerNote) { this.customerNote = customerNote; }
}
