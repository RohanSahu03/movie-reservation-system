package com.movie.booking_service.consumer;


import com.movie.booking_service.service.BookingService;
import com.movie.common.event.PaymentCompletedEvent;
import com.movie.common.event.PaymentFailedEvent;
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
            topics = "payment-completed",
            groupId = "booking-group"
    )
    public void consumeCompleted(
            PaymentCompletedEvent event
    ) {

        log.info(
                "Payment completed {}",
                event
        );

        bookingService.confirmBooking(
                event.getBookingId()
        );

    }

    @KafkaListener(
            topics = "payment-failed",
            groupId = "booking-group"
    )
    public void consumeFailed(
            PaymentFailedEvent event
    ) {

        log.info(
                "Payment failed {}",
                event
        );

        bookingService.cancelBooking(
                event.getBookingId()
        );

    }

}