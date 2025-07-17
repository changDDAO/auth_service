package com.changddao.auth_service.client;

import com.changddao.auth_service.dto.CreateUserProfileRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service", url= "${USER_SERVICE_URL}")
public interface UserClient {
    @PostMapping("/internal/users")
    void createUserProfile(@RequestBody CreateUserProfileRequest request);
}
