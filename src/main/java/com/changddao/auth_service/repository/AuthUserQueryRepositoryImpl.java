package com.changddao.auth_service.repository;

import com.changddao.auth_service.entity.AuthUser;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
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

    @Override
    public Optional<AuthUser> findByEmailOrNickname(String nickname, String email) {
        return Optional.ofNullable(queryFactory.selectFrom(authUser)
                .where(userSearchPredicate(nickname, email))
                .fetchOne());
    }

    private BooleanBuilder userSearchPredicate(String nickname, String email) {
        return new BooleanBuilder()
                .and(nicknameEq(nickname))
                .and(emailEq(email));
    }

    private BooleanExpression nicknameEq(String nickname) {
        return nickname != null ? authUser.nickname.eq(nickname) : null;
    }
    private BooleanExpression emailEq(String email) {
        return email != null ? authUser.email.eq(email) : null;
    }

}
