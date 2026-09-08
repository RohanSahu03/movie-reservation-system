package com.movie.api_gateway.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationManager
        implements ReactiveAuthenticationManager {

    private final JwtTokenValidator jwtTokenValidator;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {

        String token = authentication.getCredentials().toString();

        try {

            if (!jwtTokenValidator.isTokenValid(token)) {
                return Mono.empty();
            }

            String email = jwtTokenValidator.extractEmail(token);

            String role = jwtTokenValidator.extractRole(token);

            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(
                            email,
                            token,
                            List.of(new SimpleGrantedAuthority(role))
                    );

            auth.setDetails(jwtTokenValidator.extractUserId(token));

            return Mono.just(auth);

        } catch (Exception ex) {

            return Mono.error(
                    new AuthenticationServiceException(ex.getMessage())
            );
        }
    }
}