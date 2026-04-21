# Pipeline Orchestration Guidelines

## Core Principles

### 1. Unified Run ID Propagation
- Generate a single `run_id` at orchestration start (or accept as parameter)
- Pass this `run_id` to both `create-story` and `generate-code`
- All downstream artifacts reference this shared `run_id`
- Enables complete traceability from FSD input → code output

### 2. Graceful Handoff Between Agents
- After invoking an agent, poll for its handoff manifest (JSON)
- Never assume success; always read `status` field before proceeding
- Extract artifact paths from manifest, not from hardcoded conventions
- Timeout after 5 minutes; treat as failure

### 3. Quality Gate Enforcement
- **Story Gate**: Only proceed to code generation if story validation status == PASS
- **Code Gate**: Only finalize if code generation status == PASS
- **Fail-Fast**: Stop immediately on first FAIL; do not attempt recovery
- **Clear Reporting**: Capture why a gate failed for audit and retry guidance

### 4. Audit Trail Completeness
- Log orchestration start and end timestamps
- Record each gate decision and reason
- Include linked artifact paths in all reports
- Write both human-readable (MD) and machine-readable (JSON) manifests

### 5. Idempotency & Resume
- Use `run_id` to enable resume capability
- If pipeline is interrupted, re-invoke with same `run_id` to resume from checkpoint
- Check if prior handoff artifacts exist; don't regenerate if already complete

---

## Implementation Checklist

- [ ] Generate or accept `run_id` at start
- [ ] Resolve `source_fsd` from input zone or parameter
- [ ] Log orchestration start in `orchestration-run-<run_id>.md` (status: IN_PROGRESS)
- [ ] Invoke `@create-story` with `run_id` and `source_fsd`
- [ ] Poll for `story-handoff-<run_id>.json` (timeout: 5 min)
- [ ] Read story status; if FAIL, stop and write failure report
- [ ] If PASS, invoke `@generate-code` with handoff manifest path and `run_id`
- [ ] Poll for `code-handoff-v1-<run_id>.json` (timeout: 5 min)
- [ ] Read code status; if FAIL, stop and write failure report
- [ ] If PASS, write orchestration-run, orchestration-manifest, orchestration-audit
- [ ] Score pipeline using quality rubric; include final score in reports

---

## Handoff Manifest Reading Pattern

```yaml
# After story generation, read story-handoff-<run_id>.json:
Run ID: <extract run_id field>
Status: <extract status field — should be PASS or FAIL>
Story File: <extract story_output_file>
Validation Report: <extract validation_report_file>
Score: <extract eval_score>

# Decision:
if status == PASS:
  proceed to code generation
else:
  read validation_report for failure reason
  stop pipeline

# After code generation, read code-handoff-v1-<run_id>.json:
Run ID: <extract run_id field>
Status: <extract status field — should be PASS or FAIL>
Code Path: <extract code_output_path>
Report: <extract code_generation_report_file>
Score: <extract architecture_score>

# Decision:
if status == PASS:
  finalize pipeline
else:
  read code_generation_report for failure reason
  stop pipeline
```

---

## Timeout & Error Handling

### Timeout Scenario
```
Poll for story-handoff-<run_id>.json for 5 minutes
If not found:
  → Write orchestration-run-<run_id>.md with status: TIMED_OUT_WAITING_FOR_STORY
  → Write failure context including: retry command, logs to check, estimated wait time
  → Stop pipeline
```

### Malformed Handoff
```
If story-handoff-<run_id>.json exists but:
  - Missing "status" field
  - Invalid JSON format
  - Missing required fields
Then:
  → Write orchestration-run-<run_id>.md with status: INVALID_HANDOFF_FORMAT
  → Include error details
  → Stop pipeline
```

### Recovery Recommendations
Include in failure reports:
- Re-run command with same `run_id` to retry
- Check agent logs in story_log_zone or code_log_zone
- Manual inspection checklist (validation report, error messages)

---

## Orchestration Scores

Use the pipeline rubric to score orchestration success on 1-5 scale:
- **Score 5**: Both stages PASS, all artifacts complete, gates enforced correctly
- **Score 3**: One stage fails cleanly (gate works); clear audit trail
- **Score 1**: Malformed handoff or infrastructure failure; incomplete audit trail

Include final score in `orchestration-run-<run_id>.md`.
