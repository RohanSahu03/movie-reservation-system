package com.movie.booking_service.controller;

import com.movie.booking_service.service.redis.SeatLockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/lock")
@RequiredArgsConstructor
public class SeatLockTestController {

    private final SeatLockService seatLockService;

    @PostMapping("/{showId}/{seatId}")
    public String lockSeat(
            @PathVariable Long showId,
            @PathVariable Long seatId) {

        boolean locked =
                seatLockService.lockSeat(showId, seatId);

        return locked
                ? "Seat Locked Successfully"
                : "Seat Already Locked";
    }

    @DeleteMapping("/{showId}/{seatId}")
    public String unlockSeat(
            @PathVariable Long showId,
            @PathVariable Long seatId) {

        seatLockService.unlockSeat(showId, seatId);

        return "Seat Unlocked";
    }

    @GetMapping("/{showId}/{seatId}")
    public boolean isLocked(
            @PathVariable Long showId,
            @PathVariable Long seatId) {

        return seatLockService.isSeatLocked(showId, seatId);
    }

}