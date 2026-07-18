# Dijkstra's Shortest Path (Heap and Ordered-Set Implementations)

## Problem Description

You are given a **weighted, directed** graph with `n` vertices and **non-negative** edge weights, plus a `source` vertex. Compute the shortest distance from `source` to **every** vertex.

Solve this with **Dijkstra's algorithm**, and provide **both** standard implementations of the priority queue it relies on:

1. **Binary heap** — a min-priority-queue keyed by tentative distance, using **lazy deletion** (push duplicates, skip stale pops).
2. **Ordered set** — a balanced BST (`TreeSet` / `std::set`) of `(dist, vertex)` pairs that supports an explicit **decrease-key** by erasing the old entry before inserting the new one.

Return the distance array; a vertex unreachable from `source` gets `-1` (or `INF`, per repo convention). Because all weights are non-negative, once a vertex is popped with its final distance it is never improved again — that is the invariant both variants exploit.

---

## Examples

### Example 1

**Input:**
```text
n = 5
source = 0
edges = [ {0,1,4}, {0,2,1}, {2,1,2}, {1,3,1}, {2,3,5}, {3,4,3} ]   // directed {u, v, w}
```

**Output:**
```text
[0, 3, 1, 4, 7]
```

**Explanation:**
- Graph (directed):

```text
(0) --4--> (1) --1--> (3) --3--> (4)
 |          ^          ^
 1          2          5
 v          |          |
(2) --------+----------+
```

- Settle order by distance: `0(0) → 2(1) → 1(3) → 3(4) → 4(7)`.
- `1` is reached cheaper via `0→2→1 = 1+2 = 3`, not the direct `0→1 = 4` — this is the relaxation that both the heap and the set must capture.
- `3` uses `0→2→1→3 = 4`, beating `0→2→3 = 6`; then `4 = 4 + 3 = 7`.

### Example 2

**Input:**
```text
n = 4
source = 0
edges = [ {0,1,2}, {1,2,3} ]     // vertex 3 has no incoming edge
```

**Output:**
```text
[0, 2, 5, -1]
```

**Explanation:**
- `d(0)=0, d(1)=2, d(2)=2+3=5`.
- Vertex `3` is never reached, so its distance stays `INF` and is reported as `-1`.

---

## Input Format

- `n` — number of vertices (0-indexed, `0 .. n-1`).
- `source` — the start vertex.
- `edges` — list of directed edges `{u, v, w}` with `w >= 0`. Build an adjacency list `List<List<Edge>>` where `Edge { int to; int weight; }`.

## Output Format

- An `int[]` of length `n`: `dist[i]` is the shortest distance from `source` to `i`, or `-1` if `i` is unreachable.

---

## Constraints

- `1 <= n <= 10^5`
- `0 <= edges.length <= 2 * 10^5`
- `0 <= w <= 10^9` (use `long` accumulation to avoid overflow)
- Weights are **non-negative** — Dijkstra applies; negative weights would require Bellman-Ford.

---

## Key Points

1. **Non-negativity is mandatory** — the "settled once" invariant breaks if any edge is negative.
2. **Heap variant:** never decrease-key in place; push a new `(dist, v)` and discard a popped entry when `poppedDist > dist[v]`.
3. **Set variant:** before relaxing, `erase((dist[v], v))` if present, update `dist[v]`, then `insert` — keeping one live entry per vertex.
4. Guard distance sums with `long` since `n * maxWeight` can exceed `int`.

---

## Approach Hints

### Required idea: Dijkstra with a min-priority-queue

```text
dist[] = INF; dist[source] = 0
pq = { (0, source) }
while pq not empty:
    (d, u) = pq.popMin()
    if d > dist[u]: continue          // stale entry
    for (v, w) in adj[u]:
        if dist[u] + w < dist[v]:
            dist[v] = dist[u] + w
            pq.push( (dist[v], v) )    // relax
```

### Heap vs ordered-set — the only difference is the pop/relax step

- **Binary heap (lazy):** `pq.push` on every relax; on pop, `continue` if the entry is stale. Simple, allows duplicates, `O(E log E)`.
- **Ordered set (decrease-key):** on relax do `set.erase((old, v))` then `set.insert((new, v))`; pop is always `set.begin()`. One entry per vertex, `O(E log V)`.

---

## Complexity Analysis

- **Heap implementation:** Time `O(E log E)`, Space `O(V + E)` (duplicate entries possible).
- **Ordered-set implementation:** Time `O(E log V)`, Space `O(V + E)` (exactly one entry per vertex).
- **Naive (linear scan for min):** `O(V^2)` — better only for dense graphs where `E ≈ V^2`.
