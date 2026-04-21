# User Story Evaluation Rubric (v1.0)

Score the generated output on a scale of 1-5 based on the following dimensions:

### 1. INVEST Compliance (Weight: 40%)
- **Independent:** Can the story be delivered without a hard dependency?
- **Negotiable:** Is it a conversation starter, not a rigid contract?
- **Valuable:** Does it clearly state the business value in the "so that" clause?
- **Estimable/Small/Testable:** Is it sized correctly for a single sprint?

### 2. Formatting (Weight: 20%)
- Does it follow the standard "As a... I want... So that..." template?
- Are Acceptance Criteria (AC) clearly bulleted?

### 3. Azure AD Context (Weight: 40%)
- Since this is an Enterprise Architect workspace, does the story specifically mention identity provider (Azure AD) constraints if present in the FSD?

## Scoring Logic
- **Score 5 (Excellent):** Meets all INVEST criteria; includes security/technical constraints.
- **Score 3 (Pass):** Correct format but vague Acceptance Criteria.
- **Score 1 (Fail):** Missing "So that" clause or non-testable ACs.