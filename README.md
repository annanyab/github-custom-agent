# Agent Orchestrator Workspace

This repository uses a component-based agent architecture to transform FSD input into generated code through a gated pipeline.

## Architecture

### Logic Plane (.github/agents)

Agent contracts are defined as top-level files under .github/agents using the pattern <agent-name>.agent.md.

Supporting materials are in matching subfolders:
- prompt.md
- instructions/
- evals/
- benchmark/
- skills/ (where applicable)

### Documentation Plane (docs)

Generated artifacts are written under docs/agents and docs/orchestration.

## Active Agent Directory

| Agent | Contract File | Role |
| :--- | :--- | :--- |
| agentic-fsd-to-code | .github/agents/agentic-fsd-to-code.agent.md | Pipeline orchestrator |
| create-story | .github/agents/create-story.agent.md | Business analyst |
| generate-code | .github/agents/generate-code.agent.md | Technical architect |

## Pipeline

Default run:

```bash
@agentic-fsd-to-code run with defaults.
```

Flow:
1. Resolve run_id, source_fsd, version.
2. Invoke create-story.
3. Gate on story status PASS.
4. Invoke generate-code.
5. Gate on code status PASS.
6. Write orchestration report, manifest, and audit trail.

## Repository Structure (Actual)

```text
.
├── .github/
│   └── agents/
│       ├── agentic-fsd-to-code.agent.md
│       ├── create-story.agent.md
│       ├── generate-code.agent.md
│       ├── agentic-fsd-to-code/
│       │   ├── prompt.md
│       │   ├── instructions/
│       │   ├── evals/
│       │   └── benchmark/
│       ├── create-story/
│       │   ├── prompt.md
│       │   ├── instructions/
│       │   ├── evals/
│       │   ├── benchmark/
│       │   └── skills/
│       └── generate-code/
│           ├── prompt.md
│           ├── instructions/
│           ├── evals/
│           ├── benchmark/
│           └── skills/
├── docs/
│   ├── agents/
│   │   ├── create-story/
│   │   │   ├── input/
│   │   │   └── output/
│   │   │       ├── user-stories-sample-fsd-20260421-132101.md
│   │   │       ├── user-stories-sample-fsd-20260421-140000.md
│   │   │       └── logs/
│   │   │           ├── story-handoff-20260421-132101.json
│   │   │           ├── story-handoff-20260421-140000.json
│   │   │           ├── validation-report-20260421-132101.md
│   │   │           └── validation-report-20260421-140000.md
│   │   └── generate-code/
│   │       └── output/
│   │           ├── code-v1-20260421-132746/
│   │           │   ├── pom.xml
│   │           │   └── src/
│   │           │       └── main/
│   │           │           ├── java/
│   │           │           │   └── com/
│   │           │           │       └── enterprise/
│   │           │           │           └── gateway/
│   │           │           │               └── authservice/
│   │           │           │                   ├── AuthServiceApplication.java
│   │           │           │                   ├── controller/
│   │           │           │                   │   └── AuthController.java
│   │           │           │                   ├── dto/
│   │           │           │                   │   ├── LoginRequest.java
│   │           │           │                   │   └── LoginResponse.java
│   │           │           │                   └── service/
│   │           │           │                       ├── AuthResult.java
│   │           │           │                       ├── AuthService.java
│   │           │           │                       └── AzureAdAuthenticator.java
│   │           │           └── resources/
│   │           │               └── application.yml
│   │           ├── code-v1-20260421-140000/
│   │           │   ├── pom.xml
│   │           │   └── src/
│   │           └── logs/
│   │               ├── code-generation-report-v1-20260421-132746.md
│   │               ├── code-generation-report-v1-20260421-140000.md
│   │               ├── code-handoff-v1-20260421-132746.json
│   │               └── code-handoff-v1-20260421-140000.json
│   └── orchestration/
│       └── agentic-fsd-to-code/
│           ├── output/
│           │   └── orchestration-run-20260421-140000.md
│           └── logs/
│               ├── orchestration-audit-20260421-140000.md
│               ├── orchestration-manifest-20260421-140000.json
│               └── orchestration-run-20260421-140000.md
└── README.md
```

## Artifact Locations

- Story outputs: docs/agents/create-story/output/
- Story logs and handoff: docs/agents/create-story/output/logs/
- Code outputs: docs/agents/generate-code/output/
- Code logs and handoff: docs/agents/generate-code/output/logs/
- Orchestration outputs: docs/orchestration/agentic-fsd-to-code/output/
- Orchestration logs: docs/orchestration/agentic-fsd-to-code/logs/
