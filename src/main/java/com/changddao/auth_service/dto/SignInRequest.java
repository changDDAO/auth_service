package com.changddao.auth_service.dto;

public record SignInRequest(
        String nickname,
        String email,
        String password
) {}
