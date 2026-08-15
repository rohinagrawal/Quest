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

## Input Format

- `preorder` — an integer array: the preorder traversal of a valid BST.

## Output Format

- A list of the BST's leaf values, in the order they appear in `preorder`.

---

## Constraints

- `1 <= preorder.length <= 10^5`
- `1 <= preorder[i] <= 10^9`
- All values in `preorder` are unique
- `preorder` is guaranteed to represent a valid BST preorder traversal

---

## Key Points

1. In a BST preorder, a value is a **leaf** iff it has neither a left nor a right child implied by the sequence.
2. A **monotonic stack** with an upper-bound simulates the tree: a value is a right-turn when it exceeds the popped bound, a left-turn otherwise.
3. Detect leaves without building the tree by checking whether a node is immediately followed by both a smaller (left child) and later a larger value under its bound.

---

## Approach Hints

### Required idea: simulate the BST with a bound stack

```text
stack = [];  leaves = [];  n = len(preorder)
for i in 0..n-1:
    hadRightChild = false
    lastPopped = -inf
    while stack not empty and preorder[i] > stack.top:
        lastPopped = stack.pop(); hadRightChild = true
    // a node with no left child (next is larger) and no right child is a leaf
    stack.push(preorder[i])
// a node is a leaf if the next value is neither its left child nor within its right-subtree bound
```

- Practical rule: node `preorder[i]` is a leaf when `preorder[i+1]` starts a subtree that belongs to an **ancestor** (found via the bound stack), and `i` is the last node of its own subtree.

---

## Complexity Analysis

- **Stack simulation (intended):** Time `O(n)`, Space `O(n)` for the stack.
- **Build BST then find leaves:** `O(n)` time but `O(n)` nodes constructed — the problem forbids it.
