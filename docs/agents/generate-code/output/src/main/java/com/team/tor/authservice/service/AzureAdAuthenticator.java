package com.team.tor.authservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.team.tor.authservice.config.AzureAdProperties;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class AzureAdAuthenticator {

    private final AzureAdProperties properties;
    private final WebClient webClient;

    public AzureAdAuthenticator(AzureAdProperties properties, WebClient.Builder webClientBuilder) {
        this.properties = properties;
        this.webClient = webClientBuilder.build();
    }

    public AuthResult authenticate(String email, String password) {
        String tokenUrl = "https://login.microsoftonline.com/" + properties.getTenantId() + "/oauth2/v2.0/token";

        LinkedMultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("client_id", properties.getClientId());
        form.add("client_secret", properties.getClientSecret());
        form.add("scope", properties.getScope());
        form.add("grant_type", "password");
        form.add("username", email);
        form.add("password", password);

        try {
            JsonNode response = webClient.post()
                    .uri(tokenUrl)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .bodyValue(form)
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .block();

            String token = response != null && response.has("access_token")
                    ? response.get("access_token").asText()
                    : null;

            return new AuthResult(token != null && !token.isBlank(), token);
        } catch (Exception ex) {
            return new AuthResult(false, null);
        }
    }
}
