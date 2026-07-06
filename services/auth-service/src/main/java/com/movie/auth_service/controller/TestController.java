package com.movie.auth_service.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/api/v1/test/public")
    public String publicApi() {
        return "Public API";
    }

    @GetMapping("/api/v1/test/private")
    public String privateApi(Authentication authentication) {
        return "Welcome " + authentication.getName();
    }
}