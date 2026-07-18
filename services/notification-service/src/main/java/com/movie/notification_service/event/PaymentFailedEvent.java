package com.movie.notification_service.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentFailedEvent {

    private Long paymentId;

    private Long bookingId;

    private Long userId;

    private BigDecimal amount;

    private String reason;

    private LocalDateTime paymentTime;
}