package com.galaxybck.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record RequestPrice(
        @JsonProperty("origin") String origin,
        @JsonProperty("destination") String destination,
        @JsonProperty("date") LocalDateTime date,
        @JsonProperty("username") String username
) {
}
