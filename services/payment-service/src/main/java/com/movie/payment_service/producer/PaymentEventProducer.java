package com.movie.payment_service.producer;

import com.movie.common.event.PaymentCompletedEvent;
import com.movie.common.event.PaymentFailedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentEventProducer {

    private static final String PAYMENT_COMPLETED_TOPIC =
            "payment-completed";

    private static final String PAYMENT_FAILED_TOPIC =
            "payment-failed";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public CompletableFuture<SendResult<String, Object>> sendPaymentCompletedEvent(
            PaymentCompletedEvent event) {


        log.info(
                "Publishing PaymentCompletedEvent {}",
                event
        );


        kafkaTemplate.send(
                PAYMENT_COMPLETED_TOPIC,
                event.getBookingId().toString(),
                event
        );
    }

    public CompletableFuture<SendResult<String, Object>> sendPaymentFailedEvent(
            PaymentFailedEvent event) {

        log.info("Publishing PaymentFailedEvent {}", event);

        kafkaTemplate.send(
                PAYMENT_FAILED_TOPIC,
                event.getBookingId().toString(),
                event
        );
    }

}