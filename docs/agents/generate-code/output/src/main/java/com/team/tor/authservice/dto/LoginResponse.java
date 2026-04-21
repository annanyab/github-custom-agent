package com.team.tor.authservice.dto;

public class LoginResponse {
    private boolean authenticated;
    private String message;
    private String accessToken;

    public LoginResponse() {}

    public LoginResponse(boolean authenticated, String message, String accessToken) {
        this.authenticated = authenticated;
        this.message = message;
        this.accessToken = accessToken;
    }

    public boolean isAuthenticated() { return authenticated; }
    public void setAuthenticated(boolean authenticated) { this.authenticated = authenticated; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getAccessToken() { return accessToken; }
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }
}
