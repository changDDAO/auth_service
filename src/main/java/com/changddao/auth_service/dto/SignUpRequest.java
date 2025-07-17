package com.changddao.auth_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignUpRequest(
        Long userId,
        @NotBlank
        String nickname,
        @Email
        String email,
        @NotBlank
        String name,
        @NotBlank
        @Size(min = 8, max = 100)
        String password,
        @NotBlank
        String phoneNumber,
        AddressDto address
){
    public record AddressDto(
         @NotBlank String city,
         @NotBlank String street,
         @NotBlank  String zipcode
    ){}
}
