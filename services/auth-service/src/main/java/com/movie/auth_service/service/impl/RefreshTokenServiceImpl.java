package com.movie.auth_service.service.impl;

import com.movie.auth_service.entity.RefreshToken;
import com.movie.auth_service.entity.User;
import com.movie.auth_service.repository.RefreshTokenRepository;
import com.movie.auth_service.service.RefreshTokenService;
import com.movie.common.exception.InvalidTokenException;
import com.movie.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.refresh-token-expiration}")
    private Long refreshTokenExpiration;

    @Override
    public RefreshToken createRefreshToken(User user) {

        RefreshToken refreshToken = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(refreshTokenExpiration))
                .user(user)
                .build();

        RefreshToken savedToken = refreshTokenRepository.save(refreshToken);

        log.info("Refresh token generated for user: {}", user.getEmail());

        return savedToken;
    }

    @Override
    public RefreshToken verifyRefreshToken(String token) {

        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Refresh token not found"));

        if (refreshToken.getRevoked()) {
            throw new InvalidTokenException("Refresh token has been revoked.");
        }

        if (refreshToken.getExpiryDate().isBefore(Instant.now())) {

            refreshTokenRepository.delete(refreshToken);

            throw new InvalidTokenException("Refresh token has expired.");
        }

        return refreshToken;
    }

    @Override
    public void revokeRefreshToken(String token) {

        RefreshToken refreshToken = verifyRefreshToken(token);

        refreshTokenRepository.delete(refreshToken);

        log.info("Refresh token revoked.");
    }

    @Override
    public void revokeAllUserTokens(Long userId) {

        refreshTokenRepository.deleteByUserId(userId);

        log.info("All refresh tokens revoked for userId: {}", userId);
    }

    @Override
    public RefreshToken rotateRefreshToken(String token) {

        RefreshToken oldToken = verifyRefreshToken(token);

        User user = oldToken.getUser();

        refreshTokenRepository.delete(oldToken);

        log.info("Old refresh token deleted for {}", user.getEmail());

        return createRefreshToken(user);
    }
}