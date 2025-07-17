package com.changddao.auth_service.service;

import com.changddao.auth_service.client.UserClient;
import com.changddao.auth_service.dto.AuthResponse;
import com.changddao.auth_service.dto.SignInRequest;
import com.changddao.auth_service.dto.SignUpRequest;
import com.changddao.auth_service.entity.AuthUser;
import com.changddao.auth_service.entity.Role;
import com.changddao.auth_service.repository.AuthUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class AuthServiceTest {
    @Autowired
    private AuthService authService;

    @Autowired
    private AuthUserRepository authUserRepository;

    @MockBean
    private UserClient userClient;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Test
    @DisplayName("회원가입 정상동작")
    void signTest(){
    //given
        SignUpRequest request = new SignUpRequest(
                null,
                "닉네임",
                "test@example.com",
                "12345678",
                "홍길동",
                "010-1111-2222",
                new SignUpRequest.AddressDto("서울", "강남구", "12345")
        );
    //when
        authService.signup(request);
    //then
        AuthUser user = authUserRepository.findByEmail("test@example.com").orElseThrow();
        assertThat(user.getEmail()).isEqualTo("test@example.com");
        assertThat(passwordEncoder.matches("12345678", user.getPassword()));

        //UserClient 호출 여부 검증
        verify(userClient,times(1)).createUserProfile(any());
    }

    @Test
    @DisplayName("로그인 성공")
    void loginTest(){
    //given
        AuthUser saved = authUserRepository.save(AuthUser.builder()
                .email("signin@example.com")
                .password(passwordEncoder.encode("pass1234"))
                .role(Role.USER)
                .build());
    //when
        SignInRequest request = new SignInRequest("changddao","signin@example.com", "pass1234");
        AuthResponse response = authService.signin(request);
        //then
        assertThat(response.email()).isEqualTo("signin@example.com");
        assertThat(response.token()).isNotBlank();
    }
}