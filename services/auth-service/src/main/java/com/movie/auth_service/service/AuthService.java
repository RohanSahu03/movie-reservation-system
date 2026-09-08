package com.movie.auth_service.service;

import com.movie.auth_service.dto.request.*;
import com.movie.auth_service.dto.response.AuthResponse;
import com.movie.auth_service.dto.response.RefreshTokenResponse;
import com.movie.auth_service.dto.response.UserResponse;

public interface AuthService {

    UserResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

    RefreshTokenResponse refreshToken(RefreshTokenRequest request);

    void logout(LogoutRequest request);

    void verifyEmail(String token);

    void forgotPassword(ForgotPasswordRequest request);

    void resetPassword(ResetPasswordRequest request);

    void resendVerificationEmail(ResendVerificationRequest request);

}