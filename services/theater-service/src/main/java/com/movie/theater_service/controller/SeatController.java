package com.movie.theater_service.controller;

import com.movie.theater_service.dto.request.SeatGenerationRequest;
import com.movie.theater_service.dto.response.SeatResponse;
import com.movie.theater_service.service.SeatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/seats")
@RequiredArgsConstructor
public class SeatController {

    private final SeatService seatService;

    @PostMapping("/generate/{screenId}")
    public ResponseEntity<String> generateSeats(
            @PathVariable Long screenId,
            @Valid @RequestBody SeatGenerationRequest request) {

        seatService.generateSeats(screenId, request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Seats generated successfully.");
    }

    @GetMapping("/screen/{screenId}")
    public ResponseEntity<List<SeatResponse>> getSeatsByScreen(
            @PathVariable Long screenId) {

        return ResponseEntity.ok(
                seatService.getSeatsByScreen(screenId)
        );
    }

    @GetMapping("/{seatId}")
    public ResponseEntity<SeatResponse> getSeatById(
            @PathVariable Long seatId) {

        return ResponseEntity.ok(
                seatService.getSeatById(seatId)
        );
    }

    @DeleteMapping("/screen/{screenId}")
    public ResponseEntity<Void> deleteSeatsByScreen(
            @PathVariable Long screenId) {

        seatService.deleteSeatsByScreen(screenId);

        return ResponseEntity.noContent().build();
    }
}