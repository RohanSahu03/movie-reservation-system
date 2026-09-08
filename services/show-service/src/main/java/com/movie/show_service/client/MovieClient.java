package com.movie.show_service.client;

import com.movie.common.dto.ApiResponse;
import com.movie.show_service.dto.external.MovieResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "MOVIE-SERVICE")
public interface MovieClient {

    @GetMapping("/api/v1/movies/{id}")
    ApiResponse<MovieResponse> getMovieById(
            @PathVariable Long id
    );
}