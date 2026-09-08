package com.movie.auth_service.repository;

import com.movie.auth_service.entity.PasswordResetToken;
import com.movie.auth_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository
        extends JpaRepository<PasswordResetToken, Long> {

    /**
     * Find reset token by token string.
     */
    Optional<PasswordResetToken> findByToken(String token);

    /**
     * Find existing reset token of a user.
     */
    Optional<PasswordResetToken> findByUser(User user);

    /**
     * Delete reset token after password reset.
     */
    void deleteByUser(User user);
}