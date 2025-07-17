package com.changddao.auth_service.dto;

public record SignInRequest(
        String email,
        String password
) {}
