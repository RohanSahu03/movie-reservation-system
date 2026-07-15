package com.movie.payment_service.controller;

import com.movie.common.dto.ApiResponse;
import com.movie.payment_service.dto.request.CreatePaymentRequest;
import com.movie.payment_service.dto.response.PaymentResponse;
import com.movie.payment_service.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<ApiResponse<PaymentResponse>> createPayment(
            @RequestHeader("X-User-Id") Long userId,
            @Valid @RequestBody CreatePaymentRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "Payment created successfully",
                        paymentService.createPayment(userId, request)
                ));
    }

    @PutMapping("/{paymentReference}/process")
    public ResponseEntity<ApiResponse<PaymentResponse>> processPayment(
            @PathVariable String paymentReference) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Payment processed successfully",
                        paymentService.processPayment(paymentReference)
                )
        );
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<ApiResponse<PaymentResponse>> getPayment(
            @PathVariable Long paymentId) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Payment fetched successfully",
                        paymentService.getPaymentById(paymentId)
                )
        );
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<PaymentResponse>>> getUserPayments(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Payments fetched successfully",
                        paymentService.getUserPayments(userId)
                )
        );
    }
}