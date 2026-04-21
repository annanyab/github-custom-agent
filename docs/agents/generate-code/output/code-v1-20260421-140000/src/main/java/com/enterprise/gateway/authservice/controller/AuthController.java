package com.enterprise.gateway.authservice.controller;

import com.enterprise.gateway.authservice.dto.LoginRequest;
import com.enterprise.gateway.authservice.dto.LoginResponse;
import com.enterprise.gateway.authservice.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Azure AD Authentication APIs")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Authenticate user with corporate email and password")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse response = authService.authenticate(loginRequest);
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body(response);
        }
    }

    @GetMapping("/status")
    @Operation(summary = "Get authentication status")
    public ResponseEntity<String> getStatus() {
        return ResponseEntity.ok("Auth Service is running");
    }
}
