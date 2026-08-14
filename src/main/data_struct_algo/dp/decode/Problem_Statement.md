# Decode Ways

## Problem Description

A message is encoded with the mapping `a = 1, b = 2, ..., z = 26`. Given a **digit string** `s`, count the number of ways it can be decoded back into letters. At each position you may consume **one** digit (if it forms `1..9`) or **two** digits (if they form `10..26`). Return the total number of valid decodings.

This is a **1D dynamic programming** problem: `dp[i]` = number of ways to decode the prefix of length `i`, built from `dp[i-1]` (take one digit) plus `dp[i-2]` (take two digits), each added only when that piece is a **valid** code. The digit `0` is the crux — it has no standalone letter, so it is only decodable as part of `10` or `20`.

---

## Examples

### Example 1

**Input:**
```text
s = "226"
```

**Output:**
```text
3
```

**Explanation:**
- Splits: `2|2|6` → `"bbf"`, `22|6` → `"vf"`, `2|26` → `"bz"`.

```text
dp[0]=1
dp[1]=1  ("2")
dp[2]=2  ("2 2" | "22")
dp[3]=3  (+dp[2] for "6", +dp[1] for "26")
```

- Both the single digit `6` and the pair `26` are valid at the end, so `dp[3] = dp[2] + dp[1] = 2 + 1 = 3`.

### Example 2

**Input:**
```text
s = "06"
```

**Output:**
```text
0
```

**Explanation:**
- `0` cannot start a code (no letter maps to `0`), and `06` is not in the range `10..26`.
- With no valid first step, there are **0** decodings — a leading `0` (or any `0` not preceded by `1`/`2`) kills the string.

---

## Input Format

- `s` — a non-empty string of digits `'0'..'9'`.

## Output Format

- An integer: the number of valid decodings (may be `0`).

---

## Constraints

- `1 <= |s| <= 10^5`
- `s` consists only of digits; it may contain leading or embedded zeros.
- The answer fits in a 64-bit integer (use `long`, or mod if the variant asks).

---

## Key Points

1. **`0` is special** — a single `0` is never decodable; it only survives as the second digit of `10` or `20`.
2. A two-digit code is valid only in `10..26` (so `"27"` splits only as `2|7`, and `"30"` is undecodable).
3. Order the checks: add `dp[i-1]` when `s[i-1] != '0'`; add `dp[i-2]` when `s[i-2..i-1]` is in `10..26`.

---

## Approach Hints

### Required idea: 1D DP over prefixes

```text
dp[0] = 1                       // empty string: one way
dp[1] = (s[0] != '0') ? 1 : 0
for i in 2..n:
    one = s[i-1]                // last digit
    two = number(s[i-2], s[i-1])
    if one != '0':      dp[i] += dp[i-1]
    if 10 <= two <= 26: dp[i] += dp[i-2]
return dp[n]
```

### Space optimization

- Only `dp[i-1]` and `dp[i-2]` are ever used → keep two rolling variables for `O(1)` space.

---

## Complexity Analysis

- **1D DP (intended):** Time `O(n)`, Space `O(1)` with rolling variables (`O(n)` with a table).
- **Naive recursion (try both splits):** exponential `O(2^n)` without memoization.
