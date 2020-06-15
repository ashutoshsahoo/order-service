package com.ashu.order.auth.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

}
