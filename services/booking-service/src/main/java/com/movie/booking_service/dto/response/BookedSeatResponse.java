package com.movie.booking_service.dto.response;

import com.movie.booking_service.enums.SeatType;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookedSeatResponse {

    private Long id;

    private Long seatId;

    private String seatNumber;

    private SeatType seatType;

    private BigDecimal price;
}