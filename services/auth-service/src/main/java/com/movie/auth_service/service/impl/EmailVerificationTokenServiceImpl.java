package com.movie.auth_service.service.impl;

import com.movie.auth_service.entity.EmailVerificationToken;
import com.movie.auth_service.entity.User;
import com.movie.auth_service.repository.EmailVerificationTokenRepository;
import com.movie.auth_service.service.EmailVerificationTokenService;
import com.movie.common.exception.InvalidTokenException;
import com.movie.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class EmailVerificationTokenServiceImpl
        implements EmailVerificationTokenService {

    private final EmailVerificationTokenRepository tokenRepository;

    private static final long TOKEN_EXPIRY_MINUTES = 30;

    @Override
    public EmailVerificationToken createVerificationToken(User user) {

        log.info("Generating verification token for user: {}", user.getEmail());

        // Remove old token if present
        tokenRepository.deleteByUser(user);

        EmailVerificationToken token = EmailVerificationToken.builder()
                .token(UUID.randomUUID().toString())
                .user(user)
                .expiryDate(LocalDateTime.now().plusMinutes(TOKEN_EXPIRY_MINUTES))
                .verified(false)
                .build();

        EmailVerificationToken savedToken = tokenRepository.save(token);

        log.info("Verification token generated successfully.");

        return savedToken;
    }

    @Override
    @Transactional(readOnly = true)
    public EmailVerificationToken validateVerificationToken(String token) {

        EmailVerificationToken verificationToken =
                tokenRepository.findByToken(token)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Verification token not found."));

        if (verificationToken.getVerified()) {
            throw new InvalidTokenException(
                    "Email has already been verified.");
        }

        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new InvalidTokenException(
                    "Verification token has expired.");
        }

        return verificationToken;
    }

    @Override
    public void deleteVerificationToken(User user) {

        tokenRepository.deleteByUser(user);

        log.info("Verification token deleted for user: {}", user.getEmail());
    }
}