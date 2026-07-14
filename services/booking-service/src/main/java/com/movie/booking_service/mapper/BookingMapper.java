package com.movie.booking_service.mapper;

import com.movie.booking_service.dto.response.BookingResponse;
import com.movie.booking_service.entity.Booking;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        uses = BookedSeatMapper.class
)
public interface BookingMapper {


    @Mapping(
            target = "seats",
            source = "bookedSeats"
    )
    BookingResponse toResponse(Booking booking);


}