# All-Pairs Shortest Paths with Negative Weights (Floyd–Warshall)

## Problem Description

You are given a **weighted, directed** graph with `n` vertices whose edge weights **may be negative**. Compute the shortest distance between **every ordered pair** of vertices `(i, j)`.

Solve this with the **Floyd–Warshall** algorithm: for each intermediate vertex `k`, relax every pair `(i, j)` through `k`. Unlike Dijkstra, Floyd–Warshall tolerates negative edges — but it breaks if a **negative cycle** is reachable, because distances along that cycle can be driven arbitrarily low.

Return the `n x n` distance matrix (`INF` for unreachable pairs). If the graph contains a **negative cycle**, report it — after the main triple loop, any vertex `i` with `dist[i][i] < 0` lies on (or can reach) a negative cycle.

---

## Examples

### Example 1

**Input:**
```text
n = 4
edges = [ {0,1,3}, {1,2,-2}, {0,2,5}, {2,3,2}, {1,3,4} ]   // directed {u, v, w}
```

**Output:**
```text
[ [ 0,  3,  1,  3],
  [INF, 0, -2,  0],
  [INF,INF, 0,  2],
  [INF,INF,INF, 0] ]
```

**Explanation:**
- Graph (directed):

```text
(0) --3--> (1) --(-2)--> (2) --2--> (3)
 |          |                        ^
 5          +----------4-------------+
 v
(2)
```

- `d(0,2) = 1` uses `0→1→2 = 3 + (-2)`, beating the direct `0→2 = 5` — the **negative edge** is what makes the detour cheaper.
- `d(1,3) = 0` via `1→2→3 = -2 + 2`, beating direct `1→3 = 4`; then `d(0,3) = 0→1→2→3 = 3`.
- Backward pairs are `INF` (directed graph, no return edges).

### Example 2

**Input:**
```text
n = 3
edges = [ {0,1,1}, {1,2,-3}, {2,0,1} ]     // cycle weight = 1 - 3 + 1 = -1
```

**Output:**
```text
NEGATIVE CYCLE
```

**Explanation:**
- The cycle `0→1→2→0` sums to `-1`, so looping repeatedly drives distances down without bound.
- After the triple loop, `dist[0][0] = -1 < 0` (and likewise for `1`, `2`), which flags the negative cycle. No finite shortest-path matrix exists.

---

## Input Format

- `n` — number of vertices (0-indexed, `0 .. n-1`).
- `edges` — list of directed edges `{u, v, w}`; `w` may be negative. Initialize `dist[i][i] = 0`, `dist[u][v] = w`, all else `INF`.

## Output Format

- The `n x n` matrix `dist`, where `dist[i][j]` is the shortest distance (or `INF` if unreachable).
- If a negative cycle exists, report it instead (e.g. `NEGATIVE CYCLE`).

---

## Constraints

- `1 <= n <= 500` (the `O(n^3)` triple loop must be feasible)
- `0 <= edges.length <= n * (n - 1)`
- `-10^4 <= w <= 10^4`; negative edges are allowed, **no negative cycle** unless detection is requested
- Use a large-but-safe `INF` (e.g. `1e9`) and **skip relaxation when either endpoint is `INF`** to avoid wrap-around.

---

## Key Points

1. **Loop order is fixed:** `k` (intermediate) is the **outermost** loop, then `i`, then `j`. Swapping it is the classic bug.
2. **Guard against fake paths:** only relax `(i, j)` through `k` when `dist[i][k]` and `dist[k][j]` are both finite — otherwise `INF + negative` corrupts the matrix.
3. **Negative-cycle test:** after the loops, `dist[i][i] < 0` for any `i` means a negative cycle is reachable.
4. Negative **edges** are fine; only negative **cycles** make shortest paths undefined.

---

## Approach Hints

### Required idea: Floyd–Warshall triple relaxation

```text
dist = INF matrix; dist[i][i] = 0; dist[u][v] = w for each edge
for k in 0..n-1:
    for i in 0..n-1:
        for j in 0..n-1:
            if dist[i][k] != INF and dist[k][j] != INF:
                dist[i][j] = min(dist[i][j], dist[i][k] + dist[k][j])
```

### Negative-cycle detection

- After the triple loop, scan the diagonal: if any `dist[i][i] < 0`, a negative cycle exists — report it.
- To find *which* pairs are affected, mark `dist[i][j] = -INF` whenever `dist[i][k] + dist[k][j]` passes through such a vertex.

---

## Complexity Analysis

- **Intended approach:** Time `O(n^3)`, Space `O(n^2)` — one pass handles all pairs and negative edges.
- **Alternative (Johnson's):** `O(V*E + V*E log V)` — better for sparse graphs, but heavier to implement; Floyd–Warshall wins for dense graphs and small `n`.
- **Dijkstra per source:** invalid here — it cannot handle the negative edges this problem allows.
