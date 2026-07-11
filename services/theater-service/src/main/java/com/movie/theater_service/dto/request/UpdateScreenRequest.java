package com.movie.theater_service.dto.request;

import com.movie.theater_service.enums.ScreenType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateScreenRequest {

    @NotBlank
    private String name;

    @NotNull
    private ScreenType screenType;

}