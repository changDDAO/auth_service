package com.changddao.auth_service.service;

import com.changddao.auth_service.dto.SignUpRequest;
import com.changddao.auth_service.repository.AuthUserRepository;
import com.changddao.auth_service.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthUserService {
    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    @Transactional
    public void signup(SignUpRequest request) {

    }
}
