# Maximum Consecutive Gap (Sorted Form)

## Problem Description

Given an unsorted array `A` of `N` non-negative integers, find the **maximum difference between successive elements** in its sorted form. Return `0` if the array has fewer than `2` elements. The challenge is to do it in **linear time and space** — without a comparison sort.

Use **bucket sort by the pigeonhole principle**: if the values span range `[min, max]`, then across `N` elements the largest gap is at least `ceil((max - min) / (N - 1))`. Place elements into `N - 1` buckets of that width; the maximum gap must span **between** buckets (across an empty one), so only each bucket's min and max matter — never two elements inside the same bucket.

---

## Examples

### Example 1

**Input:**
```text
A = [1, 10, 5]
```

**Output:**
```text
5
```

**Explanation:**
- Sorted form is `[1, 5, 10]`; successive gaps are `5 - 1 = 4` and `10 - 5 = 5`.
- The maximum is **5**.

### Example 2

**Input:**
```text
A = [10, 9, 10]
```

**Output:**
```text
1
```

**Explanation:**
- Sorted form is `[9, 10, 10]`; gaps are `1` and `0`.
- Duplicate values give a `0` gap, so the maximum is **1**.

---

## Input Format

- A single integer array `A` of size `N`.

## Output Format

- An integer: the maximum successive gap in sorted order; `0` if `N < 2`.

---

## Constraints

- `1 <= N <= 10^6`
- `1 <= A[i] <= 10^9`

---

## Key Points

1. **Edge case first:** `N < 2` returns `0`.
2. The max gap is `>= ceil((max - min) / (N - 1))`, so bucket width `= that value` guarantees the answer lies **across** buckets.
3. Only each bucket's **min and max** matter; gaps within a bucket are always smaller than the bucket width.

---

## Approach Hints

### Required idea: pigeonhole bucketing

```text
lo, hi = min(A), max(A)
if hi == lo: return 0
width = max(1, ceil((hi - lo) / (N - 1)))
for each x: put into bucket (x - lo) / width, updating that bucket's min and max
answer = max over consecutive non-empty buckets of (nextBucketMin - prevBucketMax)
```

---

## Complexity Analysis

- **Bucket / pigeonhole (intended):** Time `O(N)`, Space `O(N)`.
- **Sort then scan:** `O(N log N)` — correct but not the linear target.
