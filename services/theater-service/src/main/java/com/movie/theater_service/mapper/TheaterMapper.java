package com.movie.theater_service.mapper;

import com.movie.theater_service.dto.request.CreateTheaterRequest;
import com.movie.theater_service.dto.request.UpdateTheaterRequest;
import com.movie.theater_service.dto.response.TheaterResponse;
import com.movie.theater_service.entity.Theater;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface TheaterMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "screens", ignore = true)
    Theater toEntity(CreateTheaterRequest request);

    TheaterResponse toResponse(Theater theater);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "screens", ignore = true)
    void updateEntity(UpdateTheaterRequest request,
                      @MappingTarget Theater theater);
}