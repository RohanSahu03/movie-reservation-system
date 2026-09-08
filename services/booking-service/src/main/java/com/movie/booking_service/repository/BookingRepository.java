package com.movie.booking_service.repository;

import com.movie.booking_service.entity.Booking;
import com.movie.booking_service.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {


    Optional<Booking> findByBookingNumber(String bookingNumber);


    List<Booking> findByUserIdAndActiveTrue(Long userId);


    List<Booking> findByShowIdAndActiveTrue(Long showId);


    List<Booking> findByBookingStatus(
            BookingStatus bookingStatus
    );


    boolean existsByBookingNumber(String bookingNumber);

    List<Booking> findByBookingStatusAndExpiresAtBefore(
            BookingStatus bookingStatus,
            LocalDateTime expiresAt
    );
}