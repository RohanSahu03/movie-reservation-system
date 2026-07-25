package com.movie.show_service.mapper;

import com.movie.show_service.dto.request.CreateShowRequest;
import com.movie.show_service.dto.request.UpdateShowRequest;
import com.movie.show_service.dto.response.ShowResponse;
import com.movie.show_service.entity.Show;
import com.movie.show_service.enums.ShowStatus;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-18T19:31:15+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.11 (Oracle Corporation)"
)
@Component
public class ShowMapperImpl implements ShowMapper {

    @Override
    public Show toEntity(CreateShowRequest request) {
        if ( request == null ) {
            return null;
        }

        Show.ShowBuilder show = Show.builder();

        show.movieId( request.getMovieId() );
        show.theaterId( request.getTheaterId() );
        show.screenId( request.getScreenId() );
        show.showDate( request.getShowDate() );
        show.startTime( request.getStartTime() );
        show.ticketPrice( request.getTicketPrice() );

        show.status( ShowStatus.SCHEDULED );
        show.active( true );

        return show.build();
    }

    @Override
    public ShowResponse toResponse(Show show) {
        if ( show == null ) {
            return null;
        }

        ShowResponse.ShowResponseBuilder showResponse = ShowResponse.builder();

        showResponse.id( show.getId() );
        showResponse.movieId( show.getMovieId() );
        showResponse.theaterId( show.getTheaterId() );
        showResponse.screenId( show.getScreenId() );
        showResponse.showDate( show.getShowDate() );
        showResponse.ticketPrice( show.getTicketPrice() );
        showResponse.status( show.getStatus() );
        showResponse.active( show.getActive() );

        showResponse.startTime( show.getShowStartTime().toLocalTime() );
        showResponse.endTime( show.getShowEndTime().toLocalTime() );

        return showResponse.build();
    }

    @Override
    public void updateEntity(UpdateShowRequest request, Show show) {
        if ( request == null ) {
            return;
        }

        if ( request.getShowDate() != null ) {
            show.setShowDate( request.getShowDate() );
        }
        if ( request.getStartTime() != null ) {
            show.setStartTime( request.getStartTime() );
        }
        if ( request.getTicketPrice() != null ) {
            show.setTicketPrice( request.getTicketPrice() );
        }
        if ( request.getStatus() != null ) {
            show.setStatus( request.getStatus() );
        }
    }
}
