package com.movie.booking_service.mapper;

import com.movie.booking_service.dto.response.BookedSeatResponse;
import com.movie.booking_service.entity.BookedSeat;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-18T19:02:26+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.11 (Oracle Corporation)"
)
@Component
public class BookedSeatMapperImpl implements BookedSeatMapper {

    @Override
    public BookedSeatResponse toResponse(BookedSeat bookedSeat) {
        if ( bookedSeat == null ) {
            return null;
        }

        BookedSeatResponse.BookedSeatResponseBuilder bookedSeatResponse = BookedSeatResponse.builder();

        bookedSeatResponse.id( bookedSeat.getId() );
        bookedSeatResponse.seatId( bookedSeat.getSeatId() );
        bookedSeatResponse.seatNumber( bookedSeat.getSeatNumber() );
        bookedSeatResponse.seatType( bookedSeat.getSeatType() );
        bookedSeatResponse.price( bookedSeat.getPrice() );

        return bookedSeatResponse.build();
    }
}
