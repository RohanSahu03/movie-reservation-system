package com.movie.booking_service.scheduler;

import com.movie.booking_service.entity.Booking;
import com.movie.booking_service.enums.BookingStatus;
import com.movie.booking_service.repository.BookingRepository;
import com.movie.booking_service.service.redis.SeatLockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class BookingExpirationScheduler {


    private final BookingRepository bookingRepository;

    private final SeatLockService seatLockService;


    @Scheduled(fixedDelay = 60000)
    @Transactional
    public void expireBookings() {


        log.info("Checking expired bookings...");


        List<Booking> expiredBookings =
                bookingRepository
                        .findByBookingStatusAndExpiresAtBefore(
                                BookingStatus.PENDING,
                                LocalDateTime.now()
                        );


        if(expiredBookings.isEmpty()) {

            log.info("No expired bookings found");

            return;
        }



        for(Booking booking : expiredBookings) {


            log.info(
                    "Expiring booking id={}",
                    booking.getId()
            );


            /*
             * Release Redis seat locks
             */
            booking.getBookedSeats()
                    .forEach(bookedSeat -> {


                        seatLockService.unlockSeat(
                                booking.getShowId(),
                                bookedSeat.getSeatId()
                        );


                        log.info(
                                "Released seat lock showId={} seatId={}",
                                booking.getShowId(),
                                bookedSeat.getSeatId()
                        );

                    });



            /*
             * Update booking status
             */
            booking.setBookingStatus(
                    BookingStatus.EXPIRED
            );


            bookingRepository.save(booking);



            log.info(
                    "Booking expired successfully id={}",
                    booking.getId()
            );
        }
    }
}