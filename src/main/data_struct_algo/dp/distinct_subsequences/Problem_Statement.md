# Distinct Subsequences

## Problem Description

Given two strings `A` and `B`, count the number of **distinct ways** to pick a subsequence of `A` that equals `B`. Two ways are different if they use a different set of indices from `A`, even when the resulting string is the same. A subsequence keeps the relative order of the chosen characters (`"ACE"` is a subsequence of `"ABCDE"`, `"AEC"` is not).

Solve it with **2D dynamic programming**: `dp[i][j]` = number of ways to form the prefix `B[0..j-1]` using the prefix `A[0..i-1]`. At each character of `A` you may **skip** it (`dp[i-1][j]`), and if it equals the current character of `B` you may also **use** it (`+ dp[i-1][j-1]`).

---

## Examples

### Example 1

**Input:**
```text
A = "rabbbit"
B = "rabbit"
```

**Output:**
```text
3
```

**Explanation:**
- `B = "rabbit"` needs two `b`s, and `A` has three `b`s at indices `2, 3, 4`. Choosing which two (in order) gives the distinct index sets:

```text
rabbit   using b at (2,3)
rabbit   using b at (2,4)
rabbit   using b at (3,4)
```

- Three distinct index selections → **3**.

### Example 2

**Input:**
```text
A = "babgbag"
B = "bag"
```

**Output:**
```text
5
```

**Explanation:**
- With `b` at indices `{0,2,4}`, `a` at `{1,5}`, `g` at `{3,6}`, the ordered triples `b < a < g` spelling `"bag"` are:

```text
(0,1,3)  (0,1,6)  (0,5,6)  (2,5,6)  (4,5,6)
```

- Five distinct index selections, all producing `"bag"`, are counted separately → **5**.

---

## Input Format

- Two strings `A` and `B`.

## Output Format

- An integer: the number of distinct subsequences of `A` equal to `B`.

---

## Constraints

- `1 <= |A|, |B| <= 10^3`
- Strings consist of English letters.
- The answer fits in a signed 64-bit integer (use `long`).

---

## Key Points

1. **Skip is always allowed:** `dp[i][j] = dp[i-1][j]` (don't use `A[i-1]`).
2. **Use only on a match:** if `A[i-1] == B[j-1]`, add `dp[i-1][j-1]` (consume both current characters).
3. **Base cases:** `dp[i][0] = 1` (empty `B` — the one empty selection); `dp[0][j>0] = 0` (can't form a non-empty `B` from empty `A`).
4. It counts **index sets**, not distinct result strings, so repeated characters in `A` multiply the ways.

---

## Approach Hints

### Required idea: 2D counting DP

```text
dp[i][0] = 1 for all i            // empty target: one way
dp[0][j] = 0 for j >= 1
for i in 1..|A|:
    for j in 1..|B|:
        dp[i][j] = dp[i-1][j]                     // skip A[i-1]
        if A[i-1] == B[j-1]:
            dp[i][j] += dp[i-1][j-1]              // also use A[i-1]
return dp[|A|][|B|]
```

### Space optimization

- Row `i` depends only on row `i-1` → keep **one row** and iterate `j` from high to low, giving `O(|B|)` space.

---

## Complexity Analysis

- **2D DP (intended):** Time `O(|A| * |B|)`, Space `O(|A| * |B|)` (or `O(|B|)` rolling).
- **Naive recursion (skip/use branches):** exponential `O(2^{|A|})` without memoization.
