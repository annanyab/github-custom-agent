# Architecture Quality Gate (Score 1-5)

### 1. Spring Best Practices (Weight: 30%)
- Is the `@Service` layer separated from the `@Controller`?
- Are DTOs used for request/response (No leaked Entities)?

### 2. Azure Alignment (Weight: 40%)
- Does the Terraform code include Managed Identity (No hardcoded secrets)?
- Are the availability zones matched to the FSD requirements?

### 3. Dapr Integration (Weight: 30%)
- Does the code include `dapr-sdk` dependencies?
- Is the service-to-service call using Dapr's `Invoke` method?