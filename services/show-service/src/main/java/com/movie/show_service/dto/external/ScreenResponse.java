package com.movie.show_service.dto.external;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScreenResponse {

    private Long id;

    private String name;

    private Long theaterId;

    private Integer capacity;

    private Boolean active;
}