package com.movie.payment_service.dto.response;

import com.movie.payment_service.enums.PaymentMethod;
import com.movie.payment_service.enums.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PaymentResponse {

    private Long id;

    private Long bookingId;

    private Long userId;

    private String paymentReference;

    private String transactionId;

    private BigDecimal amount;

    private PaymentMethod paymentMethod;

    private PaymentStatus paymentStatus;

    private String failureReason;
}