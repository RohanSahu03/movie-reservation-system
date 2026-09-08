package com.movie.movie_service.service.impl;

import com.movie.common.exception.ResourceNotFoundException;
import com.movie.movie_service.dto.request.CreateMovieRequest;
import com.movie.movie_service.dto.request.UpdateMovieRequest;
import com.movie.movie_service.dto.response.MovieResponse;
import com.movie.movie_service.entity.Movie;
import com.movie.movie_service.enums.Language;
import com.movie.movie_service.enums.MovieGenre;
import com.movie.movie_service.mapper.MovieMapper;
import com.movie.movie_service.repository.MovieRepository;
import com.movie.movie_service.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    @Override
    public MovieResponse createMovie(CreateMovieRequest request) {

        log.info("Creating movie: {}", request.getTitle());

        Movie movie = movieMapper.toEntity(request);

        Movie savedMovie = movieRepository.save(movie);

        log.info("Movie created successfully with id: {}", savedMovie.getId());

        return movieMapper.toResponse(savedMovie);
    }

    @Override
    public MovieResponse getMovieById(Long id) {

        Movie movie = movieRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Movie not found with id: " + id));

        return movieMapper.toResponse(movie);
    }

    @Override
    public Page<MovieResponse> getAllMovies(
            int page,
            int size,
            String sortBy,
            String direction) {

        log.info("Fetching movies page {}, size {}", page, size);

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return movieRepository.findAllByActiveTrue(pageable)
                .map(movieMapper::toResponse);
    }

    @Override
    public MovieResponse updateMovie(Long id,
                                     UpdateMovieRequest request) {

        log.info("Updating movie {}", id);

        Movie movie = movieRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Movie not found with id : " + id));

        movieMapper.updateMovie(request, movie);

        movie = movieRepository.save(movie);

        log.info("Movie {} updated successfully", id);

        return movieMapper.toResponse(movie);
    }

    @Override
    @Transactional
    public void deleteMovie(Long id) {

        Movie movie = movieRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Movie not found"));

        movieRepository.delete(movie);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovieResponse> searchByTitle(String title) {

        return movieRepository
                .findByTitleContainingIgnoreCase(title)
                .stream()
                .map(movieMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovieResponse> getByGenre(MovieGenre genre) {

        return movieRepository
                .findByGenreAndActiveTrue(genre)
                .stream()
                .map(movieMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovieResponse> getByLanguage(Language language) {

        return movieRepository
                .findByLanguageAndActiveTrue(language)
                .stream()
                .map(movieMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovieResponse> getActiveMovies() {

        return movieRepository
                .findByActiveTrue()
                .stream()
                .map(movieMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public void changeStatus(Long id, boolean active) {

        Movie movie = movieRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Movie not found"));

        movie.setActive(active);

        movieRepository.save(movie);
    }
}