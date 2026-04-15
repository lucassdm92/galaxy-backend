package com.galaxybck.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.galaxybck.model.entity.ClientEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    @JsonProperty("country_code")
    private String countryCode;

    @JsonProperty("user")
    private UserResponse user;

    public static ClientResponse from(ClientEntity entity) {
        ClientResponse dto = new ClientResponse();
        dto.name = entity.getName();
        dto.phone = entity.getPhone();
        dto.address = entity.getAddress();
        dto.vat = entity.getVat();
        dto.createdBy = entity.getCreatedBy();
        dto.active = entity.getActive();
        dto.countryCode = entity.getCountryCode();
        dto.user = UserResponse.from(entity.getUser());
        return dto;
    }
}
