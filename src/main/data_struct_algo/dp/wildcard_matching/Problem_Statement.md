# Wildcard Pattern Matching

## Problem Description

Given an input string `A` and a pattern `B` that may contain the wildcards `?` and `*`, determine whether `B` matches `A` in its **entirety** (not a partial match). Return `1` if it matches, else `0`.

- `?` matches **any single** character.
- `*` matches **any sequence** of characters, including the **empty** sequence.

Solve it with **2D dynamic programming**: `dp[i][j]` is `true` iff the prefix `A[0..i-1]` is fully matched by the pattern prefix `B[0..j-1]`. A `*` is the only interesting case — it can consume nothing (`dp[i][j-1]`) or one more character of `A` (`dp[i-1][j]`).

---

## Examples

### Example 1

**Input:**
```text
A = "adceb"
B = "*a*b"
```

**Output:**
```text
1
```

**Explanation:**
- The first `*` matches the empty sequence, `a` matches `a`, the second `*` matches `"dce"`, and `b` matches `b`.

```text
B:  *      a   *       b
A:  (empty)a   dce     b
```

- The whole string is covered, so the result is `1`.

### Example 2

**Input:**
```text
A = "acdcb"
B = "a*c?b"
```

**Output:**
```text
0
```

**Explanation:**
- `a` matches `a`; whatever the `*` consumes, the tail `c ? b` cannot align with the remaining characters — every split leaves a `b`-vs-`c` mismatch.
- No assignment covers the entire string, so the result is `0`.

---

## Input Format

- `A` — the input string (lowercase letters).
- `B` — the pattern, which may contain lowercase letters, `?`, and `*`.

## Output Format

- `1` if `B` matches all of `A`, otherwise `0`.

---

## Constraints

- `1 <= |A|, |B| <= 2 * 10^3`
- `A` consists of lowercase English letters.
- `B` consists of lowercase English letters and the characters `?` and `*`.

---

## Key Points

1. **Full-string match** — the answer is `dp[|A|][|B|]`; a prefix match does not count.
2. **Empty-string row:** `dp[0][j]` is `true` only while `B[0..j-1]` is **all** `*` (each matches empty).
3. `*` branches two ways: **use it as empty** (`dp[i][j-1]`) or **consume one char of A** (`dp[i-1][j]`).
4. `?` and a literal match behave the same as exact matching: `dp[i][j] = dp[i-1][j-1]`.

---

## Approach Hints

### Required idea: 2D DP over prefixes

```text
dp[0][0] = true
for j in 1..|B|:                       // empty A matched only by leading '*'s
    dp[0][j] = dp[0][j-1] and B[j-1] == '*'
for i in 1..|A|:
    for j in 1..|B|:
        if B[j-1] == '?' or B[j-1] == A[i-1]:
            dp[i][j] = dp[i-1][j-1]
        elif B[j-1] == '*':
            dp[i][j] = dp[i][j-1] or dp[i-1][j]   // empty  OR  consume one
        else:
            dp[i][j] = false
return dp[|A|][|B|] ? 1 : 0
```

### Greedy two-pointer alternative

- Scan `A` and `B` together; on `*` remember its position and A's index, then **backtrack** to consume one more character whenever a later mismatch occurs. Runs in `O(|A| + |B|)` time and `O(1)` space.

---

## Complexity Analysis

- **2D DP (intended):** Time `O(|A| * |B|)`, Space `O(|A| * |B|)` (or `O(|B|)` with a rolling row).
- **Greedy two-pointer:** Time `O(|A| + |B|)` average, Space `O(1)` — faster but trickier to reason about.
- **Naive recursion (expand every `*`):** exponential without memoization.
