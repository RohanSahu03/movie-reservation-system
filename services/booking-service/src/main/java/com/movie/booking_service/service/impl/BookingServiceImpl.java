package com.movie.booking_service.service.impl;

import com.movie.booking_service.client.ShowClient;
import com.movie.booking_service.client.TheaterClient;
import com.movie.booking_service.dto.external.SeatResponse;
import com.movie.booking_service.dto.external.ShowResponse;
import com.movie.booking_service.dto.request.CreateBookingRequest;
import com.movie.booking_service.dto.response.BookingResponse;
import com.movie.booking_service.entity.BookedSeat;
import com.movie.booking_service.entity.Booking;
import com.movie.booking_service.enums.BookingStatus;
import com.movie.booking_service.enums.PaymentStatus;
import com.movie.booking_service.mapper.BookingMapper;
import com.movie.booking_service.repository.BookingRepository;
import com.movie.booking_service.service.BookingService;
import com.movie.booking_service.service.redis.SeatLockService;
import com.movie.common.dto.ApiResponse;
import com.movie.common.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BookingServiceImpl implements BookingService {


    private final BookingRepository bookingRepository;

    private final BookingMapper bookingMapper;

    private final ShowClient showClient;

    private final TheaterClient theaterClient;

    private final SeatLockService seatLockService;



    @Override
    public BookingResponse createBooking(
            Long userId,
            CreateBookingRequest request) {

        /*
         * 1. Validate Show
         */
        ApiResponse<ShowResponse> response =
                showClient.getShowById(request.getShowId());

        ShowResponse show = response.data();

        if (show == null || !Boolean.TRUE.equals(show.getActive())) {
            throw new ResourceNotFoundException(
                    "Show not found or inactive");
        }

        /*
         * 2. Validate Seats
         */
        List<SeatResponse> validatedSeats = new ArrayList<>();

        for (Long seatId : request.getSeatIds()) {

            ApiResponse<SeatResponse> seatResponse =
                    theaterClient.getSeatById(seatId);

            SeatResponse seat = seatResponse.data();

            if (seat == null || !Boolean.TRUE.equals(seat.getActive())) {
                throw new ResourceNotFoundException(
                        "Seat not available : " + seatId);
            }

            validatedSeats.add(seat);
        }

        /*
         * 3. Lock Seats
         */
        List<Long> lockedSeats = new ArrayList<>();

        try {

            for (Long seatId : request.getSeatIds()) {

                boolean locked = seatLockService.lockSeat(
                        request.getShowId(),
                        seatId
                );


                if (!locked) {
                    throw new IllegalStateException(
                            "Seat already locked : " + seatId
                    );
                }

                lockedSeats.add(seatId);
            }

            /*
             * 4. Create Booked Seats
             */
            List<BookedSeat> bookedSeats = new ArrayList<>();

            BigDecimal totalAmount = BigDecimal.ZERO;

            for (SeatResponse seat : validatedSeats) {

                BookedSeat bookedSeat =
                        BookedSeat.builder()
                                .seatId(seat.getId())
                                .seatNumber(seat.getSeatNumber())
                                .seatType(seat.getSeatType())
                                .price(show.getTicketPrice())
                                .build();

                bookedSeats.add(bookedSeat);

                totalAmount = totalAmount.add(show.getTicketPrice());
            }

            /*
             * 5. Create Booking
             */
            Booking booking =
                    Booking.builder()
                            .bookingNumber(generateBookingNumber())
                            .userId(userId)
                            .showId(request.getShowId())
                            .bookingStatus(BookingStatus.PENDING)
                            .paymentStatus(PaymentStatus.PENDING)
                            .totalAmount(totalAmount)
                            .bookingTime(LocalDateTime.now())
                            .expiresAt(LocalDateTime.now().plusMinutes(1))
                            .bookedSeats(new java.util.HashSet<>())
                            .active(true)
                            .build();

            bookedSeats.forEach(bookedSeat -> {
                bookedSeat.setBooking(booking);
                booking.getBookedSeats().add(bookedSeat);
            });

            Booking saved = bookingRepository.save(booking);

            log.info("Booking created successfully id={}", saved.getId());

            return bookingMapper.toResponse(saved);

        } catch (Exception e) {

            /*
             * Rollback Redis Locks
             */
            for (Long seatId : lockedSeats) {
                seatLockService.unlockSeat(
                        request.getShowId(),
                        seatId
                );
            }

            throw e;
        }
    }


    @Override
    public BookingResponse getBookingById(Long bookingId) {

        Booking booking =
                bookingRepository.findById(
                                bookingId
                        )
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Booking not found"
                                )
                        );


        return bookingMapper.toResponse(
                booking
        );
    }



    @Override
    public BookingResponse getBookingByNumber(
            String bookingNumber) {


        Booking booking =
                bookingRepository
                        .findByBookingNumber(
                                bookingNumber
                        )
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Booking not found"
                                )
                        );


        return bookingMapper.toResponse(
                booking
        );
    }



    @Override
    public List<BookingResponse> getUserBookings(
            Long userId) {


        return bookingRepository
                .findByUserIdAndActiveTrue(
                        userId
                )
                .stream()
                .map(
                        bookingMapper::toResponse
                )
                .toList();
    }




    @Override
    public void cancelBooking(Long bookingId) {


        Booking booking =
                bookingRepository.findById(
                                bookingId
                        )
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Booking not found"
                                )
                        );


        /*
         * Only pending bookings can be cancelled
         */
        if (booking.getBookingStatus() != BookingStatus.PENDING) {
            throw new IllegalStateException(
                    "Only pending bookings can be cancelled"
            );
        }



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
                BookingStatus.CANCELLED
        );


        bookingRepository.save(
                booking
        );


        log.info(
                "Booking cancelled successfully id={}",
                bookingId
        );
    }




    private String generateBookingNumber(){

        return "BKG-"
                + UUID.randomUUID()
                .toString()
                .substring(0,8)
                .toUpperCase();

    }

    @Override
    public BookingResponse confirmBooking(Long bookingId) {


        Booking booking =
                bookingRepository.findById(bookingId)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Booking not found"
                                )
                        );

        log.info(
                "Booking {} current status = {}",
                booking.getId(),
                booking.getBookingStatus()
        );

        /*
         * Booking must be pending
         */
        if(!BookingStatus.PENDING.equals(
                booking.getBookingStatus())) {


            throw new IllegalStateException(
                    "Only pending booking can be confirmed"
            );
        }



        /*
         * Confirm booking
         */
        booking.setBookingStatus(
                BookingStatus.CONFIRMED
        );


        booking.setPaymentStatus(
                PaymentStatus.SUCCESS
        );



        Booking saved =
                bookingRepository.save(
                        booking
                );


        log.info(
                "Booking confirmed successfully id={}",
                saved.getId()
        );


        return bookingMapper.toResponse(
                saved
        );
    }

}