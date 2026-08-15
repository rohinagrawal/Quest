# Median of Number Stream

## Problem Description

Given an integer array `A` representing a stream of numbers, find the median after each insertion.

For every prefix `A[0...i]`, compute the median of all elements seen so far and append it to the answer array.

- If the number of elements is odd, the median is the middle element of the sorted prefix.
- If the number of elements is even, the median is `floor((x + y) / 2)`, where `x` and `y` are the two middle elements.

Return the array of medians for all prefixes.

---

## Input Format

- A single integer array `A`

---

## Output Format

Return an integer array where the `i-th` element is the median of prefix `A[0...i]`.

---

## Examples

### Example 1

**Input:**
```text
A = [5, 17, 100, 11]
```

**Output:**
```text
[5, 11, 17, 14]
```

**Explanation:**
- After 1st element: `[5]`, median = `5`
- After 2nd element: `[5, 17]`, median = `floor((5 + 17) / 2) = 11`
- After 3rd element: `[5, 17, 100]`, median = `17`
- After 4th element: `[5, 11, 17, 100]`, median = `floor((11 + 17) / 2) = 14`

### Example 2

**Input:**
```text
A = [1, 2, 3, 4, 5]
```

**Output:**
```text
[1, 1, 2, 2, 3]
```

**Explanation:**
- `[1]` -> `1`
- `[1, 2]` -> `1`
- `[1, 2, 3]` -> `2`
- `[1, 2, 3, 4]` -> `2`
- `[1, 2, 3, 4, 5]` -> `3`

---

## Constraints

- `1 <= |A| <= 10^5`
- `-10^9 <= A[i] <= 10^9`

---

## Key Points

1. **Two heaps:** a max-heap for the lower half, a min-heap for the upper half; the median always sits at their tops.
2. Keep the sizes balanced so they differ by at most `1` — rebalance after every insert.
3. Odd count → top of the larger heap; even count → `floor((maxHeap.top + minHeap.top) / 2)`.

---

## Approach Hints

### Required idea: two-heap running median

```text
lo = max-heap (lower half);  hi = min-heap (upper half)
insert(x):
    if lo empty or x <= lo.top: lo.push(x)
    else:                       hi.push(x)
    if size(lo) > size(hi) + 1: hi.push(lo.pop())   // rebalance
    if size(hi) > size(lo):     lo.push(hi.pop())
median = size odd ? lo.top : floor((lo.top + hi.top) / 2)
```

---

## Complexity Analysis

- **Two heaps (intended):** Time `O(n log n)` total, Space `O(n)`.
- **Naive (sort each prefix):** `O(n^2 log n)` — recomputes from scratch every step.
