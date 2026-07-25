# Number of Regions Cut by Slashes

## Problem Description

You are given an `n x n` grid where each cell holds one of three characters: `' '` (empty), `'/'`, or `'\'`. A `'/'` cuts the cell diagonally in the **forward** direction; a `'\'` cuts it in the **backward** direction. These slashes partition the whole grid into connected **regions**. Return the **number of regions**.

The trick is that a slash splits a cell into pieces, so a normal cell-level flood fill is not enough. Split **each cell into 4 triangles** — **top(0), right(1), bottom(2), left(3)** — and use a **Disjoint Set Union (Union-Find)**. The slash decides which triangles merge *within* a cell, and adjacency merges triangles *across* neighboring cells. The final number of Union-Find components is the answer.

---

## Examples

### Example 1

**Input:**
```text
n = 2
grid = [ " /",
         "/ " ]
```

**Output:**
```text
2
```

**Explanation:**
- Cell `(0,1)` and `(1,0)` each carry a `/`; the other two are empty.
- Split every cell into 4 triangles; merge per the rules below:

```text
 top(0)
left(3) [cell] right(1)
 bottom(2)
```

- The two slashes together carve the `2x2` board into exactly **2** connected regions.

### Example 2

**Input:**
```text
n = 2
grid = [ "/\\",
         "\\/" ]      // each cell is a single slash; '\\' denotes one backslash
```

**Output:**
```text
5
```

**Explanation:**
- The four slashes meet at the center, enclosing a small diamond in the middle.
- That gives **5** regions: four outer wedges plus the enclosed center — this is the case a cell-level count would miss.

---

## Input Format

- `grid` — an array of `n` strings, each of length `n`; every character is `' '`, `'/'`, or `'\'`.

## Output Format

- A single integer: the number of connected regions formed after the slashes cut the grid.

---

## Constraints

- `1 <= n <= 30` (grid is `n x n`; Union-Find operates on `4 * n * n` triangles)
- `grid[i][j] ∈ { ' ', '/', '\' }`
- Backslash appears as an **escaped** `'\\'` in most source encodings.

---

## Key Points

1. **4 triangles per cell**, indexed `top=0, right=1, bottom=2, left=3`; triangle id = `4*(r*n + c) + k`.
2. **Within a cell:** `'/'` merges `(top,left)` and `(right,bottom)`; `'\'` merges `(top,right)` and `(left,bottom)`; `' '` merges all four.
3. **Across cells:** current `right(1)` ↔ right-neighbor `left(3)`; current `bottom(2)` ↔ below-neighbor `top(0)`.
4. Start `components = 4*n*n` and decrement on each **real** merge — the leftover count is the number of regions.

---

## Approach Hints

### Required idea: split into triangles + Union-Find

```text
id(r, c, k) = 4 * (r * n + c) + k          // k: 0=top,1=right,2=bottom,3=left
components = 4 * n * n

for each cell (r, c):
    ch = grid[r][c]
    if ch != '/':  union(id(r,c,0), id(r,c,1)); union(id(r,c,2), id(r,c,3))  // top-right, bottom-left
    if ch != '\\': union(id(r,c,0), id(r,c,3)); union(id(r,c,1), id(r,c,2))  // top-left, right-bottom
    if c + 1 < n:  union(id(r,c,1), id(r,c+1,3))     // right ↔ neighbor left
    if r + 1 < n:  union(id(r,c,2), id(r+1,c,0))     // bottom ↔ neighbor top

return components
```

### How it works (visual)

**1. Split one cell into 4 triangles** — the diagonals meet at the center `X`:

```text
        top (0)
      +-----------+
      | \   0   / |
      |   \   /   |
 left |  3  X  1  | right
 (3)  |   /   \   |    (1)
      | /   2   \ |
      +-----------+
        bottom (2)
```

**2. A slash merges the triangles on the same side of the cut:**

```text
   '/'  (bottom-left → top-right)        '\'  (top-left → bottom-right)
      +-----------+                         +-----------+
      |        ╱  |  above → {0,3}          | ╲         |  above → {0,1}
      |   0,3╱    |  (top + left)           |   ╲ 0,1   |  (top + right)
      |    ╱      |                         |     ╲     |
      |   ╱ 1,2   |  below → {1,2}          |   3,2 ╲   |  below → {3,2}
      |  ╱        |  (right + bottom)       |        ╲  |  (left + bottom)
      +-----------+                         +-----------+

   ' '  (empty) → no cut → all four {0,1,2,3} merge into one piece
```

**3. Neighboring cells fuse across their shared border:**

```text
   +-------+-------+          right(1) of a cell  ==  left(3) of the cell to its right
   |   0   |   0   |          bottom(2) of a cell ==  top(0)  of the cell below
   | 3 X 1 ═ 3 X 1 |
   |   2   |   2   |   ← the "═" and "║" are the fused borders
   +---║---+-------+
   |   0   |
   | 3 X 1 |
   +-------+
```

**4. Putting Example 2 together** (`"/\"` over `"\/"`) — the four slashes point *inward* and seal a diamond in the middle:

```text
   ╱╲          Regions after all unions:
  ╱  ╲           (1) top wedge     (2) right wedge
  ╲  ╱           (3) left wedge    (4) bottom wedge
   ╲╱            (5) enclosed center diamond   → 5 components
```

### Why triangles

- A slash separates two halves of a cell, which cell-granular DSU/flood fill cannot express.
- Four triangles capture both halves *and* the four shared borders with neighbors, so all merges are edge-accurate.

---

## Complexity Analysis

- **Intended approach:** `O(n^2 · α(n))` over `4n^2` triangles → effectively `O(n^2)`, space `O(n^2)`.
- **BFS/DFS alternative:** upscale each cell to a `3x3` (or `2x2`) block of pixels, then flood fill — `O(n^2)` but heavier constant and more code.
- **Naive cell-level components:** wrong — it cannot split a cell that a slash divides.
