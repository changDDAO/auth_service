package com.changddao.auth_service.repository;

import com.changddao.auth_service.entity.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthUserRepository extends JpaRepository<AuthUser, Long>, AuthUserQueryRepository {

    Optional<AuthUser> findByEmail(String email);

    boolean existsByEmail(String email);
}
