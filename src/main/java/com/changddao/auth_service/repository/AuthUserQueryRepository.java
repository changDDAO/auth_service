package com.changddao.auth_service.repository;

import com.changddao.auth_service.entity.AuthUser;

import java.util.Optional;

public interface AuthUserQueryRepository {
    Optional<AuthUser> findUserWithEmailIgnoreCase(String email);

    Optional<AuthUser> findByEmailOrNickname(String nickname, String email);
}
