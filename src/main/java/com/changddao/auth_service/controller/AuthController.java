package com.changddao.auth_service.controller;

import com.changddao.auth_service.dto.AuthResponse;
import com.changddao.auth_service.dto.SignInRequest;
import com.changddao.auth_service.dto.SignUpRequest;
import com.changddao.auth_service.dto.response.Result;
import com.changddao.auth_service.dto.response.SingleResult;
import com.changddao.auth_service.exception.AccessDeniedException;
import com.changddao.auth_service.service.AuthService;
import com.changddao.auth_service.service.ResponseService;
import com.changddao.auth_service.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final ResponseService responseService;
    private final AuthService authService;
    private final JwtUtil jwtUtil;


    @PostMapping(value = "/signup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result signup(@ModelAttribute @Valid SignUpRequest request) {
        authService.signup(request, request.getImage());
        return responseService.handleSuccessResult();
    }


    @PostMapping("/signin")
    public SingleResult<AuthResponse> signin(@RequestBody @Valid SignInRequest request) {
        return responseService.handleSingleResult(authService.signin(request));
    }

    @GetMapping("/me")
    public SingleResult<Claims> me(@RequestHeader("Authorization") String token) {
        Claims claims = jwtUtil.parseClaims(token.replace("Bearer ", ""));
        return responseService.handleSingleResult(claims);
    }

    @PostMapping("/validate")
    public Result validate(@RequestBody String token) {
        boolean valid = jwtUtil.validateToken(token);
        return valid ? responseService.handleSuccessResult()
                : responseService.handleFailResult(401, "본인인증이 필요합니다.");
    }

    @DeleteMapping("/{id}")
    public Result deleteAccount(@PathVariable("id") Long id,
                                @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String tokenUserId = jwtUtil.getUserIdFromToken(token);
        if (!id.toString().equals(tokenUserId)) {
            throw new AccessDeniedException("본인계정만 탈퇴할 수 있습니다.");
        }
        authService.deleteAccount(id);
        return responseService.handleSuccessResult();
    }

}
