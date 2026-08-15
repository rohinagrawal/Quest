# Flatten Binary Tree to Doubly Linked List

## Problem Description
Given the `root` of a binary tree, flatten it **in place** into a doubly linked list.

In the flattened list:
- Each node's `left` pointer should act as `prev`
- Each node's `right` pointer should act as `next`
- The node order should follow **preorder traversal** (`root -> left -> right`)

Return the `head` of the flattened doubly linked list.

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
1 <-> 2 <-> 3 <-> 4 <-> 5 <-> 6   (head = 1)
```

**Explanation:**
- Preorder visit order is `1, 2, 3, 4, 5, 6`.
- Each node's `left` is rewired to the previous node (`prev`) and `right` to the next (`next`); no new nodes are created.

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
- An empty tree flattens to an empty list, so the returned head is `null`.

## Input Format

- `root` — the root of a binary tree (may be `null`); each node has `val`, `left`, `right`.

## Output Format

- The head of the flattened doubly linked list, where `left` acts as `prev` and `right` as `next`, in preorder.

---

## Constraints

- The number of nodes in the tree is in the range `[0, 10^5]`
- `-10^9 <= Node.val <= 10^9`

---

## Key Points

1. Output order is **preorder**; each node's `left` becomes `prev` and `right` becomes `next`.
2. **In place** — rewire pointers on the existing nodes; do not allocate new ones.
3. Carry a running `prev` node through the traversal to link consecutive nodes both ways.

---

## Approach Hints

### Required idea: preorder traversal with a running `prev`

```text
prev = null
dfs(node):
    if node == null: return
    left, right = node.left, node.right    // save before rewiring
    node.left = prev
    if prev != null: prev.right = node
    prev = node
    dfs(left); dfs(right)
```

### Note

- Save the original `left`/`right` **before** overwriting them, or the recursion loses its subtrees.

---

## Complexity Analysis

- **Recursive preorder (intended):** Time `O(n)`, Space `O(h)` for the call stack (`h` = tree height).
- **Morris preorder:** `O(n)` time, `O(1)` space by threading — no recursion stack.
