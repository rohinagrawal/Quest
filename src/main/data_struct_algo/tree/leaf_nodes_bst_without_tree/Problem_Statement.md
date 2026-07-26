# Find Leaf Nodes of a BST from Preorder Traversal

## Problem Description
Given an array `preorder[]` representing the preorder traversal of a Binary Search Tree (BST), return all leaf nodes of the BST **in the order they appear in preorder traversal**.

You must solve this problem **without explicitly constructing the BST**.

A node is a **leaf** if it has no left or right child.

## Examples

### Example 1

**Input:**
```text
preorder = [8, 5, 1, 7, 10, 12]
```

**Output:**
```text
[1, 7, 12]
```

**Explanation:**
- The BST rooted at `8` has left subtree `[5,1,7]` and right subtree `[10,12]`.
- Using the monotonic-stack rule, a value that is neither a left nor right internal parent is a leaf: `1, 7, 12`.

### Example 2

**Input:**
```text
preorder = [10, 5, 1, 7, 40, 50]
```

**Output:**
```text
[1, 7, 50]
```

**Explanation:**
- `1` and `7` are the leaves under the left subtree of `5`; `50` is the rightmost leaf.
- Output follows the order the leaves appear in the preorder array.

### Example 3

**Input:**
```text
preorder = [15]
```

**Output:**
```text
[15]
```

**Explanation:**
- A single-node BST is itself a leaf.

## Constraints
- `1 <= preorder.length <= 10^5`
- `1 <= preorder[i] <= 10^9`
- All values in `preorder` are unique
- `preorder` is guaranteed to represent a valid BST preorder traversal

## Note
- Do not build the tree structure explicitly
- Aim for an efficient solution in linear time
