# Maximum Frequency in Range

## Problem Description

You are given an integer array `arr` sorted in **non-decreasing** order and a list of range queries.

For each query `[L, R]`, consider the subarray `arr[L..R]` (inclusive). Find the value that appears the most times in that subarray and return **how many times** it occurs.

If several values tie for the highest count, any of them may be chosen; the answer is still that maximum count.

Process all queries and return one answer per query.

---

## Examples

### Example 1

**Input:**
- `arr = [2, 2, 2, 3, 3, 4]`
- Queries: `[0, 2]`, `[1, 4]`, `[0, 5]`

**Output:** `[3, 2, 3]`

**Explanation:**
- `[0, 2]` → subarray `[2, 2, 2]` → maximum frequency is **3**
- `[1, 4]` → subarray `[2, 2, 3, 3]` → maximum frequency is **2**
- `[0, 5]` → entire array → value `2` appears **3** times (best overall)

### Example 2

**Input:**
- `arr = [-1, 1, 1, 1, 1, 2]`
- Queries: `[0, 1]`, `[1, 4]`, `[0, 5]`

**Output:** `[1, 4, 4]`

**Explanation:**
- `[0, 1]` → `[-1, 1]` → each value appears once → **1**
- `[1, 4]` → `[1, 1, 1, 1]` → **4**
- `[0, 5]` → longest run is four `1`s → **4**

---

## Input Format

- `arr`: sorted array of integers (non-decreasing)
- `queries`: list of pairs `[L, R]` (0-based, inclusive)

## Output Format

- An integer array of length `queries.length`, where the *i*-th element is the maximum frequency for `queries[i]`

---

## Constraints

- `1 <= arr.length <= 10^5`
- `1 <= queries.length <= 10^5`
- `0 <= L <= R < arr.length`
- `arr` is sorted in non-decreasing order
- `-10^9 <= arr[i] <= 10^9`

---

## Key Points

1. **Sorted input is the key** — equal values form one contiguous block, so a value's run length is well-defined.
2. Precompute, for each index, the length of the run it belongs to; then a range-max over those run lengths answers most of the query.
3. **Boundary correction:** the leftmost/rightmost partial runs may be clipped by `[L, R]`, so bound their contribution by the actual overlap.

---

## Approach Hints

### Required idea: run-length compression + range-max segment tree

```text
compress equal values into runs; for each run know its [start, end]
build a segment tree over per-index "run length so far" (or over run boundaries)
query(L, R):
    fullMax  = range-max of complete runs strictly inside (L, R)
    leftClip  = overlap of L's run within [L, R]
    rightClip = overlap of R's run within [L, R]
    return max(fullMax, leftClip, rightClip)
```

### Boundary handling

- The first and last runs touching the range are truncated; compute their in-range length explicitly and fold into the max.

---

## Complexity Analysis

- **Segment tree (intended):** Build `O(n)`, each query `O(log n)` → `O(n + q log n)`, Space `O(n)`.
- **Naive (count per query):** `O(n)` per query → `O(n · q)`.
