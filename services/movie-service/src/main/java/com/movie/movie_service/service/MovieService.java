package com.movie.movie_service.service;

import com.movie.movie_service.dto.request.CreateMovieRequest;
import com.movie.movie_service.dto.request.UpdateMovieRequest;
import com.movie.movie_service.dto.response.MovieResponse;
import com.movie.movie_service.enums.Language;
import com.movie.movie_service.enums.MovieGenre;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MovieService {

    MovieResponse createMovie(CreateMovieRequest request);

    MovieResponse getMovieById(Long id);

    Page<MovieResponse> getAllMovies(
            int page,
            int size,
            String sortBy,
            String direction
    );

    MovieResponse updateMovie(Long id,
                              UpdateMovieRequest request);

    void deleteMovie(Long id);

    List<MovieResponse> searchByTitle(String title);

    List<MovieResponse> getByGenre(MovieGenre genre);

    List<MovieResponse> getByLanguage(Language language);

    List<MovieResponse> getActiveMovies();

    void changeStatus(Long id, boolean active);

}