package com.changddao.auth_service.controller;

import com.changddao.auth_service.dto.AuthResponse;
import com.changddao.auth_service.dto.SignInRequest;
import com.changddao.auth_service.dto.SignUpRequest;
import com.changddao.auth_service.service.AuthService;
import com.changddao.auth_service.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;
    private final JwtUtil jwtUtil;

    @PostMapping(value = "/signup")
    public ResponseEntity<Void> signup(@RequestBody @Valid SignUpRequest request) {
        authService.signup(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(@RequestBody @Valid SignInRequest request) {
        return ResponseEntity.ok(authService.signin(request));
    }

    @GetMapping("/me")
    public ResponseEntity<Claims> me(@RequestHeader("Authorization") String token) {
        Claims claims = jwtUtil.parseClaims(token.replace("Bearer ", ""));
        return ResponseEntity.ok(claims);
    }

    @PostMapping("/validate")
    public ResponseEntity<Boolean> validate(@RequestBody String token) {
        boolean valid = jwtUtil.validateToken(token);
        return ResponseEntity.ok(valid);
    }

}
