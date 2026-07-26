# Shortest Path on a Grid with Obstacles

## Problem Description

Given a 2D grid where each cell is **walkable** (`0`) or **blocked** (`1`), compute the minimum number of steps to move from a `start` cell to a `target` cell. Movement is allowed in the four cardinal directions (up, down, left, right); blocked cells cannot be entered. Return `-1` if the target is unreachable, `0` if `start == target`.

Because every move costs the same, solve with **Breadth-First Search (BFS)** from the start — the first time BFS reaches the target, it does so via a shortest path.

---

## Examples

### Example 1

**Input:**
```text
grid = [ [0, 0, 0],
         [1, 0, 1],
         [0, 0, 0] ]
start = (0, 0)
target = (2, 2)
```

**Output:**
```text
4
```

**Explanation:**
- Grid (`#` = blocked):

```text
(0,0)S  (0,1).  (0,2).
(1,0)#  (1,1).  (1,2)#
(2,0).  (2,1).  (2,2)T
```

- One shortest route: `(0,0) → (0,1) → (1,1) → (2,1) → (2,2)` — **4** steps. The blocked cells force the path through the middle column.

### Example 2

**Input:**
```text
grid = [ [0, 1, 0],
         [1, 1, 0],
         [0, 1, 0] ]
start = (0, 0)
target = (2, 2)
```

**Output:**
```text
-1
```

**Explanation:**
- The start `(0,0)` is walled off — every neighbor is blocked or leads to a dead end separated from the target.
- BFS exhausts all reachable cells without touching `(2,2)`, so the answer is `-1`.

---

## Input Format

- `grid` — a 2D array of `0` (walkable) / `1` (blocked), `R` rows by `C` columns.
- `start`, `target` — 0-based `(row, col)` coordinates, guaranteed in bounds.

## Output Format

- An integer: minimum steps, `0` if `start == target`, `-1` if unreachable.

---

## Constraints

- `1 <= R, C <= 10^3`
- `grid[i][j] ∈ {0, 1}`
- Coordinates are 0-based and within bounds.

---

## Key Points

1. **BFS = shortest path** on an unweighted grid; a DFS would not give the minimum step count.
2. Mark a cell visited **when enqueued**, not when dequeued, to avoid re-adding it many times.
3. Early exits: if `start` or `target` is blocked → `-1`; if `start == target` → `0`.

---

## Approach Hints

### Required idea: BFS from the start

```text
if start or target blocked: return -1
queue = [(start, 0)]; visited = {start}
while queue:
    (cell, d) = queue.popFront()
    if cell == target: return d
    for nb in 4-neighbors(cell):
        if in-bounds and walkable and nb not visited:
            visited.add(nb); queue.push((nb, d + 1))
return -1
```

---

## Complexity Analysis

- **BFS (intended):** Time `O(R * C)`, Space `O(R * C)` — each cell enqueued at most once.
- **DFS / repeated relaxation:** may revisit cells and does not yield the minimum directly.
