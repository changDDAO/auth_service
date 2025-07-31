package com.changddao.auth_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;

public class KafkaProducerConfig {
    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        return new DefaultKafkaProducerFactory<>(new HashMap<>());
    }

    @Bean
    public KafkaTemplate<String,Object> kafkaTemplate(){
        return new KafkaTemplate<>(producerFactory());
    }
}
