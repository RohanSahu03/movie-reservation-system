package com.movie.auth_service.service.impl;

import com.movie.auth_service.entity.PasswordResetToken;
import com.movie.auth_service.entity.User;
import com.movie.auth_service.repository.PasswordResetTokenRepository;
import com.movie.auth_service.service.PasswordResetTokenService;
import com.movie.common.exception.InvalidTokenException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PasswordResetTokenServiceImpl
        implements PasswordResetTokenService {

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    @Value("${password-reset.expiration}")
    private long expirationMinutes;

    @Override
    public PasswordResetToken createPasswordResetToken(User user) {

        log.info("Generating password reset token for user: {}", user.getEmail());

        // Delete existing token if present
        passwordResetTokenRepository.findByUser(user)
                .ifPresent(passwordResetTokenRepository::delete);

        PasswordResetToken token = PasswordResetToken.builder()
                .token(UUID.randomUUID().toString())
                .user(user)
                .expiryDate(LocalDateTime.now().plusMinutes(expirationMinutes))
                .build();

        PasswordResetToken savedToken = passwordResetTokenRepository.save(token);

        log.info("Password reset token generated for user: {}", user.getEmail());

        return savedToken;
    }

    @Override
    @Transactional(readOnly = true)
    public PasswordResetToken validatePasswordResetToken(String token) {

        PasswordResetToken resetToken =
                passwordResetTokenRepository.findByToken(token)
                        .orElseThrow(() ->
                                new InvalidTokenException("Invalid password reset token."));

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Password reset token has expired.");
        }

        return resetToken;
    }

    @Override
    public void deletePasswordResetToken(User user) {

        passwordResetTokenRepository.deleteByUser(user);

        log.info("Password reset token deleted for user: {}", user.getEmail());
    }
}