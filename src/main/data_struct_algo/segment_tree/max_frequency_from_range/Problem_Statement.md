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

## Notes

- The array being sorted is required for the intended **segment tree** approach: equal values form contiguous blocks, so the answer for `[L, R]` can be built from the ends of the range plus a range-maximum query on precomputed frequencies.
- For an unsorted array, different techniques (e.g. Mo's algorithm) are needed.
