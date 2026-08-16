# Maximum Sum with No Adjacent Cells (2 x N Grid)

## Problem Description

You are given a `2 x N` grid of integers `A`. Choose a set of cells to **maximize their sum**, subject to the rule that **no two chosen cells may be adjacent** horizontally, vertically, or diagonally. You may choose any number of cells (more than two is allowed), or none.

The key observation collapses this to one dimension: in a `2 x N` grid, **any** cell in column `i` is adjacent to **both** cells of columns `i-1` and `i+1` (horizontally and diagonally) and to the other cell in its own column (vertically). So (1) you may take **at most one** cell per column, and (2) you may **not** take cells from two consecutive columns. Let `best[i] = max(A[0][i], A[1][i])`; the problem becomes the classic **House Robber** DP over `best`: pick a maximum-sum subset of `best` with no two adjacent indices.

---

## Examples

### Example 1

**Input:**
```text
A = [ [1, 2, 3, 4],
      [4, 3, 2, 1] ]
```

**Output:**
```text
8
```

**Explanation:**
- Column bests: `best = [max(1,4), max(2,3), max(3,2), max(4,1)] = [4, 3, 3, 4]`.

```text
col:   0    1    2    3
top:   1    2    3    4
bot:   4    3    2    1
best:  4    3    3    4
```

- Take columns `0` and `3` (non-adjacent) → cells `A[1][0]=4` and `A[0][3]=4` → sum **8**, beating `4+3=7` from columns `0` and `2`.

### Example 2

**Input:**
```text
A = [ [3, 1],
      [1, 3] ]
```

**Output:**
```text
3
```

**Explanation:**
- `best = [3, 3]`, but columns `0` and `1` are adjacent, so at most one can be used.
- Picking `A[0][0]=3` and `A[1][1]=3` is illegal — they are **diagonally** adjacent — so the answer is a single `3`.

---

## Input Format

- `A` — a `2 x N` integer grid (`A[0]` is the top row, `A[1]` the bottom row).

## Output Format

- A single integer: the maximum achievable sum (`0` if choosing nothing is best).

---

## Constraints

- `1 <= N <= 10^5`
- `-10^4 <= A[i][j] <= 10^4`
- Cells are adjacent if they touch horizontally, vertically, or diagonally (8-neighborhood).

---

## Key Points

1. **Per column, take at most one** cell — the two are vertically adjacent; always the larger, so compress to `best[i] = max(A[0][i], A[1][i])`.
2. **No two consecutive columns** — every cell of column `i` touches every cell of column `i±1`, so the whole column is blocked once a neighbor is used.
3. After compression it is exactly **House Robber** on `best`; negative columns are simply skipped, and choosing nothing yields `0`.

---

## Approach Hints

### Required idea: compress columns, then House Robber DP

```text
best[i] = max(A[0][i], A[1][i])          // one value per column

dp[i] = max( dp[i-1],                     // skip column i
             dp[i-2] + best[i] )          // take column i (skip i-1)
answer = dp[N-1]
```

### Space optimization

- Only `dp[i-1]` and `dp[i-2]` are used → two rolling variables give `O(1)` space:

```text
prev2 = 0; prev1 = 0
for v in best:
    cur = max(prev1, prev2 + v)
    prev2 = prev1; prev1 = cur
return prev1
```

- Using `max(prev1, prev2 + v)` (never forcing a pick) lets negative columns and the empty selection resolve to `0` naturally.

---

## Complexity Analysis

- **Compress + House Robber (intended):** Time `O(N)`, Space `O(1)` with rolling variables.
- **Naive (enumerate valid subsets):** exponential — infeasible.
