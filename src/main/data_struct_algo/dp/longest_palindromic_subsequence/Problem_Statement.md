# Longest Palindromic Subsequence

## Problem Description

Given a string `A`, return the length of its **longest palindromic subsequence (LPS)** — the longest subsequence (characters in order, not necessarily contiguous) that reads the same forwards and backwards.

Solve it with **interval dynamic programming**: `dp[i][j]` = LPS length within the substring `A[i..j]`. If the two ends match, they wrap a smaller palindrome; otherwise drop one end and take the better side. (Equivalently, the answer is `LCS(A, reverse(A))` — a palindrome is a subsequence shared by the string and its reverse.)

---

## Examples

### Example 1

**Input:**
```text
A = "bbbab"
```

**Output:**
```text
4
```

**Explanation:**
- The subsequence `"bbbb"` (drop the `a`) reads the same both ways → length **4**.
- Ends `A[0]='b'` and `A[4]='b'` match, so `dp[0][4] = dp[1][3] + 2`.

### Example 2

**Input:**
```text
A = "cbbd"
```

**Output:**
```text
2
```

**Explanation:**
- The subsequence `"bb"` is the longest palindrome; `c` and `d` cannot extend it.
- Ends `c` and `d` differ, so `dp[0][3] = max(dp[1][3], dp[0][2]) = 2`.

---

## Input Format

- A single string `A`.

## Output Format

- An integer: the length of the longest palindromic subsequence.

---

## Constraints

- `1 <= |A| <= 10^3`
- Strings consist of printable characters (commonly lowercase English letters).

---

## Key Points

1. **Subsequence, not substring** — you may skip characters, so `"bbbb"` counts inside `"bbbab"`.
2. Matching **ends** add `2` to the inner interval's LPS; differing ends recurse on the two `n-1`-length sub-intervals.
3. Fill intervals by **increasing length**, base case `dp[i][i] = 1` (a single character is a palindrome).
4. Shortcut: `LPS(A) = LCS(A, reverse(A))` reuses the LCS routine directly.

---

## Approach Hints

### Required idea: interval DP over `A[i..j]`

```text
for i in n-1 .. 0:
    dp[i][i] = 1
    for j in i+1 .. n-1:
        if A[i] == A[j]:
            dp[i][j] = dp[i+1][j-1] + 2
        else:
            dp[i][j] = max(dp[i+1][j], dp[i][j-1])
return dp[0][n-1]
```

### Alternative: reduce to LCS

- Compute `LCS(A, reverse(A))`; any subsequence common to a string and its reverse is a palindrome, so its length equals the LPS.

---

## Complexity Analysis

- **Interval DP (intended):** Time `O(n^2)`, Space `O(n^2)` (reducible to `O(n)` with rolling rows).
- **LCS reduction:** also `O(n^2)` time and space — same bound, reuses existing LCS code.
- **Naive recursion:** exponential without memoization.
