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

## Constraints

- `1 ≤ arr.length ≤ 10^5`
- `-10^9 ≤ arr[i] ≤ 10^9`

## Approach

For each starting position `i`, count consecutive elements where `arr[i]` remains the minimum. Use a stack-based or greedy approach for optimal O(n) time complexity.
