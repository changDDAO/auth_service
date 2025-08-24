package com.changddao.auth_service.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisChecker implements CommandLineRunner {
    private final StringRedisTemplate redis;

    @Override
    public void run(String... args) {
        redis.opsForValue().set("hello", "world");
        System.out.println("✅ Redis 연결 성공: " + redis.opsForValue().get("hello"));
    }
}

