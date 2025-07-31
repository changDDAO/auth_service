package com.changddao.auth_service.service;

import com.changddao.auth_service.dto.kafka.UserDeletedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaUserProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendUserDeletedEvent(UserDeletedEvent event) {
        kafkaTemplate.send("user.deleted", event);
    }
}
