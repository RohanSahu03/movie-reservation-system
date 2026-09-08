package com.movie.theater_service.mapper;

import com.movie.theater_service.dto.request.CreateScreenRequest;
import com.movie.theater_service.dto.request.UpdateScreenRequest;
import com.movie.theater_service.dto.response.ScreenResponse;
import com.movie.theater_service.entity.Screen;
import com.movie.theater_service.entity.Theater;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-18T19:28:07+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.11 (Oracle Corporation)"
)
@Component
public class ScreenMapperImpl implements ScreenMapper {

    @Override
    public Screen toEntity(CreateScreenRequest request) {
        if ( request == null ) {
            return null;
        }

        Screen.ScreenBuilder screen = Screen.builder();

        screen.name( request.getName() );
        screen.screenType( request.getScreenType() );

        screen.active( true );

        return screen.build();
    }

    @Override
    public ScreenResponse toResponse(Screen screen) {
        if ( screen == null ) {
            return null;
        }

        ScreenResponse.ScreenResponseBuilder screenResponse = ScreenResponse.builder();

        screenResponse.theaterId( screenTheaterId( screen ) );
        screenResponse.id( screen.getId() );
        screenResponse.name( screen.getName() );
        screenResponse.screenType( screen.getScreenType() );
        screenResponse.capacity( screen.getCapacity() );
        screenResponse.active( screen.getActive() );

        return screenResponse.build();
    }

    @Override
    public void updateEntity(UpdateScreenRequest request, Screen screen) {
        if ( request == null ) {
            return;
        }

        if ( request.getName() != null ) {
            screen.setName( request.getName() );
        }
        if ( request.getScreenType() != null ) {
            screen.setScreenType( request.getScreenType() );
        }
    }

    private Long screenTheaterId(Screen screen) {
        Theater theater = screen.getTheater();
        if ( theater == null ) {
            return null;
        }
        return theater.getId();
    }
}
