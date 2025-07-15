package com.changddao.auth_service.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueryDslConfig {
    @PersistenceContext
    EntityManager em;
    private JPAQueryFactory queryFactory(){
        return new JPAQueryFactory(em);
    }
}
