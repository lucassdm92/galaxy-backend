package com.galaxybck.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.galaxybck.model.entity.Client;

public class LoginResponse {

    @JsonProperty("user_id")
    private Integer userId;

    @JsonProperty("username")
    private String username;

    @JsonProperty("token")
    private String token;

    @JsonProperty("client")
    private ClientResponse client;

    public LoginResponse(Integer userId, String username, String token, ClientResponse clientResponse) {
        this.userId = userId;
        this.username = username;
        this.token = token;
        this.client = clientResponse;

    }

    public Integer getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }
    public ClientResponse getClient() {return  client;}
    public void setUserId(Integer userId) {}
}
