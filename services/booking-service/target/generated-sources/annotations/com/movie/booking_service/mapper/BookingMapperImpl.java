package com.movie.booking_service.mapper;

import com.movie.booking_service.dto.response.BookedSeatResponse;
import com.movie.booking_service.dto.response.BookingResponse;
import com.movie.booking_service.entity.BookedSeat;
import com.movie.booking_service.entity.Booking;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-18T19:02:26+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.11 (Oracle Corporation)"
)
@Component
public class BookingMapperImpl implements BookingMapper {

    @Autowired
    private BookedSeatMapper bookedSeatMapper;

    @Override
    public BookingResponse toResponse(Booking booking) {
        if ( booking == null ) {
            return null;
        }

        BookingResponse.BookingResponseBuilder bookingResponse = BookingResponse.builder();

        bookingResponse.seats( bookedSeatSetToBookedSeatResponseList( booking.getBookedSeats() ) );
        bookingResponse.id( booking.getId() );
        bookingResponse.bookingNumber( booking.getBookingNumber() );
        bookingResponse.userId( booking.getUserId() );
        bookingResponse.showId( booking.getShowId() );
        bookingResponse.bookingStatus( booking.getBookingStatus() );
        bookingResponse.paymentStatus( booking.getPaymentStatus() );
        bookingResponse.totalAmount( booking.getTotalAmount() );
        bookingResponse.bookingTime( booking.getBookingTime() );
        bookingResponse.expiresAt( booking.getExpiresAt() );
        bookingResponse.active( booking.getActive() );
        bookingResponse.createdAt( booking.getCreatedAt() );
        bookingResponse.updatedAt( booking.getUpdatedAt() );

        return bookingResponse.build();
    }

    protected List<BookedSeatResponse> bookedSeatSetToBookedSeatResponseList(Set<BookedSeat> set) {
        if ( set == null ) {
            return null;
        }

        List<BookedSeatResponse> list = new ArrayList<BookedSeatResponse>( set.size() );
        for ( BookedSeat bookedSeat : set ) {
            list.add( bookedSeatMapper.toResponse( bookedSeat ) );
        }

        return list;
    }
}
