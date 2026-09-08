package com.movie.theater_service.service;

import com.movie.theater_service.dto.request.CreateScreenRequest;
import com.movie.theater_service.dto.request.UpdateScreenRequest;
import com.movie.theater_service.dto.response.ScreenResponse;

import java.util.List;

public interface ScreenService {

    ScreenResponse createScreen(
            Long theaterId,
            CreateScreenRequest request
    );


    ScreenResponse updateScreen(
            Long screenId,
            UpdateScreenRequest request
    );


    ScreenResponse getScreenById(
            Long screenId
    );


    List<ScreenResponse> getScreensByTheater(
            Long theaterId
    );


    void deleteScreen(
            Long screenId
    );
}