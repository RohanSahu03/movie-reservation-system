package com.movie.auth_service.service;

import com.movie.auth_service.entity.EmailVerificationToken;
import com.movie.auth_service.entity.User;

public interface EmailVerificationTokenService {

    /**
     * Generate and save a new verification token.
     */
    EmailVerificationToken createVerificationToken(User user);

    /**
     * Validate verification token.
     */
    EmailVerificationToken validateVerificationToken(String token);

    /**
     * Remove verification token after successful verification.
     */
    void deleteVerificationToken(User user);
}
