package com.movie.booking_service.client;

import com.movie.booking_service.dto.external.UserResponse;
import com.movie.common.dto.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "AUTH-SERVICE"
)
public interface AuthClient {


    @GetMapping("/api/v1/users/{id}")
    ApiResponse<UserResponse> getUserById(
            @PathVariable Long id
    );

}