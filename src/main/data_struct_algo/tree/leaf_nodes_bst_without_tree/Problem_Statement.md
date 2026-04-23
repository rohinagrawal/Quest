# Find Leaf Nodes of a BST from Preorder Traversal

## Problem Description
Given an array `preorder[]` representing the preorder traversal of a Binary Search Tree (BST), return all leaf nodes of the BST **in the order they appear in preorder traversal**.

You must solve this problem **without explicitly constructing the BST**.

A node is a **leaf** if it has no left or right child.

## Examples

### Example 1:
```
Input: preorder = [8, 5, 1, 7, 10, 12]
Output: [1, 7, 12]
Explanation: The BST formed by this preorder has leaf nodes 1, 7, and 12.
```

### Example 2:
```
Input: preorder = [10, 5, 1, 7, 40, 50]
Output: [1, 7, 50]
Explanation: The leaf nodes of the BST are 1, 7, and 50.
```

### Example 3:
```
Input: preorder = [15]
Output: [15]
Explanation: A single-node BST has exactly one leaf node.
```

## Constraints
- `1 <= preorder.length <= 10^5`
- `1 <= preorder[i] <= 10^9`
- All values in `preorder` are unique
- `preorder` is guaranteed to represent a valid BST preorder traversal

## Note
- Do not build the tree structure explicitly
- Aim for an efficient solution in linear time
