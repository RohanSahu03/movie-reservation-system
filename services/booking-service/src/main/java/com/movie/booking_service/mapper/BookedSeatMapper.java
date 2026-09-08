package com.movie.booking_service.mapper;

import com.movie.booking_service.dto.response.BookedSeatResponse;
import com.movie.booking_service.entity.BookedSeat;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookedSeatMapper {


    BookedSeatResponse toResponse(
            BookedSeat bookedSeat
    );

}