package com.movie.theater_service.mapper;

import com.movie.theater_service.dto.response.SeatResponse;
import com.movie.theater_service.entity.Screen;
import com.movie.theater_service.entity.Seat;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-18T19:28:06+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.11 (Oracle Corporation)"
)
@Component
public class SeatMapperImpl implements SeatMapper {

    @Override
    public SeatResponse toResponse(Seat seat) {
        if ( seat == null ) {
            return null;
        }

        SeatResponse.SeatResponseBuilder seatResponse = SeatResponse.builder();

        seatResponse.screenId( seatScreenId( seat ) );
        seatResponse.id( seat.getId() );
        seatResponse.seatNumber( seat.getSeatNumber() );
        seatResponse.seatRow( seat.getSeatRow() );
        seatResponse.seatColumn( seat.getSeatColumn() );
        seatResponse.seatType( seat.getSeatType() );
        seatResponse.active( seat.getActive() );

        return seatResponse.build();
    }

    private Long seatScreenId(Seat seat) {
        Screen screen = seat.getScreen();
        if ( screen == null ) {
            return null;
        }
        return screen.getId();
    }
}
