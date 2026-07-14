package com.movie.booking_service.service;

import com.movie.booking_service.dto.request.CreateBookingRequest;
import com.movie.booking_service.dto.response.BookingResponse;

import java.util.List;

public interface BookingService {


    BookingResponse createBooking(
            Long userId,
            CreateBookingRequest request
    );


    BookingResponse getBookingById(
            Long bookingId
    );


    BookingResponse getBookingByNumber(
            String bookingNumber
    );


    List<BookingResponse> getUserBookings(
            Long userId
    );


    void cancelBooking(
            Long bookingId
    );

}