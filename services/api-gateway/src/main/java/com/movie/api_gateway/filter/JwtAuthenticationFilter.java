package com.movie.api_gateway.filter;

import com.movie.api_gateway.constant.SecurityConstants;
import com.movie.api_gateway.security.JwtTokenValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter implements WebFilter {

    private final JwtTokenValidator jwtTokenValidator;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange,
                             WebFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();

        // Skip public APIs
        if (isPublicEndpoint(path)) {
            return chain.filter(exchange);
        }

        String authHeader =
                exchange.getRequest().getHeaders()
                        .getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {

            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);

            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(7);

        try {

            if (!jwtTokenValidator.isTokenValid(token)) {

                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);

                return exchange.getResponse().setComplete();
            }

            String userId =
                    String.valueOf(jwtTokenValidator.extractUserId(token));

            String email =
                    jwtTokenValidator.extractEmail(token);

            String role =
                    jwtTokenValidator.extractRole(token);

            ServerHttpRequest mutatedRequest =
                    exchange.getRequest()
                            .mutate()
                            .header("X-User-Id", userId)
                            .header("X-User-Email", email)
                            .header("X-User-Role", role)
                            .build();

            return chain.filter(
                    exchange.mutate()
                            .request(mutatedRequest)
                            .build());

        } catch (Exception ex) {

            log.error("Invalid JWT : {}", ex.getMessage());

            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);

            return exchange.getResponse().setComplete();
        }
    }

    private boolean isPublicEndpoint(String path) {

        for (String pattern : SecurityConstants.PUBLIC_ENDPOINTS) {

            if (pathMatcher.match(pattern, path)) {
                return true;
            }
        }

        return false;
    }
}