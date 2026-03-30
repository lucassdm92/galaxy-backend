package com.galaxybck.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.galaxybck.model.entity.Client;

public class ClientResponse {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    private String email;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("address")
    private String address;

    @JsonProperty("vat")
    private String vat;

    @JsonProperty("created_by")
    private String createdBy;

    @JsonProperty("active")
    private Boolean active;

    public static ClientResponse from(Client entity) {
        ClientResponse dto = new ClientResponse();
        dto.id = entity.getId();
        dto.name = entity.getName();
        dto.phone = entity.getPhone();
        dto.address = entity.getAddress();
        dto.vat = entity.getVat();
        dto.createdBy = entity.getCreatedBy();
        dto.active = entity.getActive();
        return dto;
    }

    public Integer getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public String getVat() { return vat; }
    public String getCreatedBy() { return createdBy; }
    public Boolean getActive() { return active; }
}
