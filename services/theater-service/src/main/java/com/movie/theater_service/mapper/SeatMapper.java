package com.movie.theater_service.mapper;

import com.movie.theater_service.dto.response.SeatResponse;
import com.movie.theater_service.entity.Seat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SeatMapper {

    @Mapping(source = "screen.id", target = "screenId")
    SeatResponse toResponse(Seat seat);
}