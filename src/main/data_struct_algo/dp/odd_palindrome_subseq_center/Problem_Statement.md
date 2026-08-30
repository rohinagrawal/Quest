# Odd-Length Palindromic Subsequences by Centre

## Problem Description

A palindrome reads the same forwards and backwards; for an odd-length palindrome of length `l`, its **centre** is the `(l+1)/2`-th character (1-indexed). Given a string `A` of length `N`, return an array `X` where `X[i]` = the number of **odd-length palindromic subsequences** of `A` whose centre is the character `A[i]`, taken **modulo `10^9 + 7`**. A subsequence is formed by deleting zero or more characters (order preserved).

The key reduction: an odd palindrome centred at index `i` is `A[i]` flanked by matching **wings** — a subsequence chosen from the left part `A[0..i-1]` whose reverse is a subsequence chosen from the right part `A[i+1..N-1]`. So `X[i]` counts pairs of **mirrored subsequences** across the split at `i` (the empty pair, giving the length-1 palindrome `A[i]`, always counts). Solve all `X[i]` at once with a **2D DP** in `O(N^2)`.

---

## Examples

### Example 1

**Input:**
```text
A = "aba"
```

**Output:**
```text
[1, 2, 1]
```

**Explanation:**
- `X[0]`: centre `a` at index 0 has no left characters → only `"a"` → **1**.
- `X[1]`: centre `b` at index 1 → `"b"`, and `"aba"` (wing pair `A[0]='a'` / `A[2]='a'`) → **2**.
- `X[2]`: centre `a` at index 2 has no right characters → only `"a"` → **1**.

### Example 2

**Input:**
```text
A = "abab"
```

**Output:**
```text
[1, 2, 2, 1]
```

**Explanation:**
- `X[1]` (centre `b`@1): `"b"`, and `"aba"` using `a@0` / `a@2` → **2**.
- `X[2]` (centre `a`@2): `"a"`, and `"bab"` using `b@1` / `b@3` → **2**.
- The ends `X[0]` and `X[3]` have an empty side, so each is just the single centre → **1**.

---

## Input Format

- A single string `A` of length `N` (lowercase English letters).

## Output Format

- An integer array `X` of length `N`, each value taken modulo `10^9 + 7`.

---

## Constraints

- `1 <= N <= 10^3`
- `A` consists of lowercase English letters.
- Report every `X[i]` modulo `10^9 + 7`.

---

## Key Points

1. An odd palindrome centred at `i` = `A[i]` + a left subsequence whose reverse is a right subsequence — count **mirrored wing pairs**, empty pair included.
2. `X[i]` equals the number of **common subsequences** (including empty) of the reversed prefix `A[i-1..0]` and the suffix `A[i+1..N-1]`.
3. A single joint table gives all centres: `dp[l][r]` = wing pairs using left indices `<= l` and right indices `>= r`; then `X[i] = dp[i-1][i+1]`.
4. The recurrence **subtracts** an overlap term — add `MOD` before the modulo so it never goes negative.

---

## Approach Hints

### Required idea: joint 2D DP over left/right boundaries

Let `dp[l][r]` = number of mirrored wing pairs using left indices in `[0..l]` and right indices in `[r..N-1]`. Sentinel `dp[l][r] = 1` whenever `l < 0` or `r > N-1` (empty side → only the empty wing).

```text
for l in 0 .. N-1:                 // ascending
    for r in N-1 .. 0:             // descending
        dp[l][r] = dp[l-1][r] + dp[l][r+1] - dp[l-1][r+1]      // include-exclude
        if A[l] == A[r]:
            dp[l][r] += dp[l-1][r+1]                            // this pair matches
        dp[l][r] %= MOD            // add MOD before % to keep it non-negative
X[i] = dp[i-1][i+1]                 // ends use the sentinel (= 1)
```

### Why the recurrence

- `dp[l-1][r] + dp[l][r+1] - dp[l-1][r+1]` counts pairs not using both `A[l]` and `A[r]` (inclusion–exclusion); when `A[l] == A[r]` you may additionally pair them, adding `dp[l-1][r+1]`.

---

## Complexity Analysis

- **Joint 2D DP (intended):** Time `O(N^2)`, Space `O(N^2)` (reducible with rolling boundaries).
- **Per-centre common-subsequence DP:** `O(N^3)` — an `O(N^2)` table for each of `N` centres.
- **Naive (enumerate subsequences):** exponential — infeasible.
