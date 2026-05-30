# Merge K Sorted Arrays

## Problem Description

Given **k sorted arrays**, merge them into a single sorted array.

Each array is sorted in **ascending order**. The goal is to efficiently combine all elements from all arrays into one sorted output array.

---

## Input Format

- An array of **k sorted arrays**
- Each array contains integers in ascending order
- Arrays can have different lengths

---

## Output Format

Return a single sorted array containing all elements from all input arrays.

---

## Examples

### Example 1:

**Input:**
```
arrays = [
  [1, 4, 7],
  [2, 5, 8],
  [3, 6, 9]
]
```

**Output:**
```
[1, 2, 3, 4, 5, 6, 7, 8, 9]
```

**Explanation:** All elements from the three arrays are merged in sorted order.

### Example 2:

**Input:**
```
arrays = [
  [1, 3, 5, 7],
  [2, 4, 6],
  [0, 9, 10, 11]
]
```

**Output:**
```
[0, 1, 2, 3, 4, 5, 6, 7, 9, 10, 11]
```

**Explanation:** The three arrays are merged maintaining sorted order.

### Example 3:

**Input:**
```
arrays = [
  [1],
  [2],
  [3]
]
```

**Output:**
```
[1, 2, 3]
```

---

## Constraints

- `1 <= k <= 10^4` (number of arrays)
- `0 <= arrays[i].length <= 500` (length of each array)
- `-10^9 <= arrays[i][j] <= 10^9` (element values)
- Each array is sorted in **ascending order**

---

## Approach Hints

### Naive Approach:
- Concatenate all arrays and sort the result
- **Time Complexity:** O(N log N), where N is the total number of elements
- **Space Complexity:** O(N)

### Optimized Approach (Using Min-Heap):
- Use a min-heap to track the smallest element from each array
- Initialize the heap with the first element from each array
- Repeatedly extract the minimum and add the next element from the same array
- **Time Complexity:** O(N log k), where N is total elements and k is the number of arrays
- **Space Complexity:** O(k) for the heap

### Key Insight:
Since the arrays are already sorted, we can leverage a heap to efficiently find the next smallest element among all arrays without sorting everything from scratch.

---

## Complexity Analysis

**Optimal Solution (Min-Heap):**
- **Time Complexity:** O(N log k)
  - N total elements, each element is inserted and removed from heap once
  - Each heap operation takes O(log k) time
- **Space Complexity:** O(k) for the heap + O(N) for the output array

---

## Follow-up

- Can you solve it without using extra space for the heap? (In-place merge)
- What if the arrays are sorted in descending order instead?
- How would you handle streaming data where arrays arrive one at a time?