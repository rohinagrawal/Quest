# Refine Problem Statement

Use when improving, rewriting, or creating `Problem_Statement.md` under Quest.

## Goal

Produce a **clear, scannable** statement that matches the best Quest examples while using **minimal tokens**. Add a diagram only when it replaces several sentences of explanation.

## Before Editing

1. Read the target `Problem_Statement.md` and any solution files in the same folder (`Code.*`, `Demo.java`, tests).
2. Read **one** strong neighbor from the same area — not the whole repo:
   - DSA: same topic folder, else `graph/traversal` or `linked_list/reverse_k_nodes_group`
   - Machine coding: `machine_coding/message_broker`
   - System design: pick the cleaner peer under `system_design/`
3. Open [references/patterns.md](references/patterns.md) only for the matching problem type.

## Workflow

```
Assess → Pick template → Draft minimal sections → Add diagram if needed → Trim → Validate
```

### 1. Assess (do not rewrite blindly)

| Signal | Action |
| --- | --- |
| Stub or title only | Build from code + folder name |
| Legacy flat headings (`Problem Description` without `#`) | Normalize to Quest template |
| Missing examples / I/O / constraints | Add from code and tests |
| Broken image refs (`![...](missing.png)`) | Replace with ASCII or mermaid |
| Verbose tutorial (hints + complexity + follow-up) | Drop unless user asked or repo siblings include them |
| Ambiguous traversal / ordering / tie-break | State rule explicitly; add tiny diagram |

### 2. Token Budget (strict)

**Always include:** title, problem description, examples, input format, output format, constraints.

**Include only when useful:**
- **Key Points** — non-obvious rules only (≤5 bullets)
- **Diagram** — when spatial/ordering clarity saves ≥3 sentences
- **Approach Hints / Complexity** — only if sibling problems in the same topic use them, or user requests

**Never include:**
- Restating constraints inside Key Points
- Generic algorithm lectures the reader already knows
- More than **2** full examples (use a 3rd only as a one-line edge case)
- External image links unless the file exists in the folder

### 3. Writing Rules

- `# Title` then `## Section` headings; separate major sections with `---` when the file exceeds ~80 lines.
- **Problem Description:** 2–5 short sentences. Bold only terms that change behavior (`directed`, `in-place`, `1-indexed`).
- **Examples:** use `### Example N` with labeled **Input**, **Output**, **Explanation** (1–3 bullets).
- Prefer inline literals: `` `A = [1,2,3]` `` over narrative repetition.
- **Constraints:** bullet list; use `10^5` not `100000`.
- Keep total length roughly **60–120 lines** for standard DSA; machine coding may run longer for requirements lists.

### 4. Diagrams (pick one; smallest that works)

| Need | Prefer |
| --- | --- |
| Graph / weighted edge | ASCII: `(0) --3-- (1)` |
| Linked list transform | Inline: `1 -> 2 -> 3` before/after in a `text` block |
| Tree | Compact ASCII; mermaid only if depth > 3 |
| Grid / matrix | `text` code block grid |
| Histogram / trapping water | ASCII bars in `text` block |
| Flow / state / API sequence | mermaid (≤12 nodes) |

Skip the diagram if a single example already disambiguates.

See [references/patterns.md](references/patterns.md) for copy-paste skeletons and gold-standard paths.

### 5. Problem-Type Overrides

- **DSA** — default skeleton in patterns.md
- **Machine coding** — requirements, assumptions, edge cases, deliverables; skip algorithmic complexity unless relevant
- **System design** — scope in/out, functional + non-functional requirements, assumptions; no LeetCode-style I/O unless APIs are defined

### 6. Finish

1. Write only the target `Problem_Statement.md` unless the user asked for more.
2. Run validation:

```bash
python3 .agents/skills/refine-problem-statement/scripts/validate-problem-statement.py <path/to/Problem_Statement.md>
```

3. Fix all `FAIL` lines; treat `WARN` as optional polish unless the file was a stub.

## Quality Bar

A refined statement should let a reader answer in under a minute:

- What is the task?
- What goes in and out?
- What do the limits allow?
- What does example 1 prove?

If those four are obvious without scrolling, the statement is done — do not add filler.
