# Sum of All Submatrices

## Problem Description

Given an `N x N` matrix `A`, return the sum of every possible **contiguous submatrix**. Enumerating all `O(N^4)` submatrices and summing them is too slow; instead use a **contribution technique**: count how many submatrices contain each cell `(i, j)`, multiply by `A[i][j]`, and add up.

A submatrix is fixed by choosing a top boundary at or above `i`, a bottom at or below `i`, a left at or before `j`, and a right at or after `j`. With **0-based** indices in an `N x N` grid, cell `(i, j)` appears in `(i+1) * (N-i) * (j+1) * (N-j)` submatrices.

---

## Examples

### Example 1

**Input:**
```text
A = [ [1, 1],
      [1, 1] ]
```

**Output:**
```text
16
```

**Explanation:**
- Each of the 4 cells has contribution count `(i+1)(N-i)(j+1)(N-j)`; for a `2x2`, corner cells each appear in `4` submatrices.
- Total appearances `= 4 cells * 4 = 16`, and every value is `1`, so the sum is **16**.

### Example 2

**Input:**
```text
A = [ [1, 2],
      [3, 4] ]
```

**Output:**
```text
40
```

**Explanation:**
- Every cell of a `2x2` appears in exactly `4` submatrices, so the total is `4 * (1 + 2 + 3 + 4) = 4 * 10 = 40`.
- Listing them (`[1],[2],[3],[4],[1,2],[3,4],[1,3],[2,4],` full matrix) and summing confirms **40**.

---

## Input Format

- A 2D integer matrix `A` of size `N x N`.

## Output Format

- An integer: the sum over all contiguous submatrices.

---

## Constraints

- `1 <= N <= 30`
- `0 <= A[i][j] <= 10`

---

## Key Points

1. **Count contributions, don't enumerate** — each cell's multiplicity is a product of independent boundary choices.
2. With 0-based indices, multiplicity of `(i, j)` is `(i+1) * (N-i) * (j+1) * (N-j)`.
3. The row factor `(i+1)*(N-i)` and column factor `(j+1)*(N-j)` are independent, so they multiply.

---

## Approach Hints

### Required idea: per-cell contribution count

```text
total = 0
for i in 0..N-1, j in 0..N-1:
    count = (i + 1) * (N - i) * (j + 1) * (N - j)
    total += A[i][j] * count
return total
```

---

## Complexity Analysis

- **Contribution (intended):** Time `O(N^2)`, Space `O(1)`.
- **Naive (enumerate submatrices):** `O(N^4)` submatrices, each summed — far heavier.
