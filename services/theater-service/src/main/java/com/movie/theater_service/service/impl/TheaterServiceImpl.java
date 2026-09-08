package com.movie.theater_service.service.impl;

import com.movie.common.exception.ResourceNotFoundException;
import com.movie.theater_service.dto.request.CreateTheaterRequest;
import com.movie.theater_service.dto.request.UpdateTheaterRequest;
import com.movie.theater_service.dto.response.TheaterResponse;
import com.movie.theater_service.entity.Theater;
import com.movie.theater_service.mapper.TheaterMapper;
import com.movie.theater_service.repository.TheaterRepository;
import com.movie.theater_service.service.TheaterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TheaterServiceImpl implements TheaterService {

    private final TheaterRepository theaterRepository;
    private final TheaterMapper theaterMapper;

    @Override
    public TheaterResponse createTheater(CreateTheaterRequest request) {

        if (theaterRepository.existsByNameAndCityAndActiveTrue(
                request.getName(),
                request.getCity())) {

            throw new IllegalArgumentException(
                    "Theater already exists in this city.");
        }

        Theater theater = theaterMapper.toEntity(request);

        Theater saved = theaterRepository.save(theater);

        log.info("Theater created with id : {}", saved.getId());

        return theaterMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public TheaterResponse getTheaterById(Long id) {

        Theater theater = theaterRepository
                .findByIdAndActiveTrue(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Theater not found with id : " + id));

        return theaterMapper.toResponse(theater);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TheaterResponse> getAllTheaters(
            int page,
            int size,
            String sortBy,
            String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return theaterRepository
                .findByActiveTrue(pageable)
                .map(theaterMapper::toResponse);
    }

    @Override
    public TheaterResponse updateTheater(
            Long id,
            UpdateTheaterRequest request) {

        Theater theater = theaterRepository
                .findByIdAndActiveTrue(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Theater not found with id : " + id));

        theaterMapper.updateEntity(request, theater);

        Theater updated = theaterRepository.save(theater);

        log.info("Theater updated : {}", updated.getId());

        return theaterMapper.toResponse(updated);
    }

    @Override
    public void deleteTheater(Long id) {

        Theater theater = theaterRepository
                .findByIdAndActiveTrue(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Theater not found with id : " + id));

        theater.setActive(false);

        theaterRepository.save(theater);

        log.info("Theater disabled : {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TheaterResponse> searchByCity(
            String city,
            int page,
            int size) {

        Pageable pageable = PageRequest.of(page, size);

        return theaterRepository
                .findByCityIgnoreCaseAndActiveTrue(city, pageable)
                .map(theaterMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TheaterResponse> searchByName(
            String name,
            int page,
            int size) {

        Pageable pageable = PageRequest.of(page, size);

        return theaterRepository
                .findByNameContainingIgnoreCaseAndActiveTrue(
                        name,
                        pageable
                )
                .map(theaterMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TheaterResponse> getActiveTheaters(
            int page,
            int size) {

        Pageable pageable = PageRequest.of(page, size);

        return theaterRepository
                .findByActiveTrue(pageable)
                .map(theaterMapper::toResponse);
    }

    @Override
    public void changeStatus(Long id, boolean active) {

        Theater theater = theaterRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Theater not found with id : " + id));

        theater.setActive(active);

        theaterRepository.save(theater);

        log.info("Theater {} status changed to {}",
                id,
                active);
    }
}