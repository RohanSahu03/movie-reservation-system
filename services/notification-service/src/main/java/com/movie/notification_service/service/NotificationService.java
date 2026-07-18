package com.movie.notification_service.service;

import com.movie.notification_service.event.PaymentCompletedEvent;
import com.movie.notification_service.event.PaymentFailedEvent;

public interface NotificationService {

    void sendPaymentSuccessNotification(
            PaymentCompletedEvent event
    );

    void sendPaymentFailedNotification(
            PaymentFailedEvent event
    );
}