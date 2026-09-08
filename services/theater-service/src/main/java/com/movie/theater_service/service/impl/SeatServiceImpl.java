package com.movie.theater_service.service.impl;

import com.movie.common.exception.ResourceNotFoundException;
import com.movie.theater_service.dto.request.SeatGenerationRequest;
import com.movie.theater_service.dto.response.SeatResponse;
import com.movie.theater_service.entity.Screen;
import com.movie.theater_service.entity.Seat;
import com.movie.theater_service.enums.SeatType;
import com.movie.theater_service.mapper.SeatMapper;
import com.movie.theater_service.repository.ScreenRepository;
import com.movie.theater_service.repository.SeatRepository;
import com.movie.theater_service.service.SeatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;
    private final ScreenRepository screenRepository;
    private final SeatMapper seatMapper;

    @Override
    public void generateSeats(Long screenId,
                              SeatGenerationRequest request) {

        Screen screen = screenRepository.findById(screenId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Screen not found with id : " + screenId));

        if (!seatRepository.findByScreenId(screenId).isEmpty()) {

            throw new IllegalArgumentException(
                    "Seats already generated for this screen.");
        }

        List<Seat> seats = new ArrayList<>();

        for (int row = 0; row < request.getRows(); row++) {

            String rowName = String.valueOf((char) ('A' + row));

            for (int col = 1; col <= request.getColumns(); col++) {

                SeatType seatType = determineSeatType(
                        rowName,
                        request
                );

                Seat seat = Seat.builder()
                        .seatNumber(rowName + col)
                        .seatRow(rowName)
                        .seatColumn(col)
                        .seatType(seatType)
                        .screen(screen)
                        .active(true)
                        .build();

                seats.add(seat);
            }
        }

        seatRepository.saveAll(seats);

        screen.setCapacity(seats.size());

        screenRepository.save(screen);

        log.info("{} seats generated for screen {}",
                seats.size(),
                screenId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SeatResponse> getSeatsByScreen(Long screenId) {

        return seatRepository.findByScreenId(screenId)
                .stream()
                .map(seatMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public SeatResponse getSeatById(Long seatId) {

        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Seat not found with id : " + seatId));

        return seatMapper.toResponse(seat);
    }

    @Override
    public void deleteSeatsByScreen(Long screenId) {

        List<Seat> seats = seatRepository.findByScreenId(screenId);

        seats.forEach(seat -> seat.setActive(false));

        seatRepository.saveAll(seats);

        log.info("Seats disabled for screen {}", screenId);
    }

    private SeatType determineSeatType(
            String row,
            SeatGenerationRequest request) {

        if (request.getVipRows() != null &&
                request.getVipRows().contains(row)) {

            return SeatType.VIP;
        }

        if (request.getPremiumRows() != null &&
                request.getPremiumRows().contains(row)) {

            return SeatType.PREMIUM;
        }

        if (request.getReclinerRows() != null &&
                request.getReclinerRows().contains(row)) {

            return SeatType.RECLINER;
        }

        return SeatType.REGULAR;
    }
}