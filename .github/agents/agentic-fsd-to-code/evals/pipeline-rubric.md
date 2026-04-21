# Pipeline Orchestration Quality Rubric (v1.0)

Score the orchestrated pipeline on a scale of 1-5 based on the following dimensions:

## 1. Story Generation Outcome (Weight: 40%)

### Did the story generation stage complete and pass validation?
- **5 (Excellent)**: create-story PASS, eval score 5/5, all artifacts generated
- **4 (Good)**: create-story PASS, eval score 4/5, all artifacts generated
- **3 (Acceptable)**: create-story PASS, eval score 3/5, all artifacts generated
- **2 (Poor)**: create-story PASS, eval score 2/5; stories lack detail or clarity
- **1 (Failed)**: create-story status FAIL; validation report shows missing requirements

---

## 2. Code Generation Outcome (Weight: 40%)

### Did the code generation stage complete and pass architecture validation?
- **5 (Excellent)**: generate-code PASS, architecture score 5/5, all artifacts generated, Spring Boot standards met, Azure AD integration clean
- **4 (Good)**: generate-code PASS, architecture score 4/5, minor code quality issues
- **3 (Acceptable)**: generate-code PASS, architecture score 3/5, basic standards met but room for improvement
- **2 (Poor)**: generate-code PASS, architecture score 2/5; code has design flaws
- **1 (Failed)**: generate-code status FAIL; architecture report shows significant gaps

---

## 3. Orchestration Execution (Weight: 20%)

### Did the orchestrator enforce quality gates and provide clear audit trail?

### Gating Enforcement
- **5 (Excellent)**: Both gates correctly enforced; PASS/FAIL decisions made properly; no override
- **4 (Good)**: Gates enforced; minor gate decision delays or logging gaps
- **3 (Acceptable)**: Gates present but not fully automated; manual verification needed
- **2 (Poor)**: Gates partially enforced; some PASS artifacts accepted without full validation
- **1 (Failed)**: No gate enforcement; downstream stage called even on upstream FAIL

### Artifact & Traceability Completeness
- **5 (Excellent)**: All 3 manifests present (story, code, orchestration); run_id flows end-to-end; all paths captured; timestamps aligned
- **4 (Good)**: All manifests present; run_id tracked; minor path/timestamp gaps
- **3 (Acceptable)**: Manifests present but some fields missing or unclear
- **2 (Poor)**: Incomplete manifests; run_id traceability weak
- **1 (Failed)**: Manifests missing or corrupted; no traceability

### Timeout & Error Handling
- **5 (Excellent)**: Timeouts detected and handled; clear error messages; recovery guidance provided
- **4 (Good)**: Timeouts detected; error messages clear; recovery guidance adequate
- **3 (Acceptable)**: Timeouts detected; error handling basic
- **2 (Poor)**: Timeouts not always detected; error messages unclear
- **1 (Failed)**: No timeout detection; silent failures or confusing errors

---

## Scoring Logic

**Final Orchestration Score = (Story Score × 0.4) + (Code Score × 0.4) + (Orchestration Score × 0.2)**

Rounded to nearest integer (1-5).

### Example Calculations

**Ideal Run:**
- Story = 5, Code = 5, Orchestration = 5
- Final = (5 × 0.4) + (5 × 0.4) + (5 × 0.2) = 5.0 → **PASS (Score 5)**

**Code Generation Failure (Caught by Gate):**
- Story = 5, Code = 1, Orchestration = 5
- Final = (5 × 0.4) + (1 × 0.4) + (5 × 0.2) = 3.4 → **FAIL (Score 3)**
- Note: Gate enforcement was excellent; code was the bottleneck

**Gate Bypass (Failure):**
- Story = 3, Code = 2, Orchestration = 1
- Final = (3 × 0.4) + (2 × 0.4) + (1 × 0.2) = 2.2 → **FAIL (Score 2)**
- Critical: Orchestration enforcement failed; gates did not prevent bad flow

---

## Decision Thresholds

- **Score 5**: Excellent pipeline; production ready
- **Score 4**: Good pipeline; minor issues to address in next iteration
- **Score 3**: Acceptable for internal review; rework recommended before release
- **Score 2**: Significant issues; requires manual intervention and rework
- **Score 1**: Pipeline failure; do not proceed; investigate root cause

---

## Report Integration

Include final orchestration score in:
- `orchestration-run-<run_id>.md` (human-readable)
- `orchestration-manifest-<run_id>.json` (machine-readable)

Example JSON:
```json
{
  "orchestration_score": 5,
  "story_generation_score": 5,
  "code_generation_score": 5,
  "gating_enforcement_score": 5,
  "scoring_notes": "All stages PASS with high scores; excellent gate enforcement and audit trail"
}
```
