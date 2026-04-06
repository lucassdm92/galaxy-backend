package com.galaxybck.model.entity;

import com.galaxybck.model.enums.DeliveryStatus;
import com.galaxybck.model.enums.PriceCalculationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    @Column(name = "rider_fee", nullable = false, precision = 5, scale = 2)
    private BigDecimal riderFee;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private PriceCalculationStatus status;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private ClientEntity clientEntity;

    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    @PrePersist
    protected void onCreate() {
        this.dateCreated = LocalDateTime.now();
        this.status = PriceCalculationStatus.PENDING;
    }

}
