package com.movie.auth_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class RefreshTokenResponse {

    private String accessToken;

    private String tokenType;

    private Long expiresIn;

    private String refreshToken;
}