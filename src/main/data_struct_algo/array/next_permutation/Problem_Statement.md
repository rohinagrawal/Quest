# Next Permutation

## Problem Description

Given an array `A` of `N` integers, rearrange it **in place** into the numerically **next greater** permutation. If no greater permutation exists (the array is in descending order), rearrange it into the **lowest** order (ascending). The replacement must use `O(1)` extra memory and must not call a library `next_permutation`.

The standard algorithm finds the rightmost ascending step, swaps it with the smallest element to its right that still exceeds it, then reverses the suffix to make it the smallest possible.

---

## Examples

### Example 1

**Input:**
```text
A = [1, 2, 3]
```

**Output:**
```text
[1, 3, 2]
```

**Explanation:**
- The rightmost `A[i] < A[i+1]` is `i = 1` (`2 < 3`).
- Swap `2` with the next-larger element to its right (`3`) → `[1, 3, 2]`; the one-element suffix needs no reversal.

### Example 2

**Input:**
```text
A = [3, 2, 1]
```

**Output:**
```text
[1, 2, 3]
```

**Explanation:**
- The array is fully descending, so **no** next-greater permutation exists.
- The pivot search fails, so the whole array is reversed into ascending order `[1, 2, 3]`.

---

## Input Format

- A single integer array `A`, modified in place.

## Output Format

- The array representing the next permutation.

---

## Constraints

- `1 <= N <= 5 * 10^5`
- `1 <= A[i] <= 10^9`
- **In-place**, `O(1)` extra space; no library permutation function.

---

## Key Points

1. Scan from the **right** for the first index `i` with `A[i] < A[i+1]` — the pivot; if none, reverse all (last permutation wraps to first).
2. Swap the pivot with the **smallest element greater than it** in the suffix (which is sorted descending, so scan from the right).
3. **Reverse the suffix** after the pivot to turn it from descending into ascending — the minimal tail.

---

## Approach Hints

### Required idea: pivot, swap, reverse suffix

```text
i = N - 2
while i >= 0 and A[i] >= A[i+1]: i--        // find pivot
if i >= 0:
    j = N - 1
    while A[j] <= A[i]: j--                  // smallest > A[i] in suffix
    swap(A[i], A[j])
reverse(A, i+1, N-1)                          // make suffix ascending
```

---

## Complexity Analysis

- **Intended:** Time `O(N)`, Space `O(1)`.
- **Naive (generate & sort permutations):** factorial time — infeasible.
