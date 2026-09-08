package com.movie.show_service.service.impl;

import com.movie.common.dto.ApiResponse;
import com.movie.common.exception.ResourceNotFoundException;
import com.movie.show_service.client.MovieClient;
import com.movie.show_service.client.TheaterClient;
import com.movie.show_service.dto.external.MovieResponse;
import com.movie.show_service.dto.external.ScreenResponse;
import com.movie.show_service.dto.external.TheaterResponse;
import com.movie.show_service.dto.request.CreateShowRequest;
import com.movie.show_service.dto.request.UpdateShowRequest;
import com.movie.show_service.dto.response.ShowResponse;
import com.movie.show_service.entity.Show;
import com.movie.show_service.mapper.ShowMapper;
import com.movie.show_service.repository.ShowRepository;
import com.movie.show_service.service.ShowService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ShowServiceImpl implements ShowService {


    private final ShowRepository showRepository;

    private final ShowMapper showMapper;

    private final MovieClient movieClient;

    private final TheaterClient theaterClient;


    @Override
    public ShowResponse createShow(CreateShowRequest request) {


        // Validate Movie

        ApiResponse<MovieResponse> movieApi =
                movieClient.getMovieById(request.getMovieId());


        MovieResponse movie = movieApi.data();


        if(movie == null ||
                !Boolean.TRUE.equals(movie.getActive())){

            throw new ResourceNotFoundException(
                    "Movie not found or inactive"
            );
        }

        // Validate Theater

        ApiResponse<TheaterResponse> theaterApi =
                theaterClient.getTheaterById(request.getTheaterId());

        log.info("d: {}", theaterApi);

        TheaterResponse theater = theaterApi.data();

        log.info("Theater Data: {}", theater);



        if(theater == null ||
                !Boolean.TRUE.equals(theater.getActive())){


            throw new ResourceNotFoundException(
                    "Theater not found or inactive"
            );
        }


        // Validate Screen

        ApiResponse<ScreenResponse> screenApi =
                theaterClient.getScreenById(
                        request.getScreenId()
                );


        ScreenResponse screen =
                screenApi.data();



        if(screen == null ||
                !Boolean.TRUE.equals(screen.getActive())){


            throw new ResourceNotFoundException(
                    "Screen not found or inactive"
            );
        }




        // Screen belongs to Theater validation

        if(!screen.getTheaterId()
                .equals(request.getTheaterId())){


            throw new IllegalArgumentException(
                    "Screen does not belong to theater"
            );
        }

        // Calculate show timings

        LocalDateTime showStartTime =
                LocalDateTime.of(
                        request.getShowDate(),
                        request.getStartTime()
                );


        LocalDateTime showEndTime =
                showStartTime.plusMinutes(
                        movie.getDuration()
                );


        // Check overlapping shows

        boolean exists =
                showRepository
                        .existsByScreenIdAndShowStartTimeLessThanAndShowEndTimeGreaterThan(
                                request.getScreenId(),
                                showEndTime,
                                showStartTime
                        );


        if(exists){

            throw new IllegalArgumentException(
                    "Another show already exists during this time"
            );
        }

        // Save show

        Show show =
                showMapper.toEntity(request);


        show.setShowStartTime(showStartTime);

        show.setShowEndTime(showEndTime);


        Show saved =
                showRepository.save(show);



        log.info(
                "Show created successfully id={}",
                saved.getId()
        );


        return showMapper.toResponse(saved);

    }


    @Override
    @Transactional(readOnly = true)
    public ShowResponse getShowById(Long id) {


        Show show =
                showRepository.findById(id)
                        .filter(Show::getActive)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Show not found with id : "+id
                                ));



        return showMapper.toResponse(show);

    }


    @Override
    @Transactional(readOnly = true)
    public Page<ShowResponse> getAllShows(
            int page,
            int size,
            String sortBy,
            String direction) {


        Sort sort =
                direction.equalsIgnoreCase("desc")
                        ?
                        Sort.by(sortBy).descending()
                        :
                        Sort.by(sortBy).ascending();



        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        sort
                );



        return showRepository
                .findByActiveTrue(pageable)
                .map(showMapper::toResponse);

    }


    @Override
    public ShowResponse updateShow(
            Long id,
            UpdateShowRequest request) {


        Show show =
                showRepository.findById(id)
                        .filter(Show::getActive)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Show not found with id : "+id
                                ));



        showMapper.updateEntity(
                request,
                show
        );

        // update start and end time if date/time changes

        if(request.getShowDate()!=null ||
                request.getStartTime()!=null){


            LocalDate date =
                    request.getShowDate()!=null
                            ?
                            request.getShowDate()
                            :
                            show.getShowDate();

            LocalTime time =
                    request.getStartTime()!=null
                            ?
                            request.getStartTime()
                            :
                            show.getStartTime();



            MovieResponse movie =
                    movieClient
                            .getMovieById(show.getMovieId())
                            .data();



            LocalDateTime start =
                    LocalDateTime.of(
                            date,
                            time
                    );


            show.setShowStartTime(start);
            show.setShowEndTime(
                    start.plusMinutes(
                            movie.getDuration()
                    )
            );
        }

        Show updated =
                showRepository.save(show);

        log.info(
                "Show updated successfully id={}",
                updated.getId()
        );

        return showMapper.toResponse(updated);

    }


    @Override
    public void deleteShow(Long id) {
        Show show =
                showRepository.findById(id)
                        .filter(Show::getActive)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Show not found with id : "+id
                                ));

        show.setActive(false);

        showRepository.save(show);

        log.info(
                "Show disabled id={}",
                id
        );

    }

    @Override
    @Transactional(readOnly = true)
    public List<ShowResponse> getShowsByMovie(Long movieId) {


        return showRepository
                .findByMovieIdAndActiveTrue(movieId)
                .stream()
                .map(showMapper::toResponse)
                .toList();

    }

    @Override
    @Transactional(readOnly = true)
    public List<ShowResponse> getShowsByTheater(Long theaterId) {

        return showRepository
                .findByTheaterIdAndActiveTrue(theaterId)
                .stream()
                .map(showMapper::toResponse)
                .toList();

    }

    @Override
    @Transactional(readOnly = true)
    public List<ShowResponse> getShowsByScreen(Long screenId) {

        return showRepository
                .findByScreenIdAndActiveTrue(screenId)
                .stream()
                .map(showMapper::toResponse)
                .toList();

    }

    @Override
    @Transactional(readOnly = true)
    public List<ShowResponse> getShowsByDate(
            LocalDate showDate) {

        return showRepository
                .findByShowDateAndActiveTrue(showDate)
                .stream()
                .map(showMapper::toResponse)
                .toList();

    }

    @Override
    @Transactional(readOnly = true)
    public List<ShowResponse> getUpcomingShows() {


        return showRepository
                .findByShowStartTimeAfterAndActiveTrue(
                        LocalDateTime.now()
                )
                .stream()
                .map(showMapper::toResponse)
                .toList();

    }







    @Override
    public void changeStatus(
            Long id,
            boolean active) {


        Show show =
                showRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Show not found with id : "+id
                                ));

        show.setActive(active);

        showRepository.save(show);

        log.info(
                "Show {} status changed to {}",
                id,
                active
        );

    }

}