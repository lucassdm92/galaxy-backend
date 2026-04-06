package com.galaxybck.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.galaxybck.model.entity.Rider;
import com.galaxybck.model.enums.RiderType;
import com.galaxybck.model.enums.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RiderResponse {

    @JsonProperty("uniqueCode")
    private Integer uniqueCode;

    @JsonProperty("name")
    private String name;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("address")
    private String address;

    @JsonProperty("vehicle_type")
    private VehicleType vehicleType;

    @JsonProperty("vehicle_plate")
    private String vehiclePlate;

    @JsonProperty("identification_document")
    private String identificationDocument;

    @JsonProperty("drive_license")
    private String driveLicense;

    @JsonProperty("vat_number")
    private String vatNumber;

    @JsonProperty("vat")
    private String vat;

    @JsonProperty("rider_type")
    private RiderType riderType;

    @JsonProperty("email")
    private String email;

    @JsonProperty("active")
    private Boolean active;

    @JsonProperty("created_by")
    private String createdBy;

    public static RiderResponse from(Rider entity) {
        RiderResponse dto = new RiderResponse();
        dto.uniqueCode = entity.getUniqueCode();
        dto.name = entity.getName();
        dto.phone = entity.getPhone();
        dto.address = entity.getAddress();
        dto.vehicleType = entity.getVehicleType();
        dto.vehiclePlate = entity.getVehiclePlate();
        dto.identificationDocument = entity.getIdentificationDocument();
        dto.driveLicense = entity.getDriveLicense();
        dto.vatNumber = entity.getVatNumber();
        dto.vat = entity.getVat();
        dto.riderType = entity.getRiderType();
        dto.createdBy = entity.getCreatedBy();
        return dto;
    }
}
