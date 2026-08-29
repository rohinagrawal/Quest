# Longest Common Substring

## Problem Description

Given two strings `A` and `B`, return the length of their **longest common substring** — the longest run of characters that appears **contiguously** in both strings. If they share no characters, return `0`.

This is the contiguous cousin of the longest common **subsequence**. Solve it with a **2D dynamic programming** table where `dp[i][j]` = length of the longest common suffix of the prefixes `A[0..i-1]` and `B[0..j-1]`. On a character match, extend the diagonal by one; on a **mismatch, reset to `0`** (contiguity is broken). The answer is the **maximum** cell in the whole table, not `dp[|A|][|B|]`.

---

## Examples

### Example 1

**Input:**
```text
A = "abcde"
B = "abfde"
```

**Output:**
```text
2
```

**Explanation:**
- Common substrings: `"ab"` (length `2`) and `"de"` (length `2`); the `c`/`f` mismatch in the middle breaks any longer run.

```text
      ""  a  b  f  d  e
   "" 0   0  0  0  0  0
   a  0   1  0  0  0  0
   b  0   0  2  0  0  0
   c  0   0  0  0  0  0
   d  0   0  0  0  1  0
   e  0   0  0  0  0  2
```

- Max cell is `2`. Note the common **subsequence** here would be `"abde"` (length `4`) — a substring must stay contiguous.

### Example 2

**Input:**
```text
A = "abcdxyz"
B = "xyzabcd"
```

**Output:**
```text
4
```

**Explanation:**
- `"abcd"` appears in `A` at indices `0..3` and in `B` at indices `3..6` → length `4`.
- `"xyz"` is also common but only length `3`, so the answer is `4`.

---

## Input Format

- Two strings `A` and `B`.

## Output Format

- An integer: the length of the longest common substring.

---

## Constraints

- `1 <= |A|, |B| <= 10^3`
- Strings consist of printable characters (commonly lowercase English letters).

---

## Key Points

1. **Substring, not subsequence** — characters must be contiguous, so a mismatch **resets** the run to `0` (LCS instead carries the best so far).
2. `dp[i][j]` is the longest common **suffix** ending at `A[i-1]` and `B[j-1]`; the answer is the global **max** over all cells.
3. `dp[i][j] = dp[i-1][j-1] + 1` only when the characters match; otherwise `0`.

---

## Approach Hints

### Required idea: 2D DP on common suffixes

```text
best = 0
for i in 1..|A|:
    for j in 1..|B|:
        if A[i-1] == B[j-1]:
            dp[i][j] = dp[i-1][j-1] + 1
            best = max(best, dp[i][j])
        else:
            dp[i][j] = 0          // contiguity broken
return best
```

### Space optimization

- Each row depends only on the previous row → keep **two rows** for `O(min(|A|, |B|))` space.

---

## Complexity Analysis

- **2D DP (intended):** Time `O(|A| * |B|)`, Space `O(|A| * |B|)` (or `O(min(|A|,|B|))` rolling).
- **Suffix-automaton / suffix-tree:** `O(|A| + |B|)` — optimal but far more complex to implement.
- **Naive (compare all substring pairs):** `O(|A|^2 * |B|)` — infeasible.
