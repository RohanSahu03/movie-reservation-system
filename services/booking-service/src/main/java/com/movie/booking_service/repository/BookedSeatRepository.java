package com.movie.booking_service.repository;

import com.movie.booking_service.entity.BookedSeat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookedSeatRepository
        extends JpaRepository<BookedSeat, Long> {


    List<BookedSeat> findByBookingId(Long bookingId);


    List<BookedSeat> findBySeatIdIn(
            List<Long> seatIds
    );


    boolean existsBySeatIdAndBooking_ShowId(
            Long seatId,
            Long showId
    );
}