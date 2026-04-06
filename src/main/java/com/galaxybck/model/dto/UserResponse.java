package com.galaxybck.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.galaxybck.model.entity.User;
import com.galaxybck.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    @JsonProperty("username")
    private String username;
    @JsonProperty("active")
    private Boolean active;
    @JsonProperty("role")
    private Role role;

    public static UserResponse from(User entity) {
        UserResponse dto = new UserResponse();
        dto.username = entity.getUsername();
        dto.active = entity.getActive();
        dto.role = entity.getRole();
        return dto;
    }
}
