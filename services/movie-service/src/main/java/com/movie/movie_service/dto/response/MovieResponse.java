package com.movie.movie_service.dto.response;

import com.movie.movie_service.enums.Language;
import com.movie.movie_service.enums.MovieGenre;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieResponse {

    private Long id;

    private String title;

    private String description;

    private Integer duration;

    private Language language;

    private MovieGenre genre;

    private LocalDate releaseDate;

    private String certificate;

    private String posterUrl;

    private String trailerUrl;

    private Boolean active;

    private Instant createdAt;

    private Instant updatedAt;
}