package com.galaxybck.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "GLX_CLIENT")
public class Client {

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

    @OneToMany(mappedBy = "client")
    private List<PriceCalculation> priceCalculations;

    @OneToMany(mappedBy = "client")
    private List<Delivery> deliveries;

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

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getVat() { return vat; }
    public void setVat(String vat) { this.vat = vat; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }

    public LocalDateTime getDateCreated() { return dateCreated; }
    public void setDateCreated(LocalDateTime dateCreated) { this.dateCreated = dateCreated; }

    public LocalDateTime getDateUpdated() { return dateUpdated; }
    public void setDateUpdated(LocalDateTime dateUpdated) { this.dateUpdated = dateUpdated; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public List<PriceCalculation> getPriceCalculations() { return priceCalculations; }
    public void setPriceCalculations(List<PriceCalculation> priceCalculations) { this.priceCalculations = priceCalculations; }

    public List<Delivery> getDeliveries() { return deliveries; }
    public void setDeliveries(List<Delivery> deliveries) { this.deliveries = deliveries; }
}
