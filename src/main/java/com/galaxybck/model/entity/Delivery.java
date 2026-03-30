package com.galaxybck.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "delivery")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "origin", nullable = false, length = 255)
    private String origin;

    @Column(name = "destination", nullable = false, length = 255)
    private String destination;

    @Column(name = "customer_name", nullable = false, length = 100)
    private String customerName;

    @Column(name = "customer_phone", nullable = false, length = 20)
    private String customerPhone;

    @Column(name = "customer_note", length = 500)
    private String customerNote;

    @OneToOne
    @JoinColumn(name = "price_calculation_id", nullable = false)
    private PriceCalculation priceCalculation;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    @PrePersist
    protected void onCreate() {
        this.dateCreated = LocalDateTime.now();
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

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

    public PriceCalculation getPriceCalculation() { return priceCalculation; }
    public void setPriceCalculation(PriceCalculation priceCalculation) { this.priceCalculation = priceCalculation; }

    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }

    public LocalDateTime getDateCreated() { return dateCreated; }
    public void setDateCreated(LocalDateTime dateCreated) { this.dateCreated = dateCreated; }
}
