package com.movie.common.event;

import lombok.*;

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