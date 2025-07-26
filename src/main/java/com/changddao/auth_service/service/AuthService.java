package com.changddao.auth_service.service;

import com.changddao.auth_service.client.UserClient;
import com.changddao.auth_service.dto.AuthResponse;
import com.changddao.auth_service.dto.CreateUserProfileRequest;
import com.changddao.auth_service.dto.SignInRequest;
import com.changddao.auth_service.dto.SignUpRequest;
import com.changddao.auth_service.entity.AuthUser;
import com.changddao.auth_service.entity.Role;
import com.changddao.auth_service.exception.DuplicatedEmailException;
import com.changddao.auth_service.repository.AuthUserRepository;
import com.changddao.auth_service.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserClient userClient;


    @Transactional
    public void signup(SignUpRequest req, MultipartFile image) {
        if (authUserRepository.existsByEmail(req.getEmail())) {
            throw new DuplicatedEmailException("이미 가입된 이메일입니다.");
        }
        AuthUser authUser = authUserRepository.save(AuthUser.builder()
                .email(req.getEmail())
                .nickname(req.getNickname())
                .password(passwordEncoder.encode(req.getPassword()))
                .role(Role.USER)
                .build());

        CreateUserProfileRequest profileRequest = new CreateUserProfileRequest(
                authUser.getId(),
                req.getNickname(),
                req.getName(),
                req.getPhoneNumber(),
                new CreateUserProfileRequest.AddressDto(
                        req.getAddress().getCity(),
                        req.getAddress().getStreet(),
                        req.getAddress().getZipcode()
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
