package com.movie.theater_service.service;

public interface SeatService {
    void generateSeats(Long screenId,
                       SeatGenerationRequest request);

    List<SeatResponse> getSeats(Long screenId);

    void deleteSeats(Long screenId);
}
