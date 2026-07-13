package com.movie.show_service.controller;

import com.movie.common.dto.ApiResponse;
import com.movie.show_service.dto.request.CreateShowRequest;
import com.movie.show_service.dto.request.UpdateShowRequest;
import com.movie.show_service.dto.response.ShowResponse;
import com.movie.show_service.service.ShowService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/shows")
@RequiredArgsConstructor
public class ShowController {

    private final ShowService showService;

    @PostMapping
    public ResponseEntity<ApiResponse<ShowResponse>> createShow(
            @Valid @RequestBody CreateShowRequest request) {

        ShowResponse response = showService.createShow(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "Show created successfully",
                        response
                ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ShowResponse>> getShowById(
            @PathVariable Long id) {

        ShowResponse response = showService.getShowById(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Show fetched successfully",
                        response
                )
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<ShowResponse>>> getAllShows(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(defaultValue = "showStartTime")
            String sortBy,

            @RequestParam(defaultValue = "asc")
            String direction) {

        Page<ShowResponse> response =
                showService.getAllShows(
                        page,
                        size,
                        sortBy,
                        direction
                );

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Shows fetched successfully",
                        response
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ShowResponse>> updateShow(
            @PathVariable Long id,
            @Valid @RequestBody UpdateShowRequest request) {

        ShowResponse response =
                showService.updateShow(id, request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Show updated successfully",
                        response
                )
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteShow(@PathVariable Long id) {

        showService.deleteShow(id);
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<ShowResponse>> getShowsByMovie(
            @PathVariable Long movieId) {

        return ResponseEntity.ok(
                showService.getShowsByMovie(movieId)
        );
    }

    @GetMapping("/theater/{theaterId}")
    public ResponseEntity<List<ShowResponse>> getShowsByTheater(
            @PathVariable Long theaterId) {

        return ResponseEntity.ok(
                showService.getShowsByTheater(theaterId)
        );
    }

    @GetMapping("/screen/{screenId}")
    public ResponseEntity<List<ShowResponse>> getShowsByScreen(
            @PathVariable Long screenId) {

        return ResponseEntity.ok(
                showService.getShowsByScreen(screenId)
        );
    }

    @GetMapping("/date/{showDate}")
    public ResponseEntity<List<ShowResponse>> getShowsByDate(
            @PathVariable LocalDate showDate) {

        return ResponseEntity.ok(
                showService.getShowsByDate(showDate)
        );
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<ShowResponse>> getUpcomingShows() {

        return ResponseEntity.ok(
                showService.getUpcomingShows()
        );
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> changeStatus(
            @PathVariable Long id,
            @RequestParam boolean active) {

        showService.changeStatus(id, active);

        return ResponseEntity.ok().build();
    }
}