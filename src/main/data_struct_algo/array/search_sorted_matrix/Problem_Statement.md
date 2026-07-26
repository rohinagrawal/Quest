# Search a Row- and Column-Sorted Matrix

## Problem Description

Given an `N x M` matrix `A` where **every row and every column is sorted** in non-decreasing order, and a target `B`, find `B`'s position. Using **1-based indexing**, if `A[i][j] = B` return `i * 1009 + j`; if `B` occurs multiple times, return the **smallest** such `i * 1009 + j` (topmost, then leftmost). Return `-1` if `B` is absent. Expected time is **linear** in `N + M`.

Use the **staircase search**: start at the **top-right** corner. If the current value is too large, move left (drop a column); if too small, move down (drop a row). Each step eliminates a full row or column.

---

## Examples

### Example 1

**Input:**
```text
A = [ [1, 2, 3],
      [4, 5, 6],
      [7, 8, 9] ]
B = 2
```

**Output:**
```text
1011
```

**Explanation:**
- `B = 2` sits at row `1`, column `2` (1-indexed).
- Position code `= 1 * 1009 + 2 = 1011`.

### Example 2

**Input:**
```text
A = [ [1, 2],
      [3, 3] ]
B = 3
```

**Output:**
```text
2019
```

**Explanation:**
- `3` appears at `(2,1)` → `2*1009+1 = 2019` and `(2,2)` → `2020`.
- The smallest code is **2019**, so ties resolve to the top-left-most occurrence.

---

## Input Format

- Integer matrix `A` of size `N x M` (rows and columns sorted non-decreasing).
- Integer target `B`.

## Output Format

- `i * 1009 + j` (1-indexed) for the smallest such position, or `-1` if `B` is not present.

---

## Constraints

- `1 <= N, M <= 10^3`
- `-10^5 <= A[i][j] <= 10^5`
- `-10^5 <= B <= 10^5`

---

## Key Points

1. Start at the **top-right** (or bottom-left) — a corner where one direction increases and the other decreases lets each comparison discard a whole line.
2. To honor the **smallest `i*1009+j`**, keep scanning down-column for equal values; prefer the smaller row, then smaller column.
3. Binary-searching each row is `O(N log M)` — the staircase is strictly `O(N + M)`.

---

## Approach Hints

### Required idea: staircase (top-right) search

```text
i = 1, j = M                     // 1-indexed top-right
best = -1
while i <= N and j >= 1:
    if A[i][j] == B: best = i*1009 + j; j--   // look further left/up for smaller code
    elif A[i][j] > B: j--                       // too big → drop column
    else: i++                                   // too small → drop row
return best
```

---

## Complexity Analysis

- **Staircase (intended):** Time `O(N + M)`, Space `O(1)`.
- **Naive (scan all cells):** `O(N * M)` — ignores the sorted structure.
