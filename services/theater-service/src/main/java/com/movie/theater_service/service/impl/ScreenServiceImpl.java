package com.movie.theater_service.service.impl;


import com.movie.common.exception.ResourceNotFoundException;
import com.movie.theater_service.dto.request.CreateScreenRequest;
import com.movie.theater_service.dto.request.UpdateScreenRequest;
import com.movie.theater_service.dto.response.ScreenResponse;
import com.movie.theater_service.entity.Screen;
import com.movie.theater_service.entity.Theater;
import com.movie.theater_service.mapper.ScreenMapper;
import com.movie.theater_service.repository.ScreenRepository;
import com.movie.theater_service.repository.TheaterRepository;
import com.movie.theater_service.service.ScreenService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ScreenServiceImpl implements ScreenService {


    private final ScreenRepository screenRepository;

    private final TheaterRepository theaterRepository;

    private final ScreenMapper screenMapper;



    @Override
    public ScreenResponse createScreen(
            Long theaterId,
            CreateScreenRequest request) {


        Theater theater = theaterRepository.findById(theaterId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Theater not found with id : "
                                        + theaterId));


        Screen screen = Screen.builder()
                .name(request.getName())
                .screenType(request.getScreenType())
                .capacity(0)
                .theater(theater)
                .build();


        Screen savedScreen =
                screenRepository.save(screen);


        log.info(
                "Screen created successfully. screenId={}, theaterId={}",
                savedScreen.getId(),
                theaterId
        );


        return screenMapper.toResponse(savedScreen);
    }



    @Override
    public ScreenResponse updateScreen(
            Long screenId,
            UpdateScreenRequest request) {


        Screen screen =
                screenRepository.findById(screenId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Screen not found with id : "
                                                + screenId));


        if(request.getName() != null){

            screen.setName(
                    request.getName()
            );
        }


        if(request.getScreenType() != null){

            screen.setScreenType(
                    request.getScreenType()
            );
        }


        Screen updatedScreen =
                screenRepository.save(screen);


        log.info(
                "Screen updated successfully. screenId={}",
                screenId
        );


        return screenMapper.toResponse(updatedScreen);
    }



    @Override
    @Transactional(readOnly = true)
    public ScreenResponse getScreenById(
            Long screenId) {


        Screen screen =
                screenRepository.findById(screenId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Screen not found with id : "
                                                + screenId));


        return screenMapper.toResponse(screen);
    }



    @Override
    @Transactional(readOnly = true)
    public List<ScreenResponse> getScreensByTheater(
            Long theaterId) {


        return screenRepository
                .findByTheaterIdAndActiveTrue(theaterId)
                .stream()
                .map(screenMapper::toResponse)
                .toList();

    }



    @Override
    public void deleteScreen(
            Long screenId) {


        Screen screen =
                screenRepository.findById(screenId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Screen not found with id : "
                                                + screenId));


        screen.setActive(false);


        screenRepository.save(screen);


        log.info(
                "Screen disabled successfully. screenId={}",
                screenId
        );

    }
}