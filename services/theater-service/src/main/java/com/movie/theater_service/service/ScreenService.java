package com.movie.theater_service.service;

public interface ScreenService {
    ScreenResponse createScreen(CreateScreenRequest request);

    ScreenResponse updateScreen(Long id, UpdateScreenRequest request);

    ScreenResponse getScreen(Long id);

    List<ScreenResponse> getScreensByTheater(Long theaterId);

    void deleteScreen(Long id);
}
