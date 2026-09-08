package com.movie.auth_service.service;

import com.movie.auth_service.entity.RefreshToken;
import com.movie.auth_service.entity.User;

public interface RefreshTokenService {

    RefreshToken createRefreshToken(User user);

    RefreshToken verifyRefreshToken(String token);

    void revokeRefreshToken(String token);

    void revokeAllUserTokens(Long userId);

    RefreshToken rotateRefreshToken(String token);
}