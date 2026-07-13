package com.movie.show_service.mapper;

import com.movie.show_service.dto.request.CreateShowRequest;
import com.movie.show_service.dto.request.UpdateShowRequest;
import com.movie.show_service.dto.response.ShowResponse;
import com.movie.show_service.entity.Show;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ShowMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "showStartTime", ignore = true)
    @Mapping(target = "showEndTime", ignore = true)
    @Mapping(target = "status", constant = "SCHEDULED")
    @Mapping(target = "active", constant = "true")
    Show toEntity(CreateShowRequest request);



    @Mapping(
            target = "startTime",
            expression = "java(show.getShowStartTime().toLocalTime())"
    )
    @Mapping(
            target = "endTime",
            expression = "java(show.getShowEndTime().toLocalTime())"
    )
    ShowResponse toResponse(Show show);



    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "showEndTime", ignore = true)
    void updateEntity(
            UpdateShowRequest request,
            @MappingTarget Show show
    );
}