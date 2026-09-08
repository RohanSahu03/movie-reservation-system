package com.movie.booking_service.client;

import com.movie.booking_service.dto.external.SeatResponse;
import com.movie.common.dto.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
        name = "THEATER-SERVICE"
)
public interface TheaterClient {


    @GetMapping("/api/v1/seats/{seatId}")
    ApiResponse<SeatResponse> getSeatById(
            @PathVariable Long seatId
    );


    @GetMapping("/api/v1/seats/screen/{screenId}")
    ApiResponse<List<SeatResponse>> getSeatsByScreen(
            @PathVariable Long screenId
    );

}