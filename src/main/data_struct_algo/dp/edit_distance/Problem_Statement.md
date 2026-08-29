# Edit Distance

## Problem Description

Given two strings `A` and `B`, return the **minimum number of operations** to convert `A` into `B`. Each of the following counts as one step, applied to any position:

- **Insert** a character
- **Delete** a character
- **Replace** a character

This is the **Levenshtein edit distance**, solved with **2D dynamic programming**: `dp[i][j]` = minimum operations to turn the prefix `A[0..i-1]` into `B[0..j-1]`. When the current characters match, no operation is needed and you inherit the diagonal; otherwise you take `1 +` the cheapest of the three edits.

---

## Examples

### Example 1

**Input:**
```text
A = "horse"
B = "ros"
```

**Output:**
```text
3
```

**Explanation:**
- `horse → rorse` (replace `h`→`r`), `rorse → rose` (delete `r`), `rose → ros` (delete `e`) — **3** steps.

```text
       ""  r  o  s
   ""  0   1  2  3
   h   1   1  2  3
   o   2   2  1  2
   r   3   2  2  2
   s   4   3  3  2
   e   5   4  4  3
```

- `dp[5][3] = 3` — the bottom-right cell is the answer.

### Example 2

**Input:**
```text
A = "intention"
B = "execution"
```

**Output:**
```text
5
```

**Explanation:**
- One optimal sequence: delete `t`, replace `i`→`e`, `n`→`x`, `e`→`c`, and insert `u` — **5** operations.
- No shorter sequence of insert/delete/replace edits exists.

---

## Input Format

- Two strings `A` and `B`.

## Output Format

- An integer: the minimum number of edit operations to transform `A` into `B`.

---

## Constraints

- `0 <= |A|, |B| <= 10^3`
- Strings consist of lowercase English letters.

---

## Key Points

1. **Base cases:** converting to/from an empty string costs its length — `dp[i][0] = i` (all deletes), `dp[0][j] = j` (all inserts).
2. On a **match**, `dp[i][j] = dp[i-1][j-1]` (free); the characters align with no edit.
3. On a **mismatch**, `dp[i][j] = 1 + min(replace = dp[i-1][j-1], delete = dp[i-1][j], insert = dp[i][j-1])`.
4. The three neighbors map exactly to the three operations — diagonal/replace, up/delete, left/insert.

---

## Approach Hints

### Required idea: 2D edit-distance DP

```text
for i in 0..|A|: dp[i][0] = i          // delete all of A
for j in 0..|B|: dp[0][j] = j          // insert all of B
for i in 1..|A|:
    for j in 1..|B|:
        if A[i-1] == B[j-1]:
            dp[i][j] = dp[i-1][j-1]
        else:
            dp[i][j] = 1 + min(dp[i-1][j-1],   // replace
                               dp[i-1][j],     // delete from A
                               dp[i][j-1])     // insert into A
return dp[|A|][|B|]
```

### Space optimization

- Each row depends only on the previous row → keep **two rows** (plus the saved diagonal) for `O(min(|A|, |B|))` space.

---

## Complexity Analysis

- **2D DP (intended):** Time `O(|A| * |B|)`, Space `O(|A| * |B|)` (or `O(min(|A|,|B|))` rolling).
- **Naive recursion (branch on 3 edits):** exponential `O(3^{max(|A|,|B|)})` without memoization.
