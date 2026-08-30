# Regular Expression Matching (`.` and `*`)

## Problem Description

Given an input string `A` and a pattern `B` supporting `.` and `*`, determine whether `B` matches `A` in its **entirety** (not partial). Return `1` if it matches, else `0`.

- `.` matches **any single** character.
- `*` matches **zero or more** of the **preceding element** (the token immediately before it).

Note the crucial difference from simple wildcards: `*` here is a **quantifier bound to the previous character/`.`**, not a free "any sequence". Solve it with **2D dynamic programming**: `dp[i][j]` is `true` iff `A[0..i-1]` is fully matched by `B[0..j-1]`. A `*` at `B[j-1]` (with `B[j-2]` as its element) may match **zero** copies (`dp[i][j-2]`) or, when the element matches `A[i-1]`, **one more** copy (`dp[i-1][j]`).

---

## Examples

### Example 1

**Input:**
```text
A = "aab"
B = "c*a*b"
```

**Output:**
```text
1
```

**Explanation:**
- `c*` matches **zero** `c`s, `a*` matches `"aa"`, and `b` matches `b`.

```text
B:  c*      a*    b
A:  (none)  aa    b
```

- The whole string is covered → `1`.

### Example 2

**Input:**
```text
A = "mississippi"
B = "mis*is*p*."
```

**Output:**
```text
0
```

**Explanation:**
- Tokens `m, i, s*, i, s*, p*, .` consume `m i ss i ss` then `p*` matches zero `p`s and `.` matches one `i` — but the trailing `"ppi"` is left unmatched.
- No assignment covers the entire string, so the result is `0`.

---

## Input Format

- `A` — the input string (lowercase letters).
- `B` — the pattern of lowercase letters, `.`, and `*` (each `*` follows a letter or `.`).

## Output Format

- `1` if `B` matches all of `A`, otherwise `0`.

---

## Constraints

- `1 <= |A|, |B| <= 2 * 10^3`
- `A` consists of lowercase English letters.
- `B` consists of lowercase letters, `.`, and `*`; every `*` has a valid preceding element.

---

## Key Points

1. `*` binds to the **preceding token** (`B[j-2]`); it is not a standalone "any sequence" like the `?`/`*` wildcard problem.
2. **Zero-copy branch:** `dp[i][j] = dp[i][j-2]` (drop the `x*` pair entirely) — this is what lets patterns match shorter strings, and seeds `dp[0][j]`.
3. **Extend branch:** if `B[j-2] == '.'` or `B[j-2] == A[i-1]`, then `dp[i][j] |= dp[i-1][j]` (consume one more copy).
4. A plain `.`/literal uses `dp[i][j] = dp[i-1][j-1]`; the answer is the full-match cell `dp[|A|][|B|]`.

---

## Approach Hints

### Required idea: 2D DP with `*` as a quantifier

```text
dp[0][0] = true
for j in 1..|B|:                         // empty A: only x* pairs can match
    if B[j-1] == '*': dp[0][j] = dp[0][j-2]
for i in 1..|A|:
    for j in 1..|B|:
        if B[j-1] == '.' or B[j-1] == A[i-1]:
            dp[i][j] = dp[i-1][j-1]
        elif B[j-1] == '*':
            dp[i][j] = dp[i][j-2]                          // zero copies
            if B[j-2] == '.' or B[j-2] == A[i-1]:
                dp[i][j] = dp[i][j] or dp[i-1][j]          // one more copy
return dp[|A|][|B|] ? 1 : 0
```

---

## Complexity Analysis

- **2D DP (intended):** Time `O(|A| * |B|)`, Space `O(|A| * |B|)` (or `O(|B|)` with a rolling row).
- **Naive recursion (branch each `*`):** exponential without memoization.
- **NFA/Thompson construction:** `O(|A| * |B|)` too, but heavier machinery for this fixed operator set.
