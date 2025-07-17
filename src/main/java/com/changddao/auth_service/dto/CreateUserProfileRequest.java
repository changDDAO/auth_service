package com.changddao.auth_service.dto;

public record CreateUserProfileRequest(
    Long userId,
    String nickname,
    String name,
    String phoneNumber,
    AddressDto address
) {
    public record AddressDto(String city, String street, String zipcode){

    }
}
