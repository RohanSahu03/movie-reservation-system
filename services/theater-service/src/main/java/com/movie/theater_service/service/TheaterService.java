package com.movie.theater_service.service;

import com.movie.theater_service.dto.request.CreateTheaterRequest;
import com.movie.theater_service.dto.request.UpdateTheaterRequest;
import com.movie.theater_service.dto.response.TheaterResponse;
import org.springframework.data.domain.Page;

public interface TheaterService {

    TheaterResponse createTheater(CreateTheaterRequest request);

    TheaterResponse getTheaterById(Long id);

    Page<TheaterResponse> getAllTheaters(
            int page,
            int size,
            String sortBy,
            String direction
    );

    TheaterResponse updateTheater(
            Long id,
            UpdateTheaterRequest request
    );

    void deleteTheater(Long id);

    Page<TheaterResponse> searchByCity(
            String city,
            int page,
            int size
    );

    Page<TheaterResponse> searchByName(
            String name,
            int page,
            int size
    );

    Page<TheaterResponse> getActiveTheaters(
            int page,
            int size
    );

    void changeStatus(Long id, boolean active);
}