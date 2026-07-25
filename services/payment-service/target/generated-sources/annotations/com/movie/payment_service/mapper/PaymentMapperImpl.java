package com.movie.payment_service.mapper;

import com.movie.payment_service.dto.response.PaymentResponse;
import com.movie.payment_service.entity.Payment;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-18T19:02:43+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.11 (Oracle Corporation)"
)
@Component
public class PaymentMapperImpl implements PaymentMapper {

    @Override
    public PaymentResponse toResponse(Payment payment) {
        if ( payment == null ) {
            return null;
        }

        PaymentResponse paymentResponse = new PaymentResponse();

        paymentResponse.setId( payment.getId() );
        paymentResponse.setBookingId( payment.getBookingId() );
        paymentResponse.setUserId( payment.getUserId() );
        paymentResponse.setPaymentReference( payment.getPaymentReference() );
        paymentResponse.setTransactionId( payment.getTransactionId() );
        paymentResponse.setAmount( payment.getAmount() );
        paymentResponse.setPaymentMethod( payment.getPaymentMethod() );
        paymentResponse.setPaymentStatus( payment.getPaymentStatus() );
        paymentResponse.setFailureReason( payment.getFailureReason() );

        return paymentResponse;
    }
}
