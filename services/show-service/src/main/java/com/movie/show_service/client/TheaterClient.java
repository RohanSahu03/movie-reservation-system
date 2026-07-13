package com.movie.show_service.client;

import com.movie.common.dto.ApiResponse;
import com.movie.show_service.dto.external.ScreenResponse;
import com.movie.show_service.dto.external.TheaterResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "THEATER-SERVICE")
public interface TheaterClient {

    @GetMapping("/api/v1/theaters/{id}")
    ApiResponse<TheaterResponse> getTheaterById(
            @PathVariable Long id
    );

    @GetMapping("/api/v1/screens/{id}")
    ApiResponse<ScreenResponse> getScreenById(
            @PathVariable Long id
    );
}