package com.team.tor.authservice.service;

import com.team.tor.authservice.config.AzureAdProperties;
import com.team.tor.authservice.dto.LoginResponse;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AzureAdProperties properties;
    private final AzureAdAuthenticator azureAdAuthenticator;

    public AuthService(AzureAdProperties properties, AzureAdAuthenticator azureAdAuthenticator) {
        this.properties = properties;
        this.azureAdAuthenticator = azureAdAuthenticator;
    }

    public LoginResponse login(String email, String password) {
        if (!isCorporateEmail(email)) {
            return new LoginResponse(false, "Invalid Credentials", null);
        }

        AuthResult authResult = azureAdAuthenticator.authenticate(email, password);
        if (!authResult.isAuthenticated()) {
            return new LoginResponse(false, "Invalid Credentials", null);
        }

        return new LoginResponse(true, "Authenticated", authResult.getAccessToken());
    }

    private boolean isCorporateEmail(String email) {
        String[] parts = email.split("@");
        if (parts.length != 2) {
            return false;
        }
        String domain = parts[1].toLowerCase();
        return properties.getAllowedCorporateDomains()
                .stream()
                .map(String::toLowerCase)
                .anyMatch(domain::equals);
    }
}
