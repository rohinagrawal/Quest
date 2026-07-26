# Maximum Sum Contiguous Subarray (Kadane's Algorithm)

## Problem Description

Given an integer array `A` of length `N`, find the maximum sum of a **contiguous non-empty** subarray. The subarray must be a run of adjacent elements; you may not skip indices. Solve it in one pass using **Kadane's algorithm**, which tracks the best subarray ending at each index.

---

## Examples

### Example 1

**Input:**
```text
A = [1, 2, 3, 4, -10]
```

**Output:**
```text
10
```

**Explanation:**
- The subarray `[1, 2, 3, 4]` sums to `10`.
- Appending `-10` would drop the sum to `0`, so the best window stops at index 3.

### Example 2

**Input:**
```text
A = [-2, 1, -3, 4, -1, 2, 1, -5, 4]
```

**Output:**
```text
6
```

**Explanation:**
- The subarray `[4, -1, 2, 1]` sums to `6`.
- Kadane resets the running sum to `0` whenever it would go negative, discarding the `-2` and `-3` prefixes that only hurt.

---

## Input Format

- A single integer array `A`.

## Output Format

- An integer: the maximum contiguous subarray sum.

---

## Constraints

- `1 <= N <= 10^6`
- `-10^3 <= A[i] <= 10^3`

---

## Key Points

1. The subarray must be **non-empty**, so the answer can be negative (all-negative arrays return the largest single element).
2. **Reset** the running sum to `0` when it turns negative — a negative prefix can never improve a later window.
3. Track `best` separately from the running sum so an all-negative array still returns its max element.

---

## Approach Hints

### Required idea: Kadane's running maximum

```text
best = A[0]; cur = A[0]
for i in 1..N-1:
    cur = max(A[i], cur + A[i])   // extend or restart at i
    best = max(best, cur)
return best
```

---

## Complexity Analysis

- **Kadane (intended):** Time `O(N)`, Space `O(1)`.
- **Naive (all subarrays):** `O(N^2)` or `O(N^3)` — infeasible at `N = 10^6`.
