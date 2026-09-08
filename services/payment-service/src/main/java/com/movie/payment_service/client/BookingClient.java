package com.movie.payment_service.client;

import com.movie.common.dto.ApiResponse;
import com.movie.payment_service.dto.external.BookingResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "BOOKING-SERVICE"
)
public interface BookingClient {

    @GetMapping("/api/v1/bookings/{bookingId}")
    ApiResponse<BookingResponse> getBookingById(
            @PathVariable Long bookingId
    );

}