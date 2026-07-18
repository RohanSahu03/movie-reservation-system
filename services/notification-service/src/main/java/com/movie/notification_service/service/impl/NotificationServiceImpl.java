package com.movie.notification_service.service.impl;

import com.movie.notification_service.event.PaymentCompletedEvent;
import com.movie.notification_service.event.PaymentFailedEvent;
import com.movie.notification_service.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationServiceImpl
        implements NotificationService {

    @Override
    public void sendPaymentSuccessNotification(
            PaymentCompletedEvent event
    ) {

        log.info("""
                
                =====================================
                PAYMENT SUCCESS NOTIFICATION
                =====================================
                
                Booking Id   : {}
                User Id      : {}
                Amount       : {}
                Transaction  : {}
                
                Email sent successfully.
                
                =====================================
                """,
                event.getBookingId(),
                event.getUserId(),
                event.getAmount(),
                event.getTransactionId()
        );
    }

    @Override
    public void sendPaymentFailedNotification(
            PaymentFailedEvent event
    ) {

        log.info("""
                
                =====================================
                PAYMENT FAILED NOTIFICATION
                =====================================
                
                Booking Id : {}
                User Id    : {}
                Amount     : {}
                Reason     : {}
                
                Failure email sent.
                
                =====================================
                """,
                event.getBookingId(),
                event.getUserId(),
                event.getAmount(),
                event.getReason()
        );
    }
}