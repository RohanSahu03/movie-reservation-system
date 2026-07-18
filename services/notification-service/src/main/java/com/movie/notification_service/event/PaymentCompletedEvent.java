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
public class PaymentCompletedEvent {

    private Long paymentId;

    private Long bookingId;

    private Long userId;

    private BigDecimal amount;

    private String transactionId;

    private LocalDateTime paymentTime;
}