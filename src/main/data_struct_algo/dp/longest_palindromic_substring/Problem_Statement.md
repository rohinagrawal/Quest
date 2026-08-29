# Longest Palindromic Substring

## Problem Description

Given a string `A`, return the **longest contiguous substring** of `A` that is a palindrome (reads the same forwards and backwards). If several substrings tie for the longest, returning any one of them is acceptable (this statement returns the **leftmost**).

Unlike the palindromic *subsequence*, a substring must be a **contiguous** run of characters. Solve it with **interval DP**: `dp[i][j]` is `true` iff `A[i..j]` is a palindrome, which holds when the two ends match **and** the inner substring `A[i+1..j-1]` is itself a palindrome. Track the longest interval found. (An equivalent `O(1)`-space method **expands around each center**.)

---

## Examples

### Example 1

**Input:**
```text
A = "babad"
```

**Output:**
```text
"bab"
```

**Explanation:**
- `"bab"` (indices `0..2`) is a palindrome of length `3`; `"aba"` (indices `1..3`) also qualifies.
- Both are length `3`, so either is valid — the leftmost is `"bab"`.

### Example 2

**Input:**
```text
A = "cbbd"
```

**Output:**
```text
"bb"
```

**Explanation:**
- `"bb"` (indices `1..2`) is the longest palindromic substring.
- Ends `A[1]='b'` and `A[2]='b'` match and the inner interval is empty, so `dp[1][2] = true`.

---

## Input Format

- A single string `A`.

## Output Format

- The longest palindromic substring (any one of the longest if tied).

---

## Constraints

- `1 <= |A| <= 10^3`
- Strings consist of printable characters (commonly lowercase English letters).

---

## Key Points

1. **Substring, not subsequence** — the characters must be contiguous, so `"bb"` in `"cbbd"` counts but skipping is not allowed.
2. `A[i..j]` is a palindrome iff `A[i] == A[j]` **and** (`j - i < 2` or `A[i+1..j-1]` is a palindrome).
3. Fill intervals by **increasing length** so the inner sub-interval is already computed; track the best `(start, length)`.
4. Every single character is a palindrome, so the answer has length `>= 1`.

---

## Approach Hints

### Required idea: interval DP on `A[i..j]`

```text
best = (0, 1)                       // (start, length)
for i in n-1 .. 0:
    dp[i][i] = true
    for j in i+1 .. n-1:
        if A[i] == A[j] and (j - i < 2 or dp[i+1][j-1]):
            dp[i][j] = true
            if j - i + 1 > best.length: best = (i, j - i + 1)
return A.substring(best.start, best.length)
```

### Alternative: expand around center

- For each of the `2n-1` centers (each character and each gap), expand outward while the ends match; keep the widest span. This is `O(n^2)` time but `O(1)` space.

---

## Complexity Analysis

- **Interval DP (intended):** Time `O(n^2)`, Space `O(n^2)`.
- **Expand around center:** Time `O(n^2)`, Space `O(1)` — same time, less memory.
- **Manacher's algorithm:** Time `O(n)` — optimal but intricate.
