package com.movie.booking_service.service.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
@Slf4j
public class SeatLockServiceImpl
        implements SeatLockService {

    private static final Duration LOCK_DURATION =
            Duration.ofMinutes(5);

    private final StringRedisTemplate redisTemplate;

    @Override
    public boolean lockSeat(
            Long showId,
            Long seatId) {
        log.info("Inside SeatLockService.lockSeat()");

        String key = getSeatKey(showId, seatId);

        Boolean success =
                redisTemplate.opsForValue()
                        .setIfAbsent(
                                key,
                                "LOCKED",
                                LOCK_DURATION
                        );

        log.info(
                "Redis Lock Attempt -> key={}, result={}",
                key,
                success
        );

        return Boolean.TRUE.equals(success);
    }

    @Override
    public void unlockSeat(
            Long showId,
            Long seatId) {

        redisTemplate.delete(
                getSeatKey(showId, seatId)
        );
    }

    @Override
    public boolean isSeatLocked(
            Long showId,
            Long seatId) {

        Boolean exists =
                redisTemplate.hasKey(
                        getSeatKey(showId, seatId)
                );

        return Boolean.TRUE.equals(exists);
    }

    private String getSeatKey(
            Long showId,
            Long seatId) {

        return "seat:"
                + showId
                + ":"
                + seatId;
    }

}