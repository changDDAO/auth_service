package com.changddao.auth_service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;


public record CreateUserProfileRequest(
        Long userId,
        String nickname,
        String name,
        String phoneNumber,
        AddressDto address
){
    public record AddressDto(
            String city,
            String street,
            String zipcode
    ){}
}
