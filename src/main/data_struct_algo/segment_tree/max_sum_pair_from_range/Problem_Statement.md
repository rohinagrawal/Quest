# Maximum Sum Pair in Range

## Problem Description

You are given an integer array `arr` and a list of range queries.

For each query `[L, R]`, consider the subarray `arr[L..R]` (inclusive). Choose **two distinct indices** `i` and `j` such that `L <= i < j <= R`, and maximize `arr[i] + arr[j]`.

Return that maximum sum for every query.

If the range contains fewer than two elements (`L == R`), there is no valid pair; treat that case as specified by your API (e.g. return `0`, `-1`, or skip such queries). The examples below assume `L < R`.

Process all queries and return one answer per query.

---

## Examples

### Example 1

**Input:**
- `arr = [1, 8, 3, 6, 5]`
- Queries: `[0, 4]`, `[0, 2]`, `[2, 4]`

**Output:** `[14, 11, 11]`

**Explanation:**
- `[0, 4]` → pick `8` and `6` → **14**
- `[0, 2]` → subarray `[1, 8, 3]` → `8 + 3` → **11**
- `[2, 4]` → subarray `[3, 6, 5]` → `6 + 5` → **11**

### Example 2

**Input:**
- `arr = [-5, -2, -10, -1]`
- Queries: `[0, 3]`, `[0, 1]`, `[2, 3]`

**Output:** `[-3, -7, -11]`

**Explanation:**
- `[0, 3]` → best pair is `-2 + (-1)` → **-3**
- `[0, 1]` → `-5 + (-2)` → **-7**
- `[2, 3]` → `-10 + (-1)` → **-11**

### Example 3 (sorted array)

**Input:**
- `arr = [2, 4, 4, 5, 7]` (non-decreasing)
- Queries: `[0, 4]`, `[1, 3]`

**Output:** `[12, 9]`

**Explanation:**
- In a sorted range, the two largest values are always at the right end: `7 + 5 = 12`, `5 + 4 = 9`

---

## Input Format

- `arr`: array of integers
- `queries`: list of pairs `[L, R]` (0-based, inclusive)

## Output Format

- An integer array of length `queries.length`, where the *i*-th element is the maximum pair sum for `queries[i]`

---

## Constraints

- `1 <= arr.length <= 10^5`
- `1 <= queries.length <= 10^5`
- `0 <= L <= R < arr.length`
- `-10^9 <= arr[i] <= 10^9`
- For each query, `L < R` (range length at least 2) unless stated otherwise

---

## Notes

- A brute-force scan per query is `O(n)` per range. With many queries, use a **segment tree**: each node stores the largest and second-largest values in its interval; merging two children also considers the best pair formed by taking the top values from opposite sides.
- If `arr` is sorted in **non-decreasing** order, the answer for `[L, R]` is simply `arr[R] + arr[R - 1]` in `O(1)` per query, but the segment-tree formulation still applies when the array is not sorted or when the structure is extended (e.g. updates).
