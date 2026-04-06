package com.galaxybck.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.galaxybck.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    @JsonProperty("username")
    private String username;

    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;

    @JsonProperty("created_by")
    private String createdBy;

    @JsonProperty("role")
    private Role role;
}
