# Reverse Nodes in k-Group
---

## Problem Description

Given the `head` of a linked list, reverse the nodes of the list **k at a time**, and return the modified list.

`k` is a positive integer and is less than or equal to the length of the linked list. If the number of nodes is not a multiple of `k`, then the left-out nodes at the end should remain as they are (not reversed).

**Important:** You may **not** alter the values in the list's nodes, only the nodes themselves may be changed.

---

## Examples

### Example 1:

```
Input:  1 -> 2 -> 3 -> 4 -> 5, k = 2

Visual representation:
Before: 1 -> 2 -> 3 -> 4 -> 5
         [---]    [---]    |
         group1   group2   leftover

After:  2 -> 1 -> 4 -> 3 -> 5
```

**Input:** `head = [1,2,3,4,5], k = 2`  
**Output:** `[2,1,4,3,5]`  
**Explanation:** The first two pairs (1,2) and (3,4) are reversed. Node 5 is left as-is since it doesn't form a complete group of size k.

### Example 2:

```
Input:  1 -> 2 -> 3 -> 4 -> 5, k = 3

Visual representation:
Before: 1 -> 2 -> 3 -> 4 -> 5
         [--------]    [---]
         group1        leftover

After:  3 -> 2 -> 1 -> 4 -> 5
```

**Input:** `head = [1,2,3,4,5], k = 3`  
**Output:** `[3,2,1,4,5]`  
**Explanation:** The first three nodes (1,2,3) are reversed to (3,2,1). Nodes 4 and 5 remain unchanged as they don't form a complete group.

### Example 3:

**Input:** `head = [], k = 1`  
**Output:** `[]`  
**Explanation:** Empty list remains empty.

### Example 4:

**Input:** `head = [1], k = 1`  
**Output:** `[1]`  
**Explanation:** Single node with k=1 remains unchanged.

---

## Constraints

- The number of nodes in the list is `n`
- `1 <= k <= n <= 5000`
- `0 <= Node.val <= 1000`

---

## Key Points

1. **Complete Groups Only:** Only reverse groups that have exactly `k` nodes. Any remaining nodes at the end (less than `k`) stay in their original order.

2. **Pointer Manipulation:** You must change the node pointers themselves, not swap values.

3. **Edge Cases to Consider:**
   - Empty list or single node
   - k = 1 (no reversal needed)
   - k equals the length of the list (reverse entire list)
   - List length not divisible by k

---

## Approach Hints

### Iterative Approach:
- Count nodes in groups of k
- Reverse each complete group
- Connect reversed groups together
- Handle remaining nodes

### Recursive Approach:
- Recursively process k nodes at a time
- Reverse the current k-group
- Connect to the result of the recursive call on remaining nodes

---

## Complexity Analysis

**Time Complexity:** O(n)  
- We visit each node once during the reversal process

**Space Complexity:**  
- Iterative: O(1) - only uses a constant amount of extra space
- Recursive: O(n/k) - recursion stack depth equals the number of groups

---

## Follow-up Challenge

Can you solve the problem in **O(1) extra memory space** (constant space)?  
*Hint: Use an iterative approach instead of recursion.*
