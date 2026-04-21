package com.team.tor.authservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "auth.azure")
public class AzureAdProperties {
    private String tenantId;
    private String clientId;
    private String clientSecret;
    private String scope;
    private List<String> allowedCorporateDomains = new ArrayList<>();

    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }

    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }

    public String getClientSecret() { return clientSecret; }
    public void setClientSecret(String clientSecret) { this.clientSecret = clientSecret; }

    public String getScope() { return scope; }
    public void setScope(String scope) { this.scope = scope; }

    public List<String> getAllowedCorporateDomains() { return allowedCorporateDomains; }
    public void setAllowedCorporateDomains(List<String> allowedCorporateDomains) {
        this.allowedCorporateDomains = allowedCorporateDomains;
    }
}
