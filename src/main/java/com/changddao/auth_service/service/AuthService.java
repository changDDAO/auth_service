package com.changddao.auth_service.service;

import com.changddao.auth_service.client.UserClient;
import com.changddao.auth_service.dto.AuthResponse;
import com.changddao.auth_service.dto.CreateUserProfileRequest;
import com.changddao.auth_service.dto.SignInRequest;
import com.changddao.auth_service.dto.SignUpRequest;
import com.changddao.auth_service.entity.AuthUser;
import com.changddao.auth_service.entity.Role;
import com.changddao.auth_service.repository.AuthUserRepository;
import com.changddao.auth_service.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserClient userClient;


    @Transactional
    public void signup(SignUpRequest req) {
        if (authUserRepository.existsByEmail(req.email())) {
            throw new IllegalStateException("이미 가입된 이메일입니다.");
        }
        AuthUser authUser = authUserRepository.save(AuthUser.builder()
                .email(req.email())
                .nickname(req.nickname())
                .password(passwordEncoder.encode(req.password()))
                .role(Role.USER)
                .build());

        CreateUserProfileRequest profileRequest = new CreateUserProfileRequest(
                authUser.getId(),
                req.nickname(),
                req.name(),
                req.phoneNumber(),
                new CreateUserProfileRequest.AddressDto(
                        req.address().city(),
                        req.address().street(),
                        req.address().zipcode()
                )
        );
        userClient.createUserProfile(profileRequest);
    }

    public AuthResponse signin(SignInRequest req) {
        AuthUser user = authUserRepository.findByEmailOrNickname(req.nickname(), req.email())
                .orElseThrow(() -> new RuntimeException("이메일 또는 닉네임 오류"));
        if(!passwordEncoder.matches(req.password(), user.getPassword())){
            throw new RuntimeException("이메일 또는 비밀번호 오류");
        }
        String token = jwtUtil.generateToken(user.getId().toString());
        return new AuthResponse(user.getId(), user.getEmail(),user.getNickname(), token);
    }
}
