# Morris Inorder Traversal (O(1) Space)

## Problem Description

Given the `root` of a binary tree, return its **inorder** traversal (`left → node → right`) using **`O(1)` extra space** — no recursion and no explicit stack. This is **Morris traversal**: temporarily rewire the tree using **threaded binary tree** links so you can walk back up to a node after finishing its left subtree, then restore the tree as you go.

For each node, find its **inorder predecessor** (the rightmost node of its left subtree). Create a temporary thread from that predecessor to the current node; when you later return via the thread, output the current node and remove the thread, leaving the tree unchanged at the end.

---

## Examples

### Example 1

**Input:**
```text
      4
     / \
    2   6
   / \  / \
  1  3 5   7
```

**Output:**
```text
[1, 2, 3, 4, 5, 6, 7]
```

**Explanation:**
- At `4`, the predecessor is `3` (rightmost of the left subtree); thread `3 → 4`, then descend left.
- After emitting `1, 2, 3`, the thread returns to `4`; emit `4`, remove the thread, and move right into `6`'s subtree → `5, 6, 7`.

### Example 2

**Input:**
```text
    1
     \
      2
     /
    3
```

**Output:**
```text
[1, 3, 2]
```

**Explanation:**
- `1` has no left child, so emit `1` immediately and go right to `2`.
- `2`'s predecessor is `3`; thread and descend left, emit `3`, return via thread, emit `2`. The tree's original shape is restored.

---

## Input Format

- `root` — the root of a binary tree (may be `null`). Each node has `val`, `left`, `right`.

## Output Format

- A list of node values in inorder sequence.

---

## Constraints

- `0 <= n <= 10^5` (number of nodes)
- `-10^9 <= Node.val <= 10^9`
- Must use `O(1)` auxiliary space (excluding the output list).

---

## Key Points

1. The **inorder predecessor** is the rightmost node of the current node's left subtree — reached by going left once, then right until the right child is null (or points back to current).
2. A predecessor's right pointer in one of two states distinguishes the two visits: `null` (first visit → thread it) vs pointing back to current (second visit → emit and unthread).
3. **Restore** every thread you create, so the tree is unmodified when traversal ends.

---

## Approach Hints

### Required idea: threaded links, no stack

```text
cur = root
while cur != null:
    if cur.left == null:
        visit(cur); cur = cur.right
    else:
        pred = rightmost node of cur.left (stop if its right == cur)
        if pred.right == null:
            pred.right = cur          // create thread
            cur = cur.left
        else:
            pred.right = null         // remove thread
            visit(cur); cur = cur.right
```

---

## Complexity Analysis

- **Morris (intended):** Time `O(n)` (each edge traversed at most a constant number of times), Space `O(1)`.
- **Recursive / stack inorder:** `O(n)` time but `O(h)` space for the call stack or explicit stack.
