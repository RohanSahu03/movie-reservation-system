package com.movie.common.event;

import lombok.*;

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