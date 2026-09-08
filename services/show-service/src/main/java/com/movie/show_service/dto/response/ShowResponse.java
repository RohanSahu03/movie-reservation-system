package com.movie.show_service.dto.response;

import com.movie.show_service.enums.ShowStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShowResponse {

    private Long id;

    private Long movieId;

    private Long theaterId;

    private Long screenId;

    private LocalDate showDate;

    private LocalTime startTime;

    private LocalTime endTime;

    private BigDecimal ticketPrice;

    private ShowStatus status;

    private Boolean active;

}