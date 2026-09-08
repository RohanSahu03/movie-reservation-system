package com.movie.show_service.dto.external;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TheaterResponse {

    private Long id;

    private String name;

    private Boolean active;
}