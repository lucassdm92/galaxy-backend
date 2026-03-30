package com.galaxybck.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.galaxybck.model.entity.User;

public class UserResponse {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("username")
    private String username;

    @JsonProperty("client_id")
    private Integer clientId;

    @JsonProperty("active")
    private Boolean active;

    public static UserResponse from(User entity) {
        UserResponse dto = new UserResponse();
        dto.id = entity.getId();
        dto.username = entity.getUsername();
        dto.active = entity.getActive();
        return dto;
    }

    public Integer getId() { return id; }
    public String getUsername() { return username; }
    public Integer getClientId() { return clientId; }
    public Boolean getActive() { return active; }
}
