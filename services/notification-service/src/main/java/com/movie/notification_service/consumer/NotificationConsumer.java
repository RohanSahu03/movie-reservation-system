package com.movie.notification_service.consumer;


import com.movie.common.event.PaymentCompletedEvent;
import com.movie.common.event.PaymentFailedEvent;
import com.movie.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final NotificationService notificationService;

    @KafkaListener(
            topics = "payment-completed",
            groupId = "notification-group"
    )
    public void consumePaymentCompleted(
            PaymentCompletedEvent event
    ) {

        log.info(
                "Received PaymentCompletedEvent : {}",
                event
        );

        notificationService
                .sendPaymentSuccessNotification(
                        event
                );
    }

    @KafkaListener(
            topics = "payment-failed",
            groupId = "notification-group"
    )
    public void consumePaymentFailed(
            PaymentFailedEvent event
    ) {

        log.info(
                "Received PaymentFailedEvent : {}",
                event
        );

        notificationService
                .sendPaymentFailedNotification(
                        event
                );
    }
}