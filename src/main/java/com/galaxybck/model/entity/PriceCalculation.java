package com.galaxybck.model.entity;

import com.galaxybck.model.enums.DeliveryStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "price_calculation")
public class PriceCalculation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "total_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;

    @Column(name = "base_delivery_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal baseDeliveryPrice;

    @Column(name = "commission_value", nullable = false, precision = 10, scale = 2)
    private BigDecimal commissionValue;

    @Column(name = "service_fee_value", nullable = false, precision = 10, scale = 2)
    private BigDecimal serviceFeeValue;

    @Column(name = "exceeded_km_cost", nullable = false, precision = 10, scale = 2)
    private BigDecimal exceededKmCost;

    @Column(name = "distance_km", nullable = false, precision = 5, scale = 2)
    private BigDecimal distanceKm;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private DeliveryStatus status;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    @PrePersist
    protected void onCreate() {
        this.dateCreated = LocalDateTime.now();
        this.status = DeliveryStatus.PENDING;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }

    public BigDecimal getBaseDeliveryPrice() { return baseDeliveryPrice; }
    public void setBaseDeliveryPrice(BigDecimal baseDeliveryPrice) { this.baseDeliveryPrice = baseDeliveryPrice; }

    public BigDecimal getCommissionValue() { return commissionValue; }
    public void setCommissionValue(BigDecimal commissionValue) { this.commissionValue = commissionValue; }

    public BigDecimal getServiceFeeValue() { return serviceFeeValue; }
    public void setServiceFeeValue(BigDecimal serviceFeeValue) { this.serviceFeeValue = serviceFeeValue; }

    public BigDecimal getExceededKmCost() { return exceededKmCost; }
    public void setExceededKmCost(BigDecimal exceededKmCost) { this.exceededKmCost = exceededKmCost; }

    public BigDecimal getDistanceKm() { return distanceKm; }
    public void setDistanceKm(BigDecimal distanceKm) { this.distanceKm = distanceKm; }

    public DeliveryStatus getStatus() { return status; }
    public void setStatus(DeliveryStatus status) { this.status = status; }

    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }

    public LocalDateTime getDateCreated() { return dateCreated; }
    public void setDateCreated(LocalDateTime dateCreated) { this.dateCreated = dateCreated; }
}
