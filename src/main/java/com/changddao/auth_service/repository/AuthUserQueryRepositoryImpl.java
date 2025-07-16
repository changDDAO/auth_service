package com.changddao.auth_service.repository;

import com.changddao.auth_service.entity.AuthUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.changddao.auth_service.entity.QAuthUser.authUser;

@RequiredArgsConstructor
public class AuthUserQueryRepositoryImpl implements AuthUserQueryRepository{

    private final JPAQueryFactory queryFactory;
    @Override
    public Optional<AuthUser> findUserWithEmailIgnoreCase(String email) {
        return Optional.ofNullable(
                queryFactory.selectFrom(authUser)
                        .where(authUser.email.equalsIgnoreCase(email))
                        .fetchOne()
        );

    }
}
