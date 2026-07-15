package com.movie.payment_service.dto.request;

import com.movie.payment_service.enums.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePaymentRequest {

    @NotNull
    private Long bookingId;

    @NotNull
    private PaymentMethod paymentMethod;
}