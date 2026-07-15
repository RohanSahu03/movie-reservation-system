package com.movie.payment_service.repository;

import com.movie.payment_service.entity.Payment;
import com.movie.payment_service.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository
        extends JpaRepository<Payment, Long> {

    Optional<Payment> findByPaymentReference(
            String paymentReference
    );

    Optional<Payment> findByTransactionId(
            String transactionId
    );

    List<Payment> findByUserId(
            Long userId
    );

    List<Payment> findByBookingId(
            Long bookingId
    );

    List<Payment> findByPaymentStatus(
            PaymentStatus paymentStatus
    );
}