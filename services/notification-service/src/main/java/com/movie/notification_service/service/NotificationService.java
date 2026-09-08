package com.movie.notification_service.service;

import com.movie.common.event.PaymentCompletedEvent;
import com.movie.common.event.PaymentFailedEvent;


public interface NotificationService {

    void sendPaymentSuccessNotification(
            PaymentCompletedEvent event
    );

    void sendPaymentFailedNotification(
            PaymentFailedEvent event
    );
}