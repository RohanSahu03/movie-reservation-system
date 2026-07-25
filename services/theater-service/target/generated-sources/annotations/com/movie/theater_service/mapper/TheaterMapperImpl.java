package com.movie.theater_service.mapper;

import com.movie.theater_service.dto.request.CreateTheaterRequest;
import com.movie.theater_service.dto.request.UpdateTheaterRequest;
import com.movie.theater_service.dto.response.TheaterResponse;
import com.movie.theater_service.entity.Theater;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-18T19:28:07+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.11 (Oracle Corporation)"
)
@Component
public class TheaterMapperImpl implements TheaterMapper {

    @Override
    public Theater toEntity(CreateTheaterRequest request) {
        if ( request == null ) {
            return null;
        }

        Theater.TheaterBuilder theater = Theater.builder();

        theater.name( request.getName() );
        theater.ownerName( request.getOwnerName() );
        theater.email( request.getEmail() );
        theater.phoneNumber( request.getPhoneNumber() );
        theater.address( request.getAddress() );
        theater.city( request.getCity() );
        theater.state( request.getState() );
        theater.country( request.getCountry() );
        theater.zipCode( request.getZipCode() );

        theater.active( true );

        return theater.build();
    }

    @Override
    public TheaterResponse toResponse(Theater theater) {
        if ( theater == null ) {
            return null;
        }

        TheaterResponse.TheaterResponseBuilder theaterResponse = TheaterResponse.builder();

        theaterResponse.id( theater.getId() );
        theaterResponse.name( theater.getName() );
        theaterResponse.ownerName( theater.getOwnerName() );
        theaterResponse.email( theater.getEmail() );
        theaterResponse.phoneNumber( theater.getPhoneNumber() );
        theaterResponse.address( theater.getAddress() );
        theaterResponse.city( theater.getCity() );
        theaterResponse.state( theater.getState() );
        theaterResponse.country( theater.getCountry() );
        theaterResponse.zipCode( theater.getZipCode() );
        theaterResponse.active( theater.getActive() );

        return theaterResponse.build();
    }

    @Override
    public void updateEntity(UpdateTheaterRequest request, Theater theater) {
        if ( request == null ) {
            return;
        }

        if ( request.getName() != null ) {
            theater.setName( request.getName() );
        }
        if ( request.getOwnerName() != null ) {
            theater.setOwnerName( request.getOwnerName() );
        }
        if ( request.getEmail() != null ) {
            theater.setEmail( request.getEmail() );
        }
        if ( request.getPhoneNumber() != null ) {
            theater.setPhoneNumber( request.getPhoneNumber() );
        }
        if ( request.getAddress() != null ) {
            theater.setAddress( request.getAddress() );
        }
        if ( request.getCity() != null ) {
            theater.setCity( request.getCity() );
        }
        if ( request.getState() != null ) {
            theater.setState( request.getState() );
        }
        if ( request.getCountry() != null ) {
            theater.setCountry( request.getCountry() );
        }
        if ( request.getZipCode() != null ) {
            theater.setZipCode( request.getZipCode() );
        }
    }
}
