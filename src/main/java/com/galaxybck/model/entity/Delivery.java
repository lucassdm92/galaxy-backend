package com.galaxybck.model.entity;

import com.galaxybck.model.enums.DeliveryStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "GLX_DELIVERY")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "origin", nullable = false, length = 255)
    private String origin;

    @Column(name = "destination", nullable = false, length = 255)
    private String destination;

    @Column(name = "origem_latitude", precision = 9, scale = 7, nullable = false)
    private BigDecimal origemLatitude;

    @Column(name = "origem_longitude", precision = 10, scale = 7, nullable = false)
    private BigDecimal origemLongitude;

    @Column(name = "destino_latitude", precision = 9, scale = 7, nullable = false)
    private BigDecimal destinoLatitude;

    @Column(name = "destino_longitude", precision = 10, scale = 7, nullable = false)
    private BigDecimal destinoLongitude;

    @Column(name = "customer_name", nullable = false, length = 100)
    private String customerName;

    @Column(name = "customer_phone", nullable = false, length = 20)
    private String customerPhone;

    @Column(name = "customer_note", length = 500)
    private String customerNote;

    @Column(name = "customer_email", length = 500)
    private String customerEmail;

    @Column(name = "EXTERNAL_DELIVERY_CODE", length = 500)
    private String externalDeliveryCode;

    @Column(name = "PASSWORD_TO_COLLECT", length = 500)
    private Integer passwordToCollect;

    @Column(name = "PASSWORD_TO_DELIVERY", length = 500)
    private Integer passwordToDelivery;

    @ManyToOne
    @JoinColumn(name = "price_calculation_id", nullable = false)
    private PriceCalculation priceCalculation;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private ClientEntity clientEntity;

    @ManyToOne
    @JoinColumn(name = "rider_id", nullable = true)
    private Rider rider;


    @OneToMany(mappedBy = "delivery")
    private List<DeliveryHistory>  deliveryHistory;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private DeliveryStatus status = DeliveryStatus.PENDING;

    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    @PrePersist
    protected void onCreate() {
        this.dateCreated = LocalDateTime.now();
    }
}
