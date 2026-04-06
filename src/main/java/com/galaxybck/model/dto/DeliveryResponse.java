package com.galaxybck.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.galaxybck.model.entity.Delivery;
import com.galaxybck.model.enums.DeliveryStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DeliveryResponse {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("external_delivery_code")
    private String externalDeliveryCode;


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

    @JsonProperty("price_calculation_id")
    private Integer priceCalculationId;

    @JsonProperty("status")
    private DeliveryStatus status;

    @JsonProperty("date_created")
    private LocalDateTime dateCreated;

    @JsonProperty("rider_fee")
    private BigDecimal riderFee;

    @JsonProperty("password_to_collect")
    private Integer passwordToCollect;

    @JsonProperty("password_to_delivery")
    private Integer passwordToDelivery;

    private DeliveryResponse(Delivery delivery) {
        this.id = delivery.getId();
        this.externalDeliveryCode = delivery.getExternalDeliveryCode();
        this.origin = delivery.getOrigin();
        this.destination = delivery.getDestination();
        this.customerName = delivery.getCustomerName();
        this.customerPhone = delivery.getCustomerPhone();
        this.customerNote = delivery.getCustomerNote();
        this.priceCalculationId = delivery.getPriceCalculation().getId();
        this.status = delivery.getStatus();
        this.dateCreated = delivery.getDateCreated();
        this.riderFee = delivery.getPriceCalculation().getRiderFee();
        this.passwordToCollect = delivery.getPasswordToCollect();
        this.passwordToDelivery = delivery.getPasswordToDelivery();
        this.riderFee = delivery.getPriceCalculation().getRiderFee();
    }

    public static DeliveryResponse from(Delivery delivery) {
        return new DeliveryResponse(delivery);
    }

}
