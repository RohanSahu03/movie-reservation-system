package com.movie.show_service.service;

import com.movie.show_service.dto.request.CreateShowRequest;
import com.movie.show_service.dto.request.UpdateShowRequest;
import com.movie.show_service.dto.response.ShowResponse;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface ShowService {

    ShowResponse createShow(CreateShowRequest request);

    ShowResponse getShowById(Long id);

    Page<ShowResponse> getAllShows(
            int page,
            int size,
            String sortBy,
            String direction
    );

    ShowResponse updateShow(
            Long id,
            UpdateShowRequest request
    );

    void deleteShow(Long id);

    List<ShowResponse> getShowsByMovie(Long movieId);

    List<ShowResponse> getShowsByTheater(Long theaterId);

    List<ShowResponse> getShowsByScreen(Long screenId);

    List<ShowResponse> getShowsByDate(LocalDate showDate);

    List<ShowResponse> getUpcomingShows();

    void changeStatus(Long id, boolean active);
}