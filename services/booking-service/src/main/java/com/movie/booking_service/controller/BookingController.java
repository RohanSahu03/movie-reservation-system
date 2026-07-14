package com.movie.booking_service.controller;

import com.movie.booking_service.dto.request.CreateBookingRequest;
import com.movie.booking_service.dto.response.BookingResponse;
import com.movie.booking_service.service.BookingService;
import com.movie.common.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<ApiResponse<BookingResponse>> createBooking(
            @RequestHeader("X-User-Id") Long userId,
            @Valid @RequestBody CreateBookingRequest request) {

        BookingResponse response =
                bookingService.createBooking(userId, request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "Booking created successfully",
                        response
                ));
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<ApiResponse<BookingResponse>> getBookingById(
            @PathVariable Long bookingId) {

        BookingResponse response =
                bookingService.getBookingById(bookingId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Booking fetched successfully",
                        response
                ));
    }

    @GetMapping("/number/{bookingNumber}")
    public ResponseEntity<ApiResponse<BookingResponse>> getBookingByNumber(
            @PathVariable String bookingNumber) {

        BookingResponse response =
                bookingService.getBookingByNumber(bookingNumber);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Booking fetched successfully",
                        response
                ));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<BookingResponse>>> getUserBookings(
            @PathVariable Long userId) {

        List<BookingResponse> response =
                bookingService.getUserBookings(userId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Bookings fetched successfully",
                        response
                ));
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<ApiResponse<Void>> cancelBooking(
            @PathVariable Long bookingId) {

        bookingService.cancelBooking(bookingId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Booking cancelled successfully",
                        null
                )
        );
    }

    @PutMapping("/{bookingId}/confirm")
    public ResponseEntity<ApiResponse<BookingResponse>> confirmBooking(
            @PathVariable Long bookingId
    ){

        BookingResponse response =
                bookingService.confirmBooking(
                        bookingId
                );

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Booking confirmed successfully",
                        response
                )
        );
    }

}