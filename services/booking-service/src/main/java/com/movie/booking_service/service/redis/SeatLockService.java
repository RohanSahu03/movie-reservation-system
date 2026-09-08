package com.movie.booking_service.service.redis;

public interface SeatLockService {

    boolean lockSeat(
            Long showId,
            Long seatId
    );

    void unlockSeat(
            Long showId,
            Long seatId
    );

    boolean isSeatLocked(
            Long showId,
            Long seatId
    );

}