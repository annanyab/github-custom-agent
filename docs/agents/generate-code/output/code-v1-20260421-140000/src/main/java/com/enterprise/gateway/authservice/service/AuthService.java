package com.enterprise.gateway.authservice.service;

import com.enterprise.gateway.authservice.dto.LoginRequest;
import com.enterprise.gateway.authservice.dto.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AzureAdAuthenticator azureAdAuthenticator;

    /**
     * Authenticates a user against Azure AD using provided credentials.
     * 
     * @param loginRequest contains email and password
     * @return LoginResponse with authentication status
     */
    public LoginResponse authenticate(LoginRequest loginRequest) {
        // Validate input
        if (loginRequest.getEmail() == null || loginRequest.getEmail().isBlank() ||
            loginRequest.getPassword() == null || loginRequest.getPassword().isBlank()) {
            return new LoginResponse(false, "Email and password are required", null);
        }

        // Invoke Azure AD authenticator
        AuthResult result = azureAdAuthenticator.validateCredentials(
            loginRequest.getEmail(), 
            loginRequest.getPassword()
        );

        if (result.isAuthenticated()) {
            return new LoginResponse(true, "Authentication successful", result.getAccessToken());
        } else {
            return new LoginResponse(false, "Invalid Credentials", null);
        }
    }
}
