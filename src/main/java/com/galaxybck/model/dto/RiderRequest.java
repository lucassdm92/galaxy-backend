package com.galaxybck.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.galaxybck.model.enums.RiderType;
import com.galaxybck.model.enums.VehicleType;

public record RiderRequest(
        @JsonProperty("name") String name,
        @JsonProperty("phone") String phone,
        @JsonProperty("address") String address,
        @JsonProperty("vehicle_type") VehicleType vehicleType,
        @JsonProperty("vehicle_plate") String vehiclePlate,
        @JsonProperty("identification_document") String identificationDocument,
        @JsonProperty("drive_license") String driveLicense,
        @JsonProperty("vat_number") String vatNumber,
        @JsonProperty("vat") String vat,
        @JsonProperty("rider_type") RiderType riderType,
        @JsonProperty("email") String email,
        @JsonProperty("password") String password,
        @JsonProperty("created_by") String createdBy,
        @JsonProperty("user_id") Integer userId
) {}
