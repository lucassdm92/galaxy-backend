package com.galaxybck.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
public record ClientRequest(
        @JsonProperty("name") String name,
        @JsonProperty("phone") String phone,
        @JsonProperty("address") String address,
        @JsonProperty("vat") String vat,
        @JsonProperty("user_id") Integer userId,
        @JsonProperty("created_by") String createdBy
) {}