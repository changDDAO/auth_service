package com.changddao.auth_service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginResponse {
    private String accessToken;
    private final String tokenType = "Bearer";

    public LoginResponse(String token) {
        this.accessToken = token;
    }
}
