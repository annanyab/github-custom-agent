package com.enterprise.gateway.authservice.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthResult {
    private final boolean authenticated;
    private final String accessToken;
}
