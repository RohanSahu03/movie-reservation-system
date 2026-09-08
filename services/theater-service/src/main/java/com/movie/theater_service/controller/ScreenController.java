package com.movie.theater_service.controller;

import com.movie.common.dto.ApiResponse;
import com.movie.theater_service.dto.request.CreateScreenRequest;
import com.movie.theater_service.dto.request.UpdateScreenRequest;
import com.movie.theater_service.dto.response.ScreenResponse;
import com.movie.theater_service.service.ScreenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/screens")
@RequiredArgsConstructor
public class ScreenController {

    private final ScreenService screenService;

    @PostMapping
    public ResponseEntity<ScreenResponse> createScreen(
            @Valid @RequestBody CreateScreenRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(screenService.createScreen(
                        request.getTheaterId(),
                        request
                ));
    }

    @PutMapping("/{screenId}")
    public ResponseEntity<ScreenResponse> updateScreen(
            @PathVariable Long screenId,
            @Valid @RequestBody UpdateScreenRequest request) {

        return ResponseEntity.ok(
                screenService.updateScreen(screenId, request)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ScreenResponse>> getScreenById(
            @PathVariable Long id) {

        ScreenResponse response =
                screenService.getScreenById(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Screen fetched successfully",
                        response
                )
        );
    }

    @GetMapping("/theater/{theaterId}")
    public ResponseEntity<List<ScreenResponse>> getScreensByTheater(
            @PathVariable Long theaterId) {

        return ResponseEntity.ok(
                screenService.getScreensByTheater(theaterId)
        );
    }

    @DeleteMapping("/{screenId}")
    public ResponseEntity<Void> deleteScreen(
            @PathVariable Long screenId) {

        screenService.deleteScreen(screenId);

        return ResponseEntity.noContent().build();
    }
}