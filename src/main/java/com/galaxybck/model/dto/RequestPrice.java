package com.galaxybck.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RequestPrice(

        @JsonProperty("username") String username,

       @JsonProperty("distance_meters") Integer distanceMeters
) {
}
