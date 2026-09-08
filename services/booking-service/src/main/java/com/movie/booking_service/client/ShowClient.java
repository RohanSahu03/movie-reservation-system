package com.movie.booking_service.client;

import com.movie.booking_service.dto.external.ShowResponse;
import com.movie.common.dto.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "SHOW-SERVICE"
)
public interface ShowClient {


    @GetMapping("/api/v1/shows/{id}")
    ApiResponse<ShowResponse> getShowById(
            @PathVariable Long id
    );

}