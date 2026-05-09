# Maximum XOR Subarray

## Problem Description

Given an array of integers `arr`, find the **subarray (contiguous)** which has the **maximum XOR value**.

Return the maximum XOR value.

---

## Examples

### Example 1:

**Input:** `arr = [1, 2, 3, 4]`
**Output:** `7`
**Explanation:**
The subarray [3, 4] has maximum XOR value = 3 XOR 4 = 7

### Example 2:

**Input:** `arr = [8, 1, 2, 12]`
**Output:** `15`
**Explanation:**
The subarray [1, 2, 12] has maximum XOR value = 1 XOR 2 XOR 12 = 15

### Example 3:

**Input:** `arr = [4, 6]`
**Output:** `6`
**Explanation:**
The subarray [6] has maximum XOR value = 6

---

## Constraints

- `1 <= arr.length <= 10^5`
- `0 <= arr[i] <= 2^31 - 1`