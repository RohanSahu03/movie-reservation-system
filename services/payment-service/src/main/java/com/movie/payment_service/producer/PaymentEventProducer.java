package com.movie.payment_service.producer;


import com.movie.payment_service.event.PaymentCompletedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentEventProducer {


    private static final String TOPIC =
            "payment-events";


    private final KafkaTemplate<String, Object> kafkaTemplate;



    public void sendPaymentCompletedEvent(
            PaymentCompletedEvent event
    ){

        log.info(
                "Publishing payment completed event : {}",
                event
        );


        kafkaTemplate.send(
                TOPIC,
                event.getBookingId().toString(),
                event
        );


        log.info(
                "Payment event published successfully"
        );
    }

}