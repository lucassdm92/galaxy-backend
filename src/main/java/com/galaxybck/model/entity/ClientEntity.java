package com.galaxybck.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "GLX_CLIENT")
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAME", nullable = false, length = 100)
    private String name;

    @Column(name = "PHONE", length = 20)
    private String phone;

    @Column(name = "ADDRESS", length = 255)
    private String address;

    @Column(name = "country_code", length = 4)
    private String countryCode;


    @Column(name = "VAT", length = 20)
    private String vat;

    @Column(name = "CREATED_BY", length = 100)
    private String createdBy;

    @Column(name = "ACTIVE")
    private Boolean active = true;

    @Column(name = "DATE_CREATED")
    private LocalDateTime dateCreated;

    @Column(name = "DATE_UPDATED")
    private LocalDateTime dateUpdated;

    @OneToOne
    @JoinColumn(name = "USER_ID", unique = true, nullable = false)
    private User user;

    @OneToMany(mappedBy = "clientEntity")
    private List<PriceCalculation> priceCalculations;

    @OneToMany(mappedBy = "clientEntity")
    private List<Delivery> deliveries;

    @PrePersist
    protected void onCreate() {
        this.dateCreated = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.dateUpdated = LocalDateTime.now();
    }
}
