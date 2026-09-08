package com.movie.theater_service.mapper;

import com.movie.theater_service.dto.request.CreateScreenRequest;
import com.movie.theater_service.dto.request.UpdateScreenRequest;
import com.movie.theater_service.dto.response.ScreenResponse;
import com.movie.theater_service.entity.Screen;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ScreenMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "theater", ignore = true)
    @Mapping(target = "seats", ignore = true)
    @Mapping(target = "active", constant = "true")
    Screen toEntity(CreateScreenRequest request);

    @Mapping(source = "theater.id", target = "theaterId")
    ScreenResponse toResponse(Screen screen);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "theater", ignore = true)
    @Mapping(target = "seats", ignore = true)
    void updateEntity(UpdateScreenRequest request,
                      @MappingTarget Screen screen);
}