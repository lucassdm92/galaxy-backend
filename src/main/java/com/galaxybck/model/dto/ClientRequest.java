package com.galaxybck.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ClientRequest {

    @JsonProperty("name")
    private String name;
    @JsonProperty("phone")
    private String phone;

    @JsonProperty("address")
    private String address;

    @JsonProperty("vat")
    private String vat;

    @JsonProperty("user_id")
    private Integer userId;

    @JsonProperty("created_by")
    private String createdBy;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getVat() { return vat; }
    public void setVat(String vat) { this.vat = vat; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
}
