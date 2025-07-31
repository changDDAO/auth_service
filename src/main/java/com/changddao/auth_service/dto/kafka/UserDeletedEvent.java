package com.changddao.auth_service.dto.kafka;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDeletedEvent {
    private Long userId;
    private String nickname;
    private String reason;
}
