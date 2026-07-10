package com.movie.movie_service.controller;

import com.movie.common.dto.ApiResponse;
import com.movie.movie_service.dto.request.CreateMovieRequest;
import com.movie.movie_service.dto.request.UpdateMovieRequest;
import com.movie.movie_service.dto.response.MovieResponse;
import com.movie.movie_service.enums.Language;
import com.movie.movie_service.enums.MovieGenre;
import com.movie.movie_service.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @PostMapping
    public ResponseEntity<ApiResponse<MovieResponse>> createMovie(
            @Valid @RequestBody CreateMovieRequest request) {

        MovieResponse response = movieService.createMovie(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "Movie created successfully",
                        response
                ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MovieResponse>> getMovieById(
            @PathVariable Long id) {

        MovieResponse response = movieService.getMovieById(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Movie fetched successfully",
                        response
                )
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<MovieResponse>>> getAllMovies(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(defaultValue = "title")
            String sortBy,

            @RequestParam(defaultValue = "asc")
            String direction) {

        Page<MovieResponse> response =
                movieService.getAllMovies(page, size, sortBy, direction);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Movies fetched successfully",
                        response
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<MovieResponse>> updateMovie(
            @PathVariable Long id,
            @Valid @RequestBody UpdateMovieRequest request) {

        MovieResponse response =
                movieService.updateMovie(id, request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Movie updated successfully",
                        response
                )
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMovie(@PathVariable Long id) {

        movieService.deleteMovie(id);
    }

    @GetMapping("/search")
    public ResponseEntity<List<MovieResponse>> searchMovie(
            @RequestParam String title) {

        return ResponseEntity.ok(
                movieService.searchByTitle(title));
    }

    @GetMapping("/genre/{genre}")
    public ResponseEntity<List<MovieResponse>> getByGenre(
            @PathVariable MovieGenre genre) {

        return ResponseEntity.ok(
                movieService.getByGenre(genre));
    }

    @GetMapping("/language/{language}")
    public ResponseEntity<List<MovieResponse>> getByLanguage(
            @PathVariable Language language) {

        return ResponseEntity.ok(
                movieService.getByLanguage(language));
    }

    @GetMapping("/active")
    public ResponseEntity<List<MovieResponse>> getActiveMovies() {

        return ResponseEntity.ok(
                movieService.getActiveMovies());
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> changeStatus(
            @PathVariable Long id,
            @RequestParam boolean active) {

        movieService.changeStatus(id, active);

        return ResponseEntity.ok().build();
    }
}