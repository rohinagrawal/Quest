# Largest Binary Search Tree in Binary Tree

## Problem Description
Given the root of a binary tree, find the **size of the largest subtree** that is also a Binary Search Tree (BST).

A Binary Search Tree is a binary tree where:
- The left subtree of a node contains only nodes with values less than the node's value
- The right subtree of a node contains only nodes with values greater than the node's value
- Both left and right subtrees must also be Binary Search Trees

**Size** refers to the number of nodes in the subtree.

## Examples

### Example 1

**Input:**
```text
       10
      /  \
     5    15
    / \     \
   1   8    20
```

**Output:**
```text
3
```

**Explanation:**
- The subtree rooted at `5` (`[5, 1, 8]`) is a valid BST of size `3`.
- The whole tree is not a BST (`15`'s subtree is valid, but `10`'s children don't all satisfy the BST bounds), so `3` is the largest.

### Example 2

**Input:**
```text
       50
      /  \
    30    60
   / \   / \
  5  20 45  70
         /
        40
```

**Output:**
```text
4
```

**Explanation:**
- The subtree rooted at `60` (`[60, 45, 70, 40]`) is a valid BST of size `4`.
- The left subtree under `30` is also a BST but only size `3`, so the answer is `4`.

### Example 3

**Input:**
```text
       1
      / \
     2   3
```

**Output:**
```text
1
```

**Explanation:**
- No multi-node subtree satisfies the BST property, so the best is a single node → size `1`.

## Input Format

- `root` — the root of a binary tree (may be `null`); each node has `val`, `left`, `right`.

## Output Format

- An integer: the number of nodes in the largest subtree that is a valid BST.

---

## Constraints

- The number of nodes in the tree: `0 <= n <= 10^5`
- Node values are integers: `-10^9 <= Node.val <= 10^9`
- All node values are unique

---

## Key Points

1. Solve **bottom-up** — a subtree's BST-ness depends on its children's ranges, so post-order is natural.
2. Each call returns `(isBST, size, min, max)`; a node is a BST iff both children are BSTs **and** `leftMax < val < rightMin`.
3. A single node is a BST of size `1`; an empty child is a BST with `min = +inf`, `max = -inf` so bounds always pass.

---

## Approach Hints

### Required idea: post-order DFS returning (isBST, size, min, max)

```text
dfs(node):
    if node == null: return (true, 0, +inf, -inf)
    L = dfs(node.left); R = dfs(node.right)
    if L.isBST and R.isBST and L.max < node.val < R.min:
        size = L.size + R.size + 1
        best = max(best, size)
        return (true, size, min(L.min, node.val), max(R.max, node.val))
    return (false, 0, -inf, +inf)      // not a BST; poison the bounds
return best
```

---

## Complexity Analysis

- **Post-order DFS (intended):** Time `O(n)` (each node visited once), Space `O(h)` for the stack.
- **Naive (validate every subtree separately):** `O(n^2)` — re-checks overlapping subtrees.
