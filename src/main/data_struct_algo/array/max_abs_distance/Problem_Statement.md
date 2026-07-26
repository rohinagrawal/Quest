# Maximum Value of |A[i] - A[j]| + |i - j|

## Problem Description

Given an array `A` of `N` integers, return the maximum value of `f(i, j) = |A[i] - A[j]| + |i - j|` over all pairs `1 <= i, j <= N`. A brute force over all pairs is `O(N^2)`; instead **strip the absolute values** into their sign cases and solve in `O(N)`.

Each `|·|` opens into `+` or `-`, giving four combinations, but by symmetry only two are distinct: `f` is maximized by `max((A[i]+i) - (A[j]+j))` and `max((A[i]-i) - (A[j]-j))`. For each of the two expressions, the answer is simply `max - min` over the array.

---

## Examples

### Example 1

**Input:**
```text
A = [1, 3, -1]
```

**Output:**
```text
5
```

**Explanation:**
- Using 1-indexing: `f(2,3) = |3 - (-1)| + |2 - 3| = 4 + 1 = 5`.
- Via the closed form: `A + index = [2, 5, 2]` → range `5 - 2 = 3`; `A - index = [0, 1, -4]` → range `1 - (-4) = 5`. The max is **5**.

### Example 2

**Input:**
```text
A = [2]
```

**Output:**
```text
0
```

**Explanation:**
- The only pair is `i = j = 1`, giving `f(1,1) = 0`. A single element always yields `0`.

---

## Input Format

- A single integer array `A` of size `N`.

## Output Format

- An integer: the maximum value of `f(i, j)`.

---

## Constraints

- `1 <= N <= 10^5`
- `-10^9 <= A[i] <= 10^9` (accumulate in `long` to avoid overflow)

---

## Key Points

1. Absolute values expand to sign cases; only **two** expressions matter: `A[i] + i` and `A[i] - i`.
2. For each expression the best pair is `max - min` — a single linear scan tracks both extremes.
3. `i == j` gives `0`, so the answer is never negative.

---

## Approach Hints

### Required idea: remove absolute values, track range

```text
answer = 0
for expr in { A[i] + i , A[i] - i }:      // two passes (or fuse into one)
    hi = max over i of expr
    lo = min over i of expr
    answer = max(answer, hi - lo)
return answer
```

---

## Complexity Analysis

- **Closed form (intended):** Time `O(N)`, Space `O(1)`.
- **Naive (all pairs):** `O(N^2)` — infeasible at `N = 10^5`.
