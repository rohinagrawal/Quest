# Maximum Weighted Triplet Sum (A[i]*B + A[j]*C + A[k]*D)

## Problem Description

Given an array `A` of `N` integers and three integers `B`, `C`, `D`, find the **maximum** value of `A[i]*B + A[j]*C + A[k]*D` over all (1-indexed) triples with `1 <= i <= j <= k <= N`. Indices may **repeat** (`i == j == k` is allowed).

Fix the **middle** index `j` and split the choice in two: the best `A[i]*B` over `i <= j` (a **prefix maximum**), and the best `A[k]*D` over `k >= j` (a **suffix maximum**). Precompute both in one pass each, then scan `j` once and combine `prefixMax[j] + A[j]*C + suffixMax[j]`, taking the overall maximum — no nested loop over `i` or `k` is needed.

---

## Examples

### Example 1

**Input:**
```text
A = [3, 1, 5]
B = 1, C = -1, D = 1
```

**Output:**
```text
7
```

**Explanation:**
- `prefixMax[i] = max(A[1..i] * B) = [3, 3, 5]`; `suffixMax[k] = max(A[k..3] * D) = [5, 5, 5]`.
- At `j = 2`: `prefixMax[2] + A[2]*C + suffixMax[2] = 3 + (1*-1) + 5 = 7` — the best over all `j`.
- This corresponds to `i=1, j=2, k=3`: `3*1 + 1*(-1) + 5*1 = 3 - 1 + 5 = 7`. A **negative** `C` makes the optimal `j` avoid the largest element.

### Example 2

**Input:**
```text
A = [-1, -2, -3]
B = 1, C = 1, D = 1
```

**Output:**
```text
-3
```

**Explanation:**
- Every element is negative, so the best choice repeats the **least negative** value: `i = j = k = 1` → `-1*1 + -1*1 + -1*1 = -3`.
- Indices are allowed to coincide, which is exactly what the optimum needs here.

---

## Input Format

- `A` — an integer array of size `N`.
- `B`, `C`, `D` — integer weights.

## Output Format

- A single integer: the maximum value of `A[i]*B + A[j]*C + A[k]*D` over `1 <= i <= j <= k <= N`.

---

## Constraints

- `1 <= N <= 10^5`
- `-10^4 <= A[i], B, C, D <= 10^4`
- `i`, `j`, `k` may be equal; the answer fits in a 64-bit integer (use `long`).

---

## Key Points

1. `i <= j <= k` still allows **repeats** — the same index can serve as `i`, `j`, and `k` at once.
2. Fixing the **middle** index `j` decouples the problem: the best `i` depends only on `[1..j]`, the best `k` only on `[j..N]`.
3. A **negative** weight can make it optimal to pick a smaller `|A[x]|`, so prefix/suffix max must use the actual **weighted** value (`A[x]*weight`), not `A[x]` alone.
4. Compute `prefixMax` left-to-right and `suffixMax` right-to-left — both `O(N)` single passes.

---

## Approach Hints

### Required idea: prefix max / suffix max around a fixed middle index

```text
prefixMax[0] = A[0]*B
for i in 1..N-1: prefixMax[i] = max(prefixMax[i-1], A[i]*B)

suffixMax[N-1] = A[N-1]*D
for k in N-2..0: suffixMax[k] = max(suffixMax[k+1], A[k]*D)

best = -infinity
for j in 0..N-1:
    best = max(best, prefixMax[j] + A[j]*C + suffixMax[j])
return best
```

---

## Complexity Analysis

- **Prefix/suffix max (intended):** Time `O(N)`, Space `O(N)` (reducible to `O(1)` extra if `prefixMax` is folded into the final scan and only `suffixMax` is precomputed).
- **Naive (triple nested loop over i, j, k):** `O(N^3)` — infeasible at `N = 10^5`.
