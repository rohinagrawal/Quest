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

## Constraints
- The number of nodes in the tree: `0 <= n <= 10^5`
- Node values are integers: `-10^9 <= Node.val <= 10^9`
- All node values are unique

## Note
- A single node is considered a valid BST of size 1
- An empty tree has size 0
- The entire tree may or may not be a BST
