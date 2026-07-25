# Maximum Stones Removed (Same Row or Column)

## Problem Description

You are given `n` stones placed on a 2D plane; stone `i` sits at coordinate `(rowᵢ, colᵢ)` and each coordinate holds **exactly one** stone. A **move** removes a stone that shares its **row or column** with another stone still on the plane. Return the **largest number of moves** you can make.

The key insight: stones that are transitively connected by shared rows/columns form one **connected component**, and from a component of `k` stones you can remove `k - 1` (leaving one behind). So the answer is `n - (number of connected components)`. Model this with **Disjoint Set Union (Union-Find)**: union each stone's row-key with its column-key, then count distinct roots. To keep row and column labels from colliding, offset columns (e.g. `col + 100001` or `~col`).

---

## Examples

### Example 1

**Input:**
```text
n = 6
stones = [ {0,0}, {0,1}, {1,0}, {1,2}, {2,1}, {2,2} ]
```

**Output:**
```text
5
```

**Explanation:**
- Every stone shares a row or column with another, forming a **single** connected component:

```text
col:   0     1     2
row 0: (0,0)-(0,1)
       |      |
row 1: (1,0)  |   (1,2)
              |     |
row 2:      (2,1)-(2,2)
```

- `(0,0)`–`(0,1)` share row 0; `(0,0)`–`(1,0)` share col 0; `(2,1)`–`(2,2)` share row 2; etc. — all 6 stones link into 1 component.
- One component of 6 stones → `6 - 1 = 5` removable. Answer = **5**.

### Example 2

**Input:**
```text
n = 3
stones = [ {0,0}, {0,2}, {1,1} ]
```

**Output:**
```text
1
```

**Explanation:**
- `(0,0)` and `(0,2)` share row 0 → one component of 2 (remove 1).
- `(1,1)` shares no row or column with either → its own isolated component (remove 0).
- **Two** components over 3 stones → `3 - 2 = 1`. Isolated stones can never be removed.

---

## Input Format

- `n` — number of stones.
- `stones` — list of `n` coordinate pairs `{row, col}`, all pairs distinct.

## Output Format

- A single integer: the maximum number of stones that can be removed.

---

## Constraints

- `1 <= n <= 10^5`
- `0 <= row, col <= 10^4`
- No two stones share the same `(row, col)`.

---

## Key Points

1. **Answer = `n - components`** — each component of `k` stones yields `k - 1` removals; you can always leave exactly one behind.
2. **Union rows with columns, not stones directly** — treat each distinct row-index and column-index as a DSU node; unioning `row(i)` with `col(i)` links every stone sharing that line for free (avoids `O(n²)` pairwise checks).
3. **Offset column keys** so row `5` and column `5` are different nodes (e.g. `col + 100001`).
4. Count components as **distinct roots among the row/column keys that actually appear**, or start `components = n` and decrement on each successful union.

---

## Approach Hints

### Required idea: Union-Find over row/column keys

```text
init DSU;  components = n
for each stone (r, c):
    keyR = r
    keyC = c + OFFSET            // OFFSET > max row, e.g. 100001
    if union(keyR, keyC):        // first time this stone links its row & col
        components -= 1
return n - components
```

- Use a hash map `key -> parent` (keys are sparse, up to `2·n` distinct), or a fixed array of size `2·(maxCoord+1)`.
- Apply **path compression + union by size/rank** for near-`O(α(n))` per operation.
- `union` returns `false` when the row-key and col-key already share a root — no decrement.

### Counting components

- Track `components` incrementally (start at `n`, subtract on real merges), **or** after all unions collect `find(key)` for every seen key into a set and take its size — the answer is `n - |set|`.

---

## Complexity Analysis

- **Intended approach:** `O(n · α(n))` after building keys — effectively linear; space `O(n)` for the DSU over row/column keys.
- **Naive (pairwise graph):** build edges between every pair sharing a row/column → up to `O(n²)` edges, then flood-fill — too slow for `n = 10^5`.
