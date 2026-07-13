package com.movie.show_service.repository;

import com.movie.show_service.entity.Show;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ShowRepository extends JpaRepository<Show, Long> {

    Page<Show> findByActiveTrue(Pageable pageable);

    List<Show> findByMovieIdAndActiveTrue(Long movieId);

    List<Show> findByTheaterIdAndActiveTrue(Long theaterId);

    List<Show> findByScreenIdAndActiveTrue(Long screenId);

    List<Show> findByShowDateAndActiveTrue(LocalDate showDate);

    List<Show> findByShowStartTimeAfterAndActiveTrue(
            LocalDateTime dateTime
    );

    boolean existsByScreenIdAndShowStartTimeLessThanAndShowEndTimeGreaterThan(
            Long screenId,
            LocalDateTime showEndTime,
            LocalDateTime showStartTime
    );
}