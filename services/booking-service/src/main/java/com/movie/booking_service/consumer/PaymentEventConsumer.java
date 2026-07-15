package com.movie.booking_service.consumer;

import com.movie.booking_service.service.BookingService;
import com.movie.booking_service.event.PaymentCompletedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentEventConsumer {

    private final BookingService bookingService;

    @KafkaListener(
            topics = "payment-events",
            groupId = "booking-group"
    )
    public void consumePaymentCompletedEvent(
            PaymentCompletedEvent event
    ) {

        log.info(
                "Received PaymentCompletedEvent : {}",
                event
        );

        bookingService.confirmBooking(
                event.getBookingId()
        );

        log.info(
                "Booking confirmed successfully for bookingId={}",
                event.getBookingId()
        );
    }
}