# Nearest Targets from Sources

## Problem Description

You are given an `N × M` grid that models a map with walkable streets, blocked buildings, **source** locations, and **target** locations.

| Cell | Meaning |
| --- | --- |
| `.` | Walkable empty cell |
| `#` | Blocked cell (cannot be entered) |
| `S` | Source — a location that must be matched to a target |
| `T` | Target — a destination that sources may route to |

Movement is allowed in **four cardinal directions** (up, down, left, right). Each move costs `1` step. Sources, targets, and empty cells are walkable. Obstacles are not.

**Other sources are not blockers.** A path from one source to its nearest target may pass through another source cell (or any number of sources). Only `#` cells are impassable.

For **every source cell**, find the **nearest target** by minimum number of steps. If multiple targets are at the same minimum distance, choose the target with the smallest row index; if still tied, choose the smallest column index.

Return one answer per source in **row-major order** (scan the grid top-to-bottom, left-to-right). If a source cannot reach any target, report distance `-1` and target coordinates `(-1, -1)`.

Because the grid is unweighted and there are multiple sources and targets, solve this using **multi-source BFS** seeded from all `T` cells at distance `0` (not from sources). Read the nearest-target answer at each `S` after one `O(N · M)` pass.

---

## Examples

### Example 1 — basic routing with an obstacle

**Input:**

```text
N = 4, M = 5
Grid:
S . . T .
. # . . .
. . S . T
. . . . .
```

**Annotated grid (0-indexed coordinates):**

```text
(0,0)S  (0,1).  (0,2).  (0,3)T  (0,4).
(1,0).  (1,1)#  (1,2).  (1,3).  (1,4).
(2,0).  (2,1).  (2,2)S  (2,3).  (2,4)T
(3,0).  (3,1).  (3,2).  (3,3).  (3,4).
```

**Output:**

```text
Source (0, 0) -> distance 3, target (0, 3)
Source (2, 2) -> distance 2, target (2, 4)
```

**Explanation:**

- Sources in row-major order: `(0, 0)`, then `(2, 2)`.
- From `(0, 0)` to `(0, 3)`: one shortest path is `(0,0) → (0,1) → (0,2) → (0,3)` (**3** steps). Target `(2, 4)` is farther.
- From `(2, 2)` to `(2, 4)`: `(2,2) → (2,3) → (2,4)` (**2** steps). The obstacle at `(1,1)` does not block this route.

**Multi-source BFS wavefront from all targets** (why one global BFS is enough):

```text
Targets seeded at distance 0: (0,3), (2,4)

After distance 1:          After distance 2:          After distance 3:
. 1 T 1 .                  . 1 T 1 .                  3 2 1 T 1 .
1 # 1 . .                  2 2 1 1 1                  2 # 2 1 1 .
1 1 S 1 T                  2 2 S 2 T                  2 2 S 2 T
. . . . .                  . . . . .                  . . . . .
```

When the wave from `(0,3)` first reaches `(0,0)` at distance `3`, that target is guaranteed to be the nearest target for that source.

### Example 2 — tied targets and an unreachable source

**Input:**

```text
N = 3, M = 4
Grid:
S . T T
. # . .
S # . T
```

**Output:**

```text
Source (0, 0) -> distance 2, target (0, 2)
Source (2, 0) -> distance -1, target (-1, -1)
```

**Explanation:**

- From `(0, 0)`, targets `(0, 2)` and `(0, 3)` are both reachable in **2** steps.
- Tie-break picks `(0, 2)` because it has the smaller column index.
- Source `(2, 0)` is walled off by `#` cells and cannot reach any target.

---

## Input Format

1. Two integers `N` and `M`
2. `N` lines, each containing exactly `M` characters from `{'.', '#', 'S', 'T'}`
3. At least one `S` and at least one `T`
4. Coordinates are **0-indexed**: `(row, col)` with `0 <= row < N` and `0 <= col < M`

Equivalent API shape:

```text
findNearestTargets(grid: char[N][M]) -> Result[]
Result = { sourceRow, sourceCol, distance, targetRow, targetCol }
```

## Output Format

- One result per source, ordered by row-major scan of the input grid
- Each result reports:
  - source coordinates
  - shortest distance to the chosen nearest target
  - chosen target coordinates
- Unreachable source: `distance = -1`, `target = (-1, -1)`

Example serialization (one line per source):

```text
0 0 3 0 3
2 2 2 2 4
```

---

## Constraints

- `1 <= N, M <= 10^3`
- At least one source and at least one target
- Each cell is exactly one of `'.'`, `'#'`, `'S'`, or `'T'`
- A cell is never both a source and a target
- Movement is unweighted and 4-directional only (no diagonals)

---

## Key Points

1. **Do not run BFS from every source separately.** That costs `O(S · N · M)` in the worst case.
2. **Seed one BFS queue with all targets at distance `0`.** The first time a cell is dequeued, you know its nearest target and distance.
3. **Mark visited when enqueuing**, not when dequeuing, so each walkable cell is processed at most once.
4. **Tie-breaking must be deterministic** — smallest target row, then smallest target column.
5. After the global BFS finishes, read answers only at source cells in row-major order.

---

## Approach Hints

### Required idea: multi-source BFS from targets

Treat every `T` cell as a BFS root at distance `0`.

```text
dist[row][col] = INF
nearestTarget[row][col] = invalid
queue = all target cells with dist = 0

while queue not empty:
    (r, c) = pop front
    for each 4-neighbor (nr, nc):
        if inside grid and cell is walkable and dist[nr][nc] is INF:
            dist[nr][nc] = dist[r][c] + 1
            nearestTarget[nr][nc] = nearestTarget[r][c]   // inherit nearest T
            push (nr, nc)
```

When two wavefronts meet, the cell was already claimed by whichever target arrived first — BFS level order makes that the shortest distance.

### Answering queries

Scan the grid row-major. For each `S` at `(r, c)`:

- if `dist[r][c] == INF` → unreachable
- else → output `dist[r][c]` and `nearestTarget[r][c]`

### Neighbor expansion order

Use a fixed direction order (for example: up, left, down, right) so tie-breaking during BFS discovery is reproducible in tests.

---

## Complexity Analysis

**Naive approach** — BFS from each source until the first target is hit:

- **Time:** `O(S · N · M)`
- **Space:** `O(N · M)` per BFS pass

**Multi-source BFS from all targets** — one pass over the grid:

- **Time:** `O(N · M)`
- **Space:** `O(N · M)` for `dist`, `nearestTarget`, queue, and visited state

---

## Edge Cases

| Case | Expected behavior |
| --- | --- |
| Source adjacent to a target | Distance `1` |
| Multiple sources, one target | Each source gets the same target if it is uniquely nearest |
| Equal-distance targets | Apply row/column tie-break |
| Source in a sealed component | `-1` and `(-1, -1)` |
| Single source, single target | Direct shortest-path result |
| Many targets, many sources | One `O(N · M)` BFS still suffices |
| Another source on the shortest path | **Not** a blocker — `S` cells are walkable |

---

## Related Problems

- **01 Matrix (LC 542):** distance from each `1` to nearest `0` — same BFS direction, full-grid output
- **Walls and Gates (LC 286):** distance from each empty room to nearest gate
- **Shortest path on a grid with obstacles** in this repo (`shortest_distance/`) — single source, single target variant
