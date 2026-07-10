package com.movie.movie_service.dto.request;

import com.movie.movie_service.enums.Language;
import com.movie.movie_service.enums.MovieGenre;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateMovieRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotNull
    @Min(1)
    private Integer duration;

    @NotNull
    private MovieGenre genre;

    @NotNull
    private Language language;

    @NotNull
    private LocalDate releaseDate;

    @NotBlank
    private String certificate;

    private String posterUrl;

    private String trailerUrl;
}