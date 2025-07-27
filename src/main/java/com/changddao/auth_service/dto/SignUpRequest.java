package com.changddao.auth_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
@Setter
public class SignUpRequest {

    private Long userId;

    @NotBlank
    private String nickname;

    @Email
    private String email;

    @NotBlank
    private String name;

    @NotBlank
    @Size(min = 8, max = 100)
    private String password;

    @NotBlank
    private String phoneNumber;

    private AddressDto address;

    private MultipartFile image; // ✅ 이미지 포함

    @Getter
    @NoArgsConstructor
    @Setter
    public static class AddressDto {

        @NotBlank
        private String city;

        @NotBlank
        private String street;

        @NotBlank
        private String zipcode;
    }
}
