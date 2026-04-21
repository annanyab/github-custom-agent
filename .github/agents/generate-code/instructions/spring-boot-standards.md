# Spring Boot 3.x Development Standards
- **Package Naming:** Use `com.enterprise.gateway.[module]`.
- **Security:** Use Spring Security 6.x. Ensure all endpoints are protected via OIDC (Azure AD).
- **Resiliency:** Implement the **Dapr** sidecar pattern for service-to-service invocation.
- **Documentation:** Every Controller must have Swagger/OpenAPI annotations.
- **Database:** Use Spring Data JPA/NoSQL with connection strings sourced from Environment Variables.