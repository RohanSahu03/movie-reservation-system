package com.movie.payment_service.producer;

import com.movie.payment_service.event.PaymentCompletedEvent;
import com.movie.payment_service.event.PaymentFailedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentEventProducer {

    private static final String PAYMENT_COMPLETED_TOPIC =
            "payment-completed";

    private static final String PAYMENT_FAILED_TOPIC =
            "payment-failed";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendPaymentCompletedEvent(
            PaymentCompletedEvent event) {

        log.info("Publishing PaymentCompletedEvent {}", event);

        kafkaTemplate.send(
                PAYMENT_COMPLETED_TOPIC,
                event.getBookingId().toString(),
                event
        );
    }

    public void sendPaymentFailedEvent(
            PaymentFailedEvent event) {

        log.info("Publishing PaymentFailedEvent {}", event);

        kafkaTemplate.send(
                PAYMENT_FAILED_TOPIC,
                event.getBookingId().toString(),
                event
        ).whenComplete((result, ex) -> {

            if (ex == null) {

                log.info(
                        "PaymentFailedEvent sent successfully topic={} partition={} offset={}",
                        result.getRecordMetadata().topic(),
                        result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset()
                );

            } else {

                log.error(
                        "Failed to publish PaymentFailedEvent",
                        ex
                );
            }
        });
    }

}