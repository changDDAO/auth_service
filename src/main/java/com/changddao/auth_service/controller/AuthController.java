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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;
    private final JwtUtil jwtUtil;

    @PostMapping(value = "/signup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> signup(@ModelAttribute @Valid SignUpRequest request) {
        log.info("nickname: {}", request.getNickname());
        log.info("city: {}", request.getAddress().getCity());

        if (request.getImage() != null) {
            log.info("image name: {}", request.getImage().getOriginalFilename());
        }

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
