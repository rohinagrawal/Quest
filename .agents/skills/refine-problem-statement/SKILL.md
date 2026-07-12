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
| **Sibling skim** | Need section style (hints? complexity? diagrams?) | `list_dir` parent topic → open **one** other `Problem_Statement.md` → skim headings + **Approach Hints** section shape |

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
| **POLISH** | Content already teaches the problem | 60–110 | Restructure; keep examples and hints |
| **EXPAND** | Missing I/O, constraints, examples | 80–130 | 2 examples + formats + bounds + hints if algorithmic |
| **BUILD** | Stub / title only | 100–160 | EXPAND + diagram/paths + **Approach Hints** for algorithm problems |

**BUILD checklist (all required):**
1. Task, rules, tie-break / output order in description
2. **Name the required technique** in Problem Description when the problem implies a specific algorithm (e.g. multi-source BFS, Dial's algorithm) — mirror `smallest_cost_path` / `nearest_targets_from_sources`
3. **Two** examples: **Input**, **Output**, **Explanation** (path trace or steps)
4. Input Format + Output Format
5. Constraints with numeric bounds (`10^5`)
6. One ASCII diagram for grid / graph / tree / list (in Example 1)
7. Key Points — non-obvious only (≤5 bullets)
8. **`## Approach Hints`** — required for graph/BFS/shortest-path/heap/tree-algorithm problems; see [patterns.md](references/patterns.md)
9. **`## Complexity Analysis`** — brief time/space after Approach Hints

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
- **Problem Description:** 3–5 sentences; **name the required algorithm** when one exists (`multi-source BFS`, `Dial's algorithm`, …)
- **Examples:** `### Example N` — Explanation is mandatory (2–3 bullets)
- **Approach Hints:** required for algorithm-specific DSA — short subsections + compact pseudocode or steps (not a lecture)
- **Complexity Analysis:** 2–4 bullets comparing naive vs intended approach when relevant
- **Constraints:** `10^5` not `100000`
- Diagram snippets: [patterns.md](references/patterns.md)

### Approach Hints shape (match repo siblings)

```markdown
## Approach Hints

### Required idea: <technique name>

[2–4 bullets or compact pseudocode block]

### <optional sub-step> (e.g. answering queries, bucket progression)

[1–3 bullets]
```

- **Problem Description** calls out the technique in one sentence (like Dial's in `smallest_cost_path`, multi-source BFS in `nearest_targets_from_sources`)
- **Approach Hints** carries the implementation sketch — keep pseudocode ≤15 lines
- Skip Approach Hints only for trivial array/string problems with no named pattern

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
