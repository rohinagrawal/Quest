# Longest Common Subsequence

## Problem Description

Given two strings `A` and `B`, return the length of their **longest common subsequence (LCS)** — the longest sequence of characters that appears in **both** strings in the same relative order, though not necessarily contiguously. If there is no common subsequence, return `0`.

Solve it with a **2D dynamic programming** table where `dp[i][j]` = LCS length of the prefixes `A[0..i-1]` and `B[0..j-1]`. When the current characters match, extend the diagonal; otherwise take the better of dropping one character from either string.

---

## Examples

### Example 1

**Input:**
```text
A = "abcde"
B = "ace"
```

**Output:**
```text
3
```

**Explanation:**
- The subsequence `"ace"` appears in both (in `A`, skip `b` and `d`).

```text
      ""  a  c  e
   "" 0   0  0  0
   a  0   1  1  1
   b  0   1  1  1
   c  0   1  2  2
   d  0   1  2  2
   e  0   1  2  3
```

- `dp[5][3] = 3` — the bottom-right cell holds the answer.

### Example 2

**Input:**
```text
A = "abc"
B = "def"
```

**Output:**
```text
0
```

**Explanation:**
- The two strings share no characters, so no common subsequence exists.
- Every `dp` cell stays `0`, giving `0`.

---

## Input Format

- Two strings `A` and `B`.

## Output Format

- An integer: the length of the longest common subsequence.

---

## Constraints

- `1 <= |A|, |B| <= 10^3`
- Strings consist of printable characters (commonly lowercase English letters).

---

## Key Points

1. **Subsequence, not substring** — characters need not be contiguous, only in order.
2. On a character match the answer comes from the **diagonal** `dp[i-1][j-1] + 1`; on a mismatch from `max(dp[i-1][j], dp[i][j-1])`.
3. A row/column of `0`s (empty prefix) seeds the table; the answer is `dp[|A|][|B|]`.

---

## Approach Hints

### Required idea: 2D LCS dynamic programming

```text
dp[0][*] = dp[*][0] = 0
for i in 1..|A|:
    for j in 1..|B|:
        if A[i-1] == B[j-1]:
            dp[i][j] = dp[i-1][j-1] + 1
        else:
            dp[i][j] = max(dp[i-1][j], dp[i][j-1])
return dp[|A|][|B|]
```

### Space optimization

- Each row depends only on the previous row → keep **two rows** (or one row with a saved diagonal) for `O(min(|A|, |B|))` space.

---

## Complexity Analysis

- **2D DP (intended):** Time `O(|A| * |B|)`, Space `O(|A| * |B|)` (or `O(min(|A|,|B|))` rolling).
- **Naive recursion (branch on match/skip):** exponential `O(2^{|A|+|B|})` without memoization.
