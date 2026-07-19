# Shortest Distance with Weights in {1, 2}

## Problem Description

You are given a **weighted, undirected** graph with `n` vertices where **every edge weight is either `1` or `2`**, plus a `source` and a `destination`. Find the shortest total distance from `source` to `destination`.

Because the weights are tiny bounded integers, you do not need a general heap-based Dijkstra. Solve it with **edge-splitting + plain BFS**: replace each **weight-2** edge `u — v` with two **weight-1** edges through a **dummy vertex** (`u — d — v`). Every edge then has weight `1`, so a normal BFS layer-by-layer gives the shortest distance in `O(V + E)`.

Return `-1` if `destination` is unreachable, and `0` if `source == destination`. (Equivalently, this can be solved with a **deque-based 0-1-2 BFS** or **Dial's algorithm** using buckets `0 .. 2·(V-1)`.)

---

## Examples

### Example 1

**Input:**
```text
n = 5
source = 0
destination = 4
edges = [ {0,1,2}, {0,2,1}, {2,3,1}, {3,4,1}, {1,4,2} ]   // undirected {u, v, w}, w in {1,2}
```

**Output:**
```text
3
```

**Explanation:**
- Graph (undirected):

```text
(0) --2-- (1) --2-- (4)
 |                   ^
 1                   1
 v                   |
(2) --1-- (3) -------+
```

- Path `0→1→4` costs `2 + 2 = 4`.
- Path `0→2→3→4` costs `1 + 1 + 1 = 3` — cheaper despite having more edges.
- The shortest distance is **3**. Fewest *edges* is not the same as least *weight* when weights differ.

### Example 2

**Input:**
```text
n = 4
source = 0
destination = 3
edges = [ {0,1,1}, {1,2,2} ]     // vertex 3 is isolated
```

**Output:**
```text
-1
```

**Explanation:**
- From `0` we reach `1` (dist 1) and `2` (dist 3), but nothing connects to `3`.
- `destination` is unreachable, so the answer is `-1`.

---

## Input Format

- `n` — number of vertices (0-indexed, `0 .. n-1`).
- `source`, `destination` — start and target vertices.
- `edges` — list of undirected edges `{u, v, w}` with `w ∈ {1, 2}`.

## Output Format

- A single integer: the shortest distance; `-1` if `destination` is unreachable; `0` if `source == destination`.

---

## Constraints

- `1 <= n <= 10^5`
- `0 <= edges.length <= 2 * 10^5`
- Every weight `w` satisfies `1 <= w <= 2`
- Graph may be **disconnected**; each `{u, v, w}` is bidirectional.

---

## Key Points

1. **Least weight ≠ fewest edges** — a longer chain of `1`-weight edges can beat a short `2`-weight hop.
2. Edge-splitting adds at most `E` dummy vertices; total size stays `O(V + E)`, so BFS remains linear.
3. If using **0-1-2 BFS** instead: on relaxation push to front for the cheaper move; buckets/deque keep pop order monotonic.
4. A plain (unsplit) BFS is **wrong** — it counts edges, not weights.

---

## Approach Hints

### Required idea: split weight-2 edges to unit weights, then BFS

```text
build unit-weight adjacency:
    for each edge (u, v, w):
        if w == 1:
            add u — v
        else:                       // w == 2
            d = new dummy vertex
            add u — d ;  add d — v   // two weight-1 edges

BFS from source over the unit graph:
    dist[] = INF; dist[source] = 0; queue = [source]
    while queue:
        u = pop front
        for nb in adj[u]:
            if dist[u] + 1 < dist[nb]:
                dist[nb] = dist[u] + 1
                push nb
return dist[destination] == INF ? -1 : dist[destination]
```

### Dial's / bucket alternative (no dummy vertices)

- Keep buckets indexed by distance `0 .. 2·(V-1)`; process the lowest non-empty bucket first.
- Relax edge `(u, v, w)`: if `dist[u] + w < dist[v]`, place `v` in `bucket[dist[v]]`.

---

## Complexity Analysis

- **Edge-splitting + BFS:** Time `O(V + E)`, Space `O(V + E)` (dummy vertices bounded by `E`).
- **Dial's / 0-1-2 deque BFS:** Time `O(V + E + maxDist)`, Space `O(V + E)` — avoids extra vertices.
- **General Dijkstra:** `O(E log V)` — correct but adds a needless log factor for `{1,2}` weights.
