package com.movie.movie_service.dto.request;

import com.movie.movie_service.enums.Language;
import com.movie.movie_service.enums.MovieGenre;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateMovieRequest {

    @NotBlank(message = "Movie title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Duration is required")
    private Integer duration;

    @NotNull(message = "Language is required")
    private Language language;

    @NotNull(message = "Genre is required")
    private MovieGenre genre;

    @NotNull(message = "Release date is required")
    private LocalDate releaseDate;

    @NotBlank(message = "Certificate is required")
    private String certificate;

    private String posterUrl;

    private String trailerUrl;
}