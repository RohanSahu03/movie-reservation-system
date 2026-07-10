package com.movie.movie_service.repository;

import com.movie.movie_service.entity.Movie;
import com.movie.movie_service.enums.Language;
import com.movie.movie_service.enums.MovieGenre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    Optional<Movie> findByIdAndActiveTrue(Long id);

    Page<Movie> findAllByActiveTrue(Pageable pageable);


    List<Movie> findByTitleContainingIgnoreCase(String title);

    List<Movie> findByGenreAndActiveTrue(MovieGenre genre);

    List<Movie> findByLanguageAndActiveTrue(Language language);

    List<Movie> findByActiveTrue();
}