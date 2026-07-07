package com.movie.auth_service.service;

import com.movie.auth_service.entity.PasswordResetToken;
import com.movie.auth_service.entity.User;

public interface PasswordResetTokenService {

    /**
     * Generate and save a password reset token.
     */
    PasswordResetToken createPasswordResetToken(User user);

    /**
     * Validate a password reset token.
     */
    PasswordResetToken validatePasswordResetToken(String token);

    /**
     * Delete a user's password reset token.
     */
    void deletePasswordResetToken(User user);
}