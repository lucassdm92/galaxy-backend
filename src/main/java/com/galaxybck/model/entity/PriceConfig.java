package com.galaxybck.model.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pricing_config")
public class PriceConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "base_delivery_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal baseDeliveryPrice;

    @Column(name = "price_per_km", nullable = false, precision = 10, scale = 2)
    private BigDecimal pricePerKm;

    @Column(name = "min_km", nullable = false, precision = 5, scale = 2)
    private BigDecimal minKm;

    @Column(name = "service_fee", nullable = false, precision = 10, scale = 2)
    private BigDecimal serviceFee;

    @Column(name = "commission_rate", nullable = false, precision = 5, scale = 4)
    private BigDecimal commissionRate;

    @Column(name = "boost_multiplier", precision = 5, scale = 2)
    private BigDecimal boostMultiplier;

    @Column(name = "active")
    private Boolean active = true;

    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    @Column(name = "date_updated")
    private LocalDateTime dateUpdated;

    @Column(name = "user_create", length = 100)
    private String userCreate;

    @Column(name = "user_update", length = 100)
    private String userUpdate;

    @PrePersist
    protected void onCreate() {
        this.dateCreated = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.dateUpdated = LocalDateTime.now();
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public BigDecimal getBaseDeliveryPrice() { return baseDeliveryPrice; }
    public void setBaseDeliveryPrice(BigDecimal baseDeliveryPrice) { this.baseDeliveryPrice = baseDeliveryPrice; }

    public BigDecimal getPricePerKm() { return pricePerKm; }
    public void setPricePerKm(BigDecimal pricePerKm) { this.pricePerKm = pricePerKm; }

    public BigDecimal getMinKm() { return minKm; }
    public void setMinKm(BigDecimal minKm) { this.minKm = minKm; }

    public BigDecimal getServiceFee() { return serviceFee; }
    public void setServiceFee(BigDecimal serviceFee) { this.serviceFee = serviceFee; }

    public BigDecimal getCommissionRate() { return commissionRate; }
    public void setCommissionRate(BigDecimal commissionRate) { this.commissionRate = commissionRate; }

    public BigDecimal getBoostMultiplier() { return boostMultiplier; }
    public void setBoostMultiplier(BigDecimal boostMultiplier) { this.boostMultiplier = boostMultiplier; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }

    public LocalDateTime getDateCreated() { return dateCreated; }
    public void setDateCreated(LocalDateTime dateCreated) { this.dateCreated = dateCreated; }

    public LocalDateTime getDateUpdated() { return dateUpdated; }
    public void setDateUpdated(LocalDateTime dateUpdated) { this.dateUpdated = dateUpdated; }

    public String getUserCreate() { return userCreate; }
    public void setUserCreate(String userCreate) { this.userCreate = userCreate; }

    public String getUserUpdate() { return userUpdate; }
    public void setUserUpdate(String userUpdate) { this.userUpdate = userUpdate; }
}
