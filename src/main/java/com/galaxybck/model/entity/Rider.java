package com.galaxybck.model.entity;

import com.galaxybck.model.enums.RiderStatus;
import com.galaxybck.model.enums.RiderType;
import com.galaxybck.model.enums.VehicleType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "GLX_RIDER")
public class Rider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "unique_code", nullable = false, unique = true)
    private Integer uniqueCode;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "address", length = 255)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_type", nullable = false)
    private VehicleType vehicleType;

    @Column(name = "vehicle_plate", length = 20)
    private String vehiclePlate;

    @Column(name = "identification_document", nullable = false, length = 50)
    private String identificationDocument;

    @Column(name = "drive_license", nullable = false, length = 50)
    private String driveLicense;

    @Column(name = "vat_number", length = 20)
    private String vatNumber;

    @Column(name = "vat", length = 20)
    private String vat;

    @Enumerated(EnumType.STRING)
    @Column(name = "rider_type", nullable = false)
    private RiderType riderType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private RiderStatus active = RiderStatus.ACTIVE;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;

    @Column(name = "created_by", length = 100)
    private String createdBy;

    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    @Column(name = "date_updated")
    private LocalDateTime dateUpdated;

    @PrePersist
    protected void onCreate() {
        this.dateCreated = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.dateUpdated = LocalDateTime.now();
    }
}
