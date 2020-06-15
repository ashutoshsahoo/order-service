package com.ashu.order.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class LoginResponse {

    private String token;

    private String type = "Bearer";

    private String username;

    private List<String> roles;

    public LoginResponse(String token, String username, List<String> roles) {
        super();
        this.token = token;
        this.username = username;
        this.roles = roles;
    }

}
