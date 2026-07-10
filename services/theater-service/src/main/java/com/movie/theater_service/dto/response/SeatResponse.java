package com.movie.theater_service.dto.response;

import com.movie.theater_service.enums.SeatType;
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

    private Long screenId;

    private Boolean active;
}