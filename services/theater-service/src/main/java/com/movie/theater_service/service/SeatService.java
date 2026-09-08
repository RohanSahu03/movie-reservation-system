package com.movie.theater_service.service;

import com.movie.theater_service.dto.request.SeatGenerationRequest;
import com.movie.theater_service.dto.response.SeatResponse;

import java.util.List;

public interface SeatService {

    void generateSeats(
            Long screenId,
            SeatGenerationRequest request
    );

    List<SeatResponse> getSeatsByScreen(Long screenId);

    SeatResponse getSeatById(Long seatId);

    void deleteSeatsByScreen(Long screenId);

}