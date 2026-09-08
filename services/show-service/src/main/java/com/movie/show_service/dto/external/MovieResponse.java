package com.movie.show_service.dto.external;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieResponse {

    private Long id;

    private String title;

    private Integer duration;

    private Boolean active;
}