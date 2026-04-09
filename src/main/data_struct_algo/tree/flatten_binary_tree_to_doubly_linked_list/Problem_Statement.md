# Flatten Binary Tree to Doubly Linked List

## Problem Description
Given the `root` of a binary tree, flatten it **in place** into a doubly linked list.

In the flattened list:
- Each node's `left` pointer should act as `prev`
- Each node's `right` pointer should act as `next`
- The node order should follow **preorder traversal** (`root -> left -> right`)

Return the `head` of the flattened doubly linked list.

## Example

```
Input Tree:
      1
     / \
    2   5
   / \   \
  3   4   6

Output List (head = 1):
1 <-> 2 <-> 3 <-> 4 <-> 5 <-> 6
```

## Constraints

- The number of nodes in the tree is in the range `[0, 10^5]`
- `-10^9 <= Node.val <= 10^9`

## Notes

- Do not create new nodes
- Rearrange pointers only
