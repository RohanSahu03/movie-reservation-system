package com.movie.show_service.dto.request;

import com.movie.show_service.enums.ShowStatus;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateShowRequest {

    private LocalDate showDate;


    private LocalTime startTime;


    @DecimalMin("1.00")
    private BigDecimal ticketPrice;


    private ShowStatus status;

}