# Maximum Product Subarray

## Problem Description

Given an integer array `A` of size `N`, find the **contiguous** subarray (containing at least one number) with the **largest product**, and return that product. The array may contain negative numbers and zeros.

Unlike the maximum-**sum** subarray, the product problem must track **two** running values per index: the largest and the **smallest** product ending here. A large negative "smallest" can become the new maximum the moment it is multiplied by another negative, so both extremes must be carried. Zeros reset both to consider restarting the subarray at the next element.

---

## Examples

### Example 1

**Input:**
```text
A = [2, 3, -2, 4]
```

**Output:**
```text
6
```

**Explanation:**
- The subarray `[2, 3]` gives `2 * 3 = 6`.
- Extending across `-2` would drop the product negative, so the best window stops before it — max product is **6**.

### Example 2

**Input:**
```text
A = [-2, 0, -1]
```

**Output:**
```text
0
```

**Explanation:**
- Every subarray spanning two elements includes a `0` or a single negative, so the best single value is `0`.
- The `0` also **resets** the running products; no window beats `0` here.

---

## Input Format

- A single integer array `A` of size `N`.

## Output Format

- An integer: the maximum product over all contiguous subarrays.

---

## Constraints

- `1 <= N <= 10^5`
- `-10 <= A[i] <= 10` (the running product fits in a 64-bit integer; use `long`)
- The subarray must be **non-empty**.

---

## Key Points

1. **Track max and min together** — a negative number swaps their roles, so the current min can become the next max.
2. On each element consider three candidates: the element alone, `maxPrev * x`, and `minPrev * x`.
3. A **zero** forces both running products to restart (the element alone, `0`), effectively cutting the array.
4. The answer can be a single element (all-negative or all-zero arrays), since the subarray must be non-empty.

---

## Approach Hints

### Required idea: DP tracking max and min ending here

```text
maxHere = minHere = ans = A[0]
for i in 1 .. N-1:
    x = A[i]
    if x < 0: swap(maxHere, minHere)     // negative flips extremes
    maxHere = max(x, maxHere * x)
    minHere = min(x, minHere * x)
    ans = max(ans, maxHere)
return ans
```

- Equivalent without the swap: `maxHere = max(x, max(maxPrev*x, minPrev*x))`, `minHere = min(x, min(maxPrev*x, minPrev*x))` using the **old** values.

---

## Complexity Analysis

- **Two-track DP (intended):** Time `O(N)`, Space `O(1)`.
- **Prefix/suffix product scan:** also `O(N)` — take the max over left-to-right and right-to-left running products, resetting on zeros.
- **Naive (all subarrays):** `O(N^2)` products — infeasible at `N = 10^5`.
