package com.movie.booking_service.dto.external;

import com.movie.booking_service.enums.SeatType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatResponse {

    private Long id;

    private String seatNumber;

    private String seatRow;

    private Integer seatColumn;

    private SeatType seatType;

    private Boolean active;
}