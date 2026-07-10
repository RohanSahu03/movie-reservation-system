package com.movie.theater_service.service;

public interface TheaterService {
    TheaterResponse createTheater(CreateTheaterRequest request);

    TheaterResponse updateTheater(Long id, UpdateTheaterRequest request);

    TheaterResponse getTheaterById(Long id);

    Page<TheaterResponse> getAllTheaters(
            int page,
            int size,
            String sortBy,
            String direction
    );

    List<TheaterResponse> getByCity(String city);

    void deleteTheater(Long id);

    void changeStatus(Long id, boolean active);
}
