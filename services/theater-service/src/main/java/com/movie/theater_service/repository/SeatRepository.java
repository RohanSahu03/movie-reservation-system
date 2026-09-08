package com.movie.theater_service.repository;

import com.movie.theater_service.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Long> {

    List<Seat> findByScreenId(Long screenId);

    Optional<Seat> findByScreenIdAndSeatNumber(
            Long screenId,
            String seatNumber
    );

}