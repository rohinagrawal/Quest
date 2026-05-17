# Tree Splitting

## Problem Description

You are given an undirected **tree** with `n` nodes labeled `1` to `n`. Node `1` is the **root** (used only for orientation when describing the tree; the tree is undirected).

The tree has exactly `n - 1` edges. Edges are listed in the input in order and are numbered **`1` to `n - 1`** by that order (the first input edge is edge `1`, the second is edge `2`, and so on).

You are given `q` queries. In the `k`-th query you receive a distinct integer `j` — the index of an edge that has **not** been removed before. **Remove** edge `j` from the tree permanently.

After each removal, the graph becomes a **forest** (one or more disjoint trees). For that query, report the **size of the largest connected component** in the forest **after all removals from queries `1` through `k`** have been applied.

Equivalently: maintain the set of removed edges; after each new removal, among every current tree in the forest, take the maximum number of nodes in any single tree.

Process all queries and return one answer per query.

---

## Examples

### Example 1

**Input:**
- `n = 5`
- Edges (in order):
  1. `(1, 2)`
  2. `(1, 3)`
  3. `(1, 4)`
  4. `(3, 5)`
- `q = 2`
- Queries: remove edge `1`, then remove edge `2`

**Tree (root `1` at top):**

```
    1
   /|\
  2 3 4
    |
    5
```

**Output:** `[4, 2]`

**Explanation:**
- After removing edge `1` `(1, 2)`: components `{1, 3, 4, 5}` (size **4**) and `{2}` (size **1**) → answer **4**
- After also removing edge `2` `(1, 3)`: components `{1, 4}` (size **2**), `{3, 5}` (size **2**), and `{2}` (size **1**) → answer **2**

### Example 2

**Input:**
- `n = 4`
- Edges:
  1. `(1, 2)`
  2. `(2, 3)`
  3. `(3, 4)`
- `q = 1`
- Queries: remove edge `2`

**Output:** `[2]`

**Explanation:**
- Removing edge `2` `(2, 3)` splits the path into `{1, 2}` and `{3, 4}`, each of size **2** → answer **2**

---

## Input Format

1. Integer `n` — number of nodes
2. `n - 1` lines — each line `u v` (`1 <= u, v <= n`), describing one edge of the tree, in the order that defines edge indices `1 .. n - 1`
3. Integer `q` — number of queries
4. `q` lines — each line contains one integer `j` (`1 <= j <= n - 1`), the edge to remove in that step

All `j` values across the `q` queries are **pairwise distinct**. An edge is removed at most once.

## Output Format

- `q` integers: the *k*-th integer is the maximum component size in the forest after processing the first `k` queries

---

## Constraints

- `2 <= n <= 10^5`
- `1 <= q <= n - 1`
- `1 <= u, v <= n`
- The given edges form a valid tree
- Each query index `j` satisfies `1 <= j <= n - 1` and no `j` is repeated

---

## Notes

- Removals are **cumulative**: query `k` sees the forest produced by all `k` edge deletions so far, not only the two pieces created by the latest cut.
- Edge `j` always refers to the **original** `j`-th input edge, even after earlier edges were removed.
- A brute-force rebuild (BFS/DFS per query, skipping removed edges) costs `O(n · q)`.
- A standard optimization processes queries **in reverse** (add edges back) with a **Disjoint Set Union (DSU)** to achieve `O(n + q)` time.
