package com.movie.api_gateway.constant;

public final class SecurityConstants {

    private SecurityConstants() {
    }

    public static final String[] PUBLIC_ENDPOINTS = {

            "/api/v1/auth/login",
            "/api/v1/auth/register",
            "/api/v1/auth/refresh-token",
            "/api/v1/auth/verify-email",
            "/api/v1/auth/resend-verification",
            "/api/v1/auth/forgot-password",
            "/api/v1/auth/reset-password",

            "/actuator/**"
    };
}