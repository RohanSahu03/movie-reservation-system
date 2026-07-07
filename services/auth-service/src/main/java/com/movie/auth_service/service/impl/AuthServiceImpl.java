package com.movie.auth_service.service.impl;

import com.movie.auth_service.dto.request.*;
import com.movie.auth_service.dto.response.AuthResponse;
import com.movie.auth_service.dto.response.RefreshTokenResponse;
import com.movie.auth_service.dto.response.UserResponse;
import com.movie.auth_service.entity.*;
import com.movie.auth_service.enums.RoleType;
import com.movie.auth_service.exception.EmailAlreadyVerifiedException;
import com.movie.auth_service.exception.UserAlreadyExistsException;
import com.movie.auth_service.service.*;
import com.movie.common.exception.BadRequestException;
import com.movie.common.exception.ResourceNotFoundException;
import com.movie.auth_service.mapper.UserMapper;
import com.movie.auth_service.repository.RoleRepository;
import com.movie.auth_service.repository.UserRepository;
import com.movie.auth_service.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.movie.auth_service.security.JwtService;
import com.movie.auth_service.security.UserPrincipal;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final EmailVerificationTokenService emailVerificationTokenService;
    private final EmailService emailService;
    private final PasswordResetTokenService passwordResetTokenService;

    @Override
    public UserResponse register(RegisterRequest request) {

        log.info("Registration request received for email : {}", request.getEmail());

        // Step 1 : Check duplicate email
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException(
                    "User already exists with email : " + request.getEmail());
        }

        // Step 2 : Convert DTO to Entity
        User user = userMapper.toEntity(request);

        // Step 3 : Encrypt password
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Step 4 : Save user
        User savedUser = userRepository.save(user);

        // Step 5 : Fetch CUSTOMER role
        Role customerRole = roleRepository.findByName(RoleType.CUSTOMER.name())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Default CUSTOMER role not found"));

        // Step 6 : Map User and Role
        UserRole userRole = UserRole.builder()
                .user(savedUser)
                .role(customerRole)
                .build();

        userRoleRepository.save(userRole);

// Generate email verification token
        EmailVerificationToken verificationToken =
                emailVerificationTokenService.createVerificationToken(savedUser);

// Send verification email
        emailService.sendVerificationEmail(
                savedUser.getEmail(),
                savedUser.getFirstName(),
                verificationToken.getToken()
        );

        log.info("Verification email sent to {}", savedUser.getEmail());

        log.info("User registered successfully with id : {}", savedUser.getId());

        return userMapper.toResponse(savedUser);
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        log.info("Login request received for email : {}", request.getEmail());

        // Authenticate user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Fetch user from database
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with email : " + request.getEmail()));

        if (!Boolean.TRUE.equals(user.getEmailVerified())) {
            throw new BadRequestException(
                    "Please verify your email before logging in."
            );
        }

        // Generate Access Token
        UserPrincipal userPrincipal = new UserPrincipal(user);

        String accessToken = jwtService.generateAccessToken(userPrincipal);

        // Generate Refresh Token
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        // Get first role
        String role = user.getUserRoles()
                .stream()
                .findFirst()
                .map(userRole -> userRole.getRole().getName())
                .orElse("CUSTOMER");

        log.info("User logged in successfully : {}", request.getEmail());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .tokenType("Bearer")
                .expiresIn(900000L)
                .email(user.getEmail())
                .role(role)
                .build();
    }

    @Override
    public RefreshTokenResponse refreshToken(RefreshTokenRequest request) {

        log.info("Refresh token request received.");

        // Verify old token, delete it, create a new one
        RefreshToken refreshToken =
                refreshTokenService.rotateRefreshToken(request.getRefreshToken());

        User user = refreshToken.getUser();

        UserPrincipal userPrincipal = new UserPrincipal(user);

        String accessToken = jwtService.generateAccessToken(userPrincipal);

        log.info("Refresh token rotated for user: {}", user.getEmail());

        return RefreshTokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .tokenType("Bearer")
                .expiresIn(900000L)
                .build();
    }

    @Override
    public void logout(LogoutRequest request) {

        log.info("Logout request received.");

        refreshTokenService.revokeRefreshToken(
                request.getRefreshToken()
        );

        log.info("User logged out successfully.");
    }

    @Override
    public void verifyEmail(String token) {

        EmailVerificationToken verificationToken =
                emailVerificationTokenService.validateVerificationToken(token);

        User user = verificationToken.getUser();

        user.setEmailVerified(true);
        user.setEnabled(true);

        userRepository.save(user);

        emailVerificationTokenService.deleteVerificationToken(user);

        log.info("Email verified successfully for user: {}", user.getEmail());
    }

    @Override
    public void forgotPassword(ForgotPasswordRequest request) {

        log.info("Forgot password request received for email: {}", request.getEmail());

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with email: " + request.getEmail()));

        PasswordResetToken resetToken =
                passwordResetTokenService.createPasswordResetToken(user);

        emailService.sendPasswordResetEmail(
                user.getEmail(),
                user.getFirstName(),
                resetToken.getToken()
        );

        log.info("Password reset email sent to {}", user.getEmail());
    }

    @Override
    public void resetPassword(ResetPasswordRequest request) {

        log.info("Reset password request received");

        PasswordResetToken resetToken =
                passwordResetTokenService
                        .validatePasswordResetToken(request.getToken());

        User user = resetToken.getUser();

        user.setPassword(
                passwordEncoder.encode(request.getNewPassword())
        );

        userRepository.save(user);

        passwordResetTokenService.deletePasswordResetToken(user);

        log.info("Password reset successfully for {}", user.getEmail());
    }

    @Override
    public void resendVerificationEmail(ResendVerificationRequest request) {

        log.info("Resend verification email request received for {}",
                request.getEmail());

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with email: " + request.getEmail()));

        if (Boolean.TRUE.equals(user.getEmailVerified())) {
            throw new EmailAlreadyVerifiedException("Email is already verified.");
        }

        EmailVerificationToken verificationToken =
                emailVerificationTokenService.createVerificationToken(user);

        emailService.sendVerificationEmail(
                user.getEmail(),
                user.getFirstName(),
                verificationToken.getToken());

        log.info("Verification email resent to {}", user.getEmail());
    }
}