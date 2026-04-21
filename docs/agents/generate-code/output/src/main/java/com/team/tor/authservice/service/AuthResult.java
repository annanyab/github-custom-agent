package com.team.tor.authservice.service;

public class AuthResult {
    private final boolean authenticated;
    private final String accessToken;

    public AuthResult(boolean authenticated, String accessToken) {
        this.authenticated = authenticated;
        this.accessToken = accessToken;
    }

    public boolean isAuthenticated() { return authenticated; }
    public String getAccessToken() { return accessToken; }
}
