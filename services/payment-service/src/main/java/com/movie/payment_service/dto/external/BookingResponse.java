package com.movie.payment_service.dto.external;



import com.movie.payment_service.enums.BookingStatus;
import com.movie.payment_service.enums.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BookingResponse {

    private Long id;

    private Long userId;

    private BigDecimal totalAmount;

    private BookingStatus bookingStatus;

    private Boolean active;
}