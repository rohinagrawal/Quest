# Flatten Binary Tree to Linked List

## Problem Description

Given the `root` of a binary tree, flatten it **in place** into a "linked list" that reuses the same `TreeNode` structure: each node's `right` pointer points to the next node and each `left` pointer is set to `null`. The order must match the tree's **preorder traversal** (`node → left → right`).

The elegant solution rewires pointers with `O(1)` extra space using the **Morris-style right-threading**: for each node with a left child, find the **rightmost node of the left subtree**, attach the current right subtree to it, then move the left subtree to the right and null out the left.

---

## Examples

### Example 1

**Input:**
```text
      1
     / \
    2   5
   / \   \
  3   4   6
```

**Output:**
```text
1 -> 2 -> 3 -> 4 -> 5 -> 6   (all left pointers null)
```

**Explanation:**
- Preorder order is `1, 2, 3, 4, 5, 6`.
- At `1`, the left subtree's rightmost node is `4`; splice `5,6` after `4`, move `2,3,4` to the right, and set `1.left = null`. Repeating flattens the whole tree.

### Example 2

**Input:**
```text
   (empty tree)
```

**Output:**
```text
null
```

**Explanation:**
- An empty tree is already flat; the root stays `null` and nothing is rewired.

---

## Input Format

- `root` — the root of a binary tree (may be `null`). Each node has `val`, `left`, `right`.

## Output Format

- The tree modified in place: a right-leaning chain in preorder, every `left` pointer `null`.

---

## Constraints

- `0 <= n <= 10^5` (number of nodes)
- `-10^9 <= Node.val <= 10^9`
- Rearrange existing pointers only — do **not** allocate new nodes.

---

## Key Points

1. Output order is **preorder**, so the left subtree must sit before the original right subtree in the chain.
2. The `O(1)`-space trick: attach the right subtree to the **rightmost node of the left subtree**, then hoist the left subtree up as the new right.
3. After moving, set `left = null` on the node you processed to satisfy the "linked list" shape.

---

## Approach Hints

### Required idea: right-threading (Morris-style)

```text
cur = root
while cur != null:
    if cur.left != null:
        pred = rightmost node of cur.left
        pred.right = cur.right     // splice old right after left subtree
        cur.right = cur.left       // left subtree becomes new right
        cur.left = null
    cur = cur.right
```

---

## Complexity Analysis

- **Threading (intended):** Time `O(n)`, Space `O(1)`.
- **Reverse-preorder recursion:** `O(n)` time but `O(h)` recursion stack.
