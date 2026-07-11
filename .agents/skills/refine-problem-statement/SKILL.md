# Refine Problem Statement

Use when improving, rewriting, or creating `Problem_Statement.md` under Quest.

## Goal

Produce an **interview-ready** statement in **one pass**: clear task, explicit I/O, worked examples with explanations, numeric constraints. Optimize for **speed**, **output tokens**, and **tool cost**.

**Target:** ≤4 tool rounds total. Write once → validate → fix `FAIL`s only → stop.

---

## Cost Budget

| Resource | Limit |
| --- | --- |
| File reads | Target `Problem_Statement.md` + `Code.*` (if any) + [patterns.md](references/patterns.md) — **max 3 files** |
| Repo neighbor | **At most 1** sibling discovered dynamically (see below) — headings skim only |
| Web search | **At most 1** query — **BUILD stubs only**, to confirm a similar known problem exists |
| Repo grep | Parent topic folder only; never whole-repo |
| Write passes | **1** (no polish loop) |
| Output length | See depth tiers — never exceed tier max |

---

## Workflow

```
Classify → Gather (parallel, budgeted) → Write once → Validate → Fix FAILs → Stop
```

### 1. Classify (no reads)

| State | Tier | Action |
| --- | --- | --- |
| ≤2 lines or &lt;25 words | **BUILD** | Author full problem |
| Legacy flat headings | **EXPAND** | Normalize + fill gaps |
| Good content, messy format | **POLISH** | Restructure only |
| Missing sections only | **EXPAND** | Add from `Code.*` |

**Stub + empty `Code.*`:** you are **authoring**, not reformatting.

### 2. Gather (one parallel batch)

Always read in parallel:
- Target `Problem_Statement.md`
- `Code.*` / tests in same folder (if present)
- [patterns.md](references/patterns.md) — skeleton + stub-inference table only

**BUILD stubs only** — pick **one** optional source (not both):

| Option | When | Cost |
| --- | --- | --- |
| **Web search** | Folder name ambiguous or need standard I/O/constraints | 1 query; extract ≤5 facts (shape, bounds, tie-break) |
| **Sibling skim** | Need section style (hints? complexity? diagrams?) | `list_dir` parent topic → open **one** other `Problem_Statement.md` → read headings + first example only |

Skip optional gather when stub-inference table clearly matches the folder name.

**Web search rules:**
- Query from folder name: e.g. `nearest targets sources grid BFS leetcode`
- Use results only to **confirm** problem shape and standard constraints — do not paste external text
- Do not search for algorithm tutorials or generic pattern articles
- If search finds a known problem (LC 542, 286, etc.), align I/O and tie-breaks — still write in Quest template

**Sibling discovery (no hardcoded paths):**

```text
parent = directory containing the target problem folder
candidates = glob(parent/*/Problem_Statement.md) excluding target
pick one: prefer longest file, or name sharing keywords with target folder
skim: headings + whether Approach Hints / Complexity / diagrams exist
do not open a second sibling
```

### 3. Write once

Pick depth tier; stay inside line budget.

| Tier | When | Lines | Include |
| --- | --- | --- | --- |
| **POLISH** | Content already teaches the problem | 60–100 | Restructure; keep examples |
| **EXPAND** | Missing I/O, constraints, examples | 80–120 | 2 examples + formats + bounds |
| **BUILD** | Stub / title only | 90–140 | EXPAND + diagram/paths for spatial problems |

**BUILD checklist (all required):**
1. Task, rules, tie-break / output order in description
2. **Two** examples: **Input**, **Output**, **Explanation** (path trace or steps)
3. Input Format + Output Format
4. Constraints with numeric bounds (`10^5`)
5. One ASCII diagram for grid / graph / tree / list (in Example 1)
6. Key Points — non-obvious only (≤5 bullets)
7. Approach Hints + Complexity — **only if** the one sibling skim shows them

**Token savers (always):**
- No `## Related Problems` section
- No edge-case tables — cover edge cases in Example 2 explanation
- No third full example
- No wavefront / multi-panel diagrams — one grid with coordinates is enough
- Bold only behavior-changing terms (`row-major`, `1-indexed`, `directed`)

### 4. Validate and stop

```bash
python3 .agents/skills/refine-problem-statement/scripts/validate-problem-statement.py <path/to/Problem_Statement.md>
```

Fix every `FAIL`. Ignore `WARN` on length unless you exceeded tier max. **Stop** — no second pass.

---

## Anti-Patterns

| Failure | Fix |
| --- | --- |
| **Heading transplant** | BUILD tier; stub-inference + optional web/sibling |
| **Over-research** | >1 web search, >1 sibling, or whole-repo grep |
| **Hardcoded neighbor paths** | Discover sibling via `list_dir` / `glob` on parent topic |
| **Example-free** | Add Explanation bullets with path trace |
| **Tutorial bloat** | Trim to tier max; drop Related Problems and edge tables |

---

## Writing Rules

- `# Title` then `## Section`; `---` between major sections when &gt;80 lines
- **Problem Description:** 3–5 sentences
- **Examples:** `### Example N` — Explanation is mandatory (2–3 bullets)
- **Constraints:** `10^5` not `100000`
- Diagram snippets: [patterns.md](references/patterns.md)

### Problem-type overrides

- **DSA** — skeleton in patterns.md
- **Machine coding** — requirements, assumptions, deliverables
- **System design** — functional/non-functional, out of scope

---

## Quality Bar

Reader answers in under one minute:
- What is the task?
- What goes in and out?
- What do the limits allow?
- **What does Example 1 prove?**

If Example 1 has no Explanation, the refine is **not done**.
