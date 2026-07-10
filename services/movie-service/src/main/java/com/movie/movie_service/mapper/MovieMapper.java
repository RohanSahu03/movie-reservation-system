package com.movie.movie_service.mapper;

import com.movie.movie_service.dto.request.CreateMovieRequest;
import com.movie.movie_service.dto.request.UpdateMovieRequest;
import com.movie.movie_service.dto.response.MovieResponse;
import com.movie.movie_service.entity.Movie;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", constant = "true")
    Movie toEntity(CreateMovieRequest request);

    MovieResponse toResponse(Movie movie);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    void updateMovie(UpdateMovieRequest request,
                     @MappingTarget Movie movie);
}