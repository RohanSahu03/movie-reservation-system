package com.movie.booking_service.dto.response;

import com.movie.booking_service.enums.BookingStatus;
import com.movie.booking_service.enums.PaymentStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingResponse {

    private Long id;

    private String bookingNumber;

    private Long userId;

    private Long showId;

    private BookingStatus bookingStatus;

    private PaymentStatus paymentStatus;

    private BigDecimal totalAmount;

    private LocalDateTime bookingTime;

    private LocalDateTime expiresAt;

    private Boolean active;

    private List<BookedSeatResponse> seats;

    private Instant createdAt;

    private Instant updatedAt;
}