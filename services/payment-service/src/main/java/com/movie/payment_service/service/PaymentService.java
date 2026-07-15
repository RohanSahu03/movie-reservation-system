package com.movie.payment_service.service;

import com.movie.payment_service.dto.request.CreatePaymentRequest;
import com.movie.payment_service.dto.response.PaymentResponse;

import java.util.List;

public interface PaymentService {

    PaymentResponse createPayment(
            Long userId,
            CreatePaymentRequest request
    );

    PaymentResponse processPayment(
            String paymentReference
    );

    PaymentResponse getPaymentById(
            Long paymentId
    );

    List<PaymentResponse> getUserPayments(
            Long userId
    );

}