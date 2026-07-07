package com.movie.auth_service.controller;

import com.movie.auth_service.dto.request.*;
import com.movie.auth_service.dto.response.AuthResponse;
import com.movie.auth_service.dto.response.RefreshTokenResponse;
import com.movie.auth_service.dto.response.UserResponse;
import com.movie.auth_service.service.AuthService;
import com.movie.common.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> register(
            @Valid @RequestBody RegisterRequest request) {

        UserResponse response = authService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "User registered successfully",
                        response
                ));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(
            @Valid @RequestBody LoginRequest request) {

        AuthResponse response = authService.login(request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Login successful",
                        response
                )
        );
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse<RefreshTokenResponse>> refreshToken(
            @Valid @RequestBody RefreshTokenRequest request) {

        RefreshTokenResponse response = authService.refreshToken(request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Access token refreshed successfully",
                        response
                )
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(
            @Valid @RequestBody LogoutRequest request) {

        authService.logout(request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Logged out successfully",
                        null
                )
        );
    }

    @GetMapping("/verify-email")
    public ResponseEntity<ApiResponse<String>> verifyEmail(
            @RequestParam String token) {

        authService.verifyEmail(token);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Email verified successfully.",
                        null
                )
        );
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse<String>> forgotPassword(
            @Valid @RequestBody ForgotPasswordRequest request) {

        authService.forgotPassword(request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Password reset email sent successfully.",
                        null
                )
        );
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse<String>> resetPassword(
            @Valid @RequestBody ResetPasswordRequest request) {

        authService.resetPassword(request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Password reset successful.",
                        null
                )
        );
    }

    @PostMapping("/resend-verification")
    public ResponseEntity<ApiResponse<String>> resendVerificationEmail(
            @Valid @RequestBody ResendVerificationRequest request) {

        authService.resendVerificationEmail(request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Verification email sent successfully.",
                        null
                )
        );
    }
}