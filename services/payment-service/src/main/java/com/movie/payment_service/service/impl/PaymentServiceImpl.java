package com.movie.payment_service.service.impl;

import com.movie.common.dto.ApiResponse;
import com.movie.common.exception.ResourceNotFoundException;
import com.movie.payment_service.client.BookingClient;
import com.movie.payment_service.dto.external.BookingResponse;
import com.movie.payment_service.dto.request.CreatePaymentRequest;
import com.movie.payment_service.dto.response.PaymentResponse;
import com.movie.payment_service.entity.Payment;
import com.movie.payment_service.enums.BookingStatus;
import com.movie.payment_service.enums.PaymentStatus;
import com.movie.payment_service.mapper.PaymentMapper;
import com.movie.payment_service.repository.PaymentRepository;
import com.movie.payment_service.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    private final PaymentMapper paymentMapper;

    private final BookingClient bookingClient;

    @Override
    public PaymentResponse createPayment(
            Long userId,
            CreatePaymentRequest request) {

        /*
         * Fetch Booking
         */
        ApiResponse<BookingResponse> response =
                bookingClient.getBookingById(
                        request.getBookingId()
                );

        BookingResponse booking =
                response.data();

        if (booking == null || !Boolean.TRUE.equals(booking.getActive())) {

            throw new ResourceNotFoundException(
                    "Booking not found"
            );
        }

        /*
         * Booking should be pending
         */
        if (booking.getBookingStatus() != BookingStatus.PENDING) {

            throw new IllegalStateException(
                    "Only pending booking can be paid"
            );
        }

        /*
         * Create Payment
         */
        Payment payment =
                Payment.builder()
                        .bookingId(
                                booking.getId()
                        )
                        .userId(
                                booking.getUserId()
                        )
                        .amount(
                                booking.getTotalAmount()
                        )
                        .paymentMethod(
                                request.getPaymentMethod()
                        )
                        .paymentStatus(
                                PaymentStatus.PENDING
                        )
                        .paymentReference(
                                generatePaymentReference()
                        )
                        .build();

        Payment saved =
                paymentRepository.save(
                        payment
                );

        log.info(
                "Payment created successfully id={}",
                saved.getId()
        );

        return paymentMapper.toResponse(
                saved
        );
    }

    private String generatePaymentReference() {

        return "PAY-"
                + UUID.randomUUID()
                .toString()
                .substring(0, 8)
                .toUpperCase();
    }

    @Override
    public PaymentResponse processPayment(
            String paymentReference) {

        Payment payment =
                paymentRepository
                        .findByPaymentReference(paymentReference)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Payment not found"
                                ));


        /*
         * Only pending payments can be processed
         */
        if (payment.getPaymentStatus() != PaymentStatus.PENDING) {

            throw new IllegalStateException(
                    "Payment already processed"
            );
        }


        /*
         * Mock payment gateway
         */
        boolean paymentSuccess = mockPaymentGateway();


        if (paymentSuccess) {

            payment.setPaymentStatus(
                    PaymentStatus.SUCCESS
            );

            payment.setTransactionId(
                    generateTransactionId()
            );

        } else {

            payment.setPaymentStatus(
                    PaymentStatus.FAILED
            );

            payment.setFailureReason(
                    "Payment declined by gateway"
            );
        }


        Payment saved =
                paymentRepository.save(payment);


        log.info(
                "Payment processed reference={} status={}",
                saved.getPaymentReference(),
                saved.getPaymentStatus()
        );


        return paymentMapper.toResponse(saved);
    }

    @Override
    public PaymentResponse getPaymentById(
            Long paymentId) {

        Payment payment =
                paymentRepository.findById(paymentId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Payment not found"
                                ));

        return paymentMapper.toResponse(payment);
    }


    @Override
    public List<PaymentResponse> getUserPayments(
            Long userId) {

        return paymentRepository
                .findByUserId(userId)
                .stream()
                .map(paymentMapper::toResponse)
                .toList();
    }


    private String generateTransactionId() {

        return "TXN-"
                + UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 12)
                .toUpperCase();
    }

    private boolean mockPaymentGateway() {

        return Math.random() < 0.8;
    }
}