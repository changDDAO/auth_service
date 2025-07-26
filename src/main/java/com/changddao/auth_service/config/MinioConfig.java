package com.changddao.auth_service.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {
    private final String url;
    private final String accessKey;
    private final String secretKey;

    public MinioConfig(
            @Value("${MINIO_URL}") String url,
            @Value("${MINIO_USER}") String accessKey,
            @Value("${MINIO_PASSWORD}") String secretKey
    ) {
        this.url = url;
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }
    @Bean
    public MinioClient minioClient(){
        return MinioClient.builder()
                .endpoint(url)
                .credentials(accessKey, secretKey)
                .build();
    }
}
