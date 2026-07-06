package com.movie.auth_service.service.impl;

import com.movie.auth_service.dto.request.LoginRequest;
import com.movie.auth_service.dto.request.RegisterRequest;
import com.movie.auth_service.dto.response.AuthResponse;
import com.movie.auth_service.dto.response.UserResponse;
import com.movie.auth_service.entity.Role;
import com.movie.auth_service.entity.User;
import com.movie.auth_service.entity.UserRole;
import com.movie.auth_service.enums.RoleType;
import com.movie.auth_service.exception.UserAlreadyExistsException;
import com.movie.common.exception.ResourceNotFoundException;
import com.movie.auth_service.mapper.UserMapper;
import com.movie.auth_service.repository.RoleRepository;
import com.movie.auth_service.repository.UserRepository;
import com.movie.auth_service.repository.UserRoleRepository;
import com.movie.auth_service.service.AuthService;
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

        log.info("User registered successfully with id : {}", savedUser.getId());

        // Step 7 : Return response
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

        // Generate JWT
        UserPrincipal userPrincipal = new UserPrincipal(user);

        String accessToken = jwtService.generateAccessToken(userPrincipal);

        // Get first role
        String role = user.getUserRoles()
                .stream()
                .findFirst()
                .map(userRole -> userRole.getRole().getName())
                .orElse("CUSTOMER");

        log.info("User logged in successfully : {}", request.getEmail());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .tokenType("Bearer")
                .expiresIn(900000L)
                .email(user.getEmail())
                .role(role)
                .build();
    }
}