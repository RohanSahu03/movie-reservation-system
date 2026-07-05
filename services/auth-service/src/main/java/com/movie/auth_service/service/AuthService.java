package com.movie.auth_service.service;

import com.movie.auth_service.dto.request.LoginRequest;
import com.movie.auth_service.dto.request.RegisterRequest;
import com.movie.auth_service.dto.response.AuthResponse;
import com.movie.auth_service.dto.response.UserResponse;

public interface AuthService {

    UserResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

}