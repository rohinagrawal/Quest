# Count Subarrays with First Element as Minimum

## Problem Description

Given an array `arr[]`, find the number of subarrays where the first element is the minimum (not greater than all other elements in that subarray).

## Examples

### Example 1
**Input:** `arr[] = [1, 2, 1]`  
**Output:** `5`  
**Explanation:**
- All possible subarrays: `{1}`, `{1, 2}`, `{1, 2, 1}`, `{2}`, `{2, 1}`, `{1}`
- Subarrays meeting the condition: `{1}`, `{1, 2}`, `{1, 2, 1}`, `{2}`, `{1}`
- Count: **5**

### Example 2
**Input:** `arr[] = [1, 3, 5, 2]`  
**Output:** `8`  
**Explanation:**
- Subarrays meeting the condition:
  - Starting at index 0: `{1}`, `{1, 3}`, `{1, 3, 5}`, `{1, 3, 5, 2}`
  - Starting at index 1: `{3}`, `{3, 5}`
  - Starting at index 2: `{5}`
  - Starting at index 3: `{2}`
- Count: **8**

## Input Format

- A single integer array `arr`.

## Output Format

- An integer: the number of subarrays whose **first** element is `<=` every other element in that subarray.

---

## Constraints

- `1 <= arr.length <= 10^5`
- `-10^9 <= arr[i] <= 10^9`

---

## Key Points

1. A subarray starting at `i` qualifies iff `arr[i]` is `<=` all elements from `i` to its end index.
2. For each `i`, the valid subarrays extend right until the **first strictly smaller** element — count `= distance to that element`.
3. A **monotonic stack** finds each element's "next strictly smaller" in `O(n)`, avoiding the `O(n^2)` per-start scan.

---

## Approach Hints

### Required idea: next-smaller-element via monotonic stack

```text
total = 0
stack = []                        // indices, increasing values
for i in 0..n-1:
    while stack not empty and arr[stack.top] > arr[i]:
        j = stack.pop()
        total += i - j            // subarrays starting at j end just before i
    stack.push(i)
while stack not empty:
    j = stack.pop(); total += n - j   // extend to array end
return total
```

### Why it works

- When `arr[i]` is the first element smaller than `arr[j]`, every subarray `arr[j..i-1]` has `arr[j]` as its minimum — that's `i - j` subarrays.

---

## Complexity Analysis

- **Monotonic stack (intended):** Time `O(n)`, Space `O(n)`.
- **Naive (scan from each start):** `O(n^2)`.
