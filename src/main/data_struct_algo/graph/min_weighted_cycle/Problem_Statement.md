# Minimum Weight Cycle in a Directed Graph

## Problem Description

You are given a **weighted, directed** graph with `n` vertices and **non-negative** edge weights. Find the cycle whose total edge-weight sum is **smallest**, and return that weight. If the graph has no cycle (it is a DAG), return `-1`.

Solve this by combining **all-pairs shortest paths (Floyd–Warshall)** with an **edge-closing** scan: the minimum cycle through an edge `u → v` (weight `w`) is `w + shortestPath(v, u)`, i.e. cross the edge, then take the cheapest way back. Compute all-pairs distances once, then minimize `w + dist[v][u]` over **every** edge. The overall answer is the smallest such value.

A cycle must use at least one edge; a vertex's self-distance `dist[i][i] = 0` is **not** a cycle and must be excluded.

---

## Examples

### Example 1

**Input:**
```text
n = 4
edges = [ {0,1,1}, {1,2,2}, {2,0,3}, {2,3,1}, {3,1,1} ]   // directed {u, v, w}
```

**Output:**
```text
4
```

**Explanation:**
- Graph (directed):

```text
(0) --1--> (1) --2--> (2) --3--> (0)
                       |
                       1
                       v
           (1) <--1-- (3)
```

- Two cycles exist: `0→1→2→0 = 1+2+3 = 6`, and `1→2→3→1 = 2+1+1 = 4`.
- Edge-closing check on `1→2 (w=2)`: `w + dist[2][1] = 2 + (2→3→1 = 2) = 4` — the minimum.
- The smallest cycle weight is **4**.

### Example 2

**Input:**
```text
n = 3
edges = [ {0,1,2}, {1,2,3} ]     // no edge returns to an earlier vertex
```

**Output:**
```text
-1
```

**Explanation:**
- The graph is a DAG: `0→1→2` with no path back.
- For every edge `u→v`, `dist[v][u] = INF`, so no `w + dist[v][u]` is finite — there is no cycle, return `-1`.

---

## Input Format

- `n` — number of vertices (0-indexed, `0 .. n-1`).
- `edges` — list of directed edges `{u, v, w}` with `w >= 0`. Initialize `dist[i][i] = 0`, `dist[u][v] = w`, all else `INF`.

## Output Format

- A single integer: the minimum cycle weight, or `-1` if the graph is acyclic.

---

## Constraints

- `1 <= n <= 500` (the `O(n^3)` Floyd–Warshall pass must be feasible)
- `0 <= edges.length <= n * (n - 1)`
- `0 <= w <= 10^5`
- Weights are **non-negative**; use a safe `INF` and skip relaxation when either endpoint is `INF`.

---

## Key Points

1. A cycle needs `>= 1` edge — **never** use `dist[i][i] = 0` as a candidate; close the cycle through a real edge instead.
2. Compute `dist[][]` **first** (full Floyd–Warshall), then do the edge scan — do not interleave.
3. `dist[v][u]` may reuse the edge `u→v`'s reverse only if such an edge exists; in a directed graph the return path is independent.
4. **Undirected variant:** you cannot reuse the same edge back — instead remove edge `(u,v)`, find the shortest `u→v` path in the rest, then add `w`.

---

## Approach Hints

### Required idea: Floyd–Warshall + edge closing

```text
dist = INF matrix; dist[i][i] = 0; dist[u][v] = min(dist[u][v], w) per edge
for k, i, j:                          // Floyd-Warshall, k outermost
    if dist[i][k] + dist[k][j] < dist[i][j]:
        dist[i][j] = dist[i][k] + dist[k][j]

best = INF
for each edge (u, v, w):
    if dist[v][u] != INF:
        best = min(best, w + dist[v][u])   // cross edge, shortest way back
return best == INF ? -1 : best
```

### Why the edge scan works

- Any cycle contains at least one edge `u→v`; the rest of the cycle is a `v→u` path.
- The cheapest such path is exactly `dist[v][u]`, so `w + dist[v][u]` is the best cycle *using that edge*.

---

## Complexity Analysis

- **Intended approach:** Floyd–Warshall `O(n^3)` + edge scan `O(E)` → `O(n^3)`, space `O(n^2)`.
- **Alternative (per-vertex Dijkstra):** `O(V * (E log V))` — better for sparse graphs, but only with non-negative weights.
- **Naive (enumerate cycles):** exponential — infeasible.
