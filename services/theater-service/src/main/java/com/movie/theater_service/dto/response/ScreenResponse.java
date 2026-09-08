package com.movie.theater_service.dto.response;

import com.movie.theater_service.enums.ScreenType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScreenResponse {

    private Long id;

    private String name;

    private ScreenType screenType;

    private Integer capacity;

    private Long theaterId;

    private Boolean active;
}