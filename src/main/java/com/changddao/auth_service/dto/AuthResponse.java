package com.changddao.auth_service.dto;

public record AuthResponse(
    Long userId,
    String email,
    String token
) {}
