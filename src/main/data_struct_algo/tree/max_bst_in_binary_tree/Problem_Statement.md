# Largest Binary Search Tree in Binary Tree

## Problem Description
Given the root of a binary tree, find the **size of the largest subtree** that is also a Binary Search Tree (BST).

A Binary Search Tree is a binary tree where:
- The left subtree of a node contains only nodes with values less than the node's value
- The right subtree of a node contains only nodes with values greater than the node's value
- Both left and right subtrees must also be Binary Search Trees

**Size** refers to the number of nodes in the subtree.

## Examples

### Example 1:
```
Input:
       10
      /  \
     5    15
    / \     \
   1   8    20

Output: 3
Explanation: The subtree rooted at node 5 with nodes [5, 1, 8] forms a valid BST of size 3.
```

### Example 2:
```
Input:
       50
      /  \
    30    60
   / \   / \
  5  20 45  70
         /
        40

Output: 4
Explanation: The subtree rooted at node 60 with nodes [60, 45, 70, 40] forms a valid BST of size 4.
```

### Example 3:
```
Input:
       1
      / \
     2   3

Output: 1
Explanation: No subtree (except single nodes) forms a valid BST. Each single node is a BST of size 1.
```

## Constraints
- The number of nodes in the tree: `0 <= n <= 10^5`
- Node values are integers: `-10^9 <= Node.val <= 10^9`
- All node values are unique

## Note
- A single node is considered a valid BST of size 1
- An empty tree has size 0
- The entire tree may or may not be a BST
