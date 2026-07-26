# Smallest Cost Path with Bounded Weights (Dial's Algorithm)

## Problem Description

Given an **undirected, weighted** graph and two vertices `start` and `end`, find the minimum total path cost between them. Every edge weight is an integer in the range **`[1..k]`**. Return `0` if `start == end`, and `-1` if `end` is unreachable.

Because the weights are small bounded integers, you do not need a heap-based Dijkstra. Solve it with **Dial's algorithm** — a bucket-based shortest path where `bucket[d]` holds vertices whose tentative distance is `d`. Scanning buckets in increasing distance order settles vertices in `O(V + E + k·V)` without a priority queue.

---

## Examples

### Example 1

**Input:**
```text
vertices = {0, 1, 2}
edges = [ {0,1,3}, {0,2,1}, {2,1,2} ]   // undirected {u, v, w}, w in [1..k]
start = 0, end = 1, k = 3
```

**Output:**
```text
3
```

**Explanation:**
- Graph:

```text
(0) --3-- (1)
 |          ^
 1          2
 v          |
(2) --------+
```

- Direct edge `0→1` costs `3`; the detour `0→2→1` costs `1 + 2 = 3` as well.
- Both routes tie at **3**, so the minimum cost is `3`.

### Example 2

**Input:**
```text
vertices = {0, 1, 2, 3}
edges = [ {0,1,2}, {1,2,1} ]     // vertex 3 is isolated
start = 0, end = 3, k = 2
```

**Output:**
```text
-1
```

**Explanation:**
- From `0` we reach `1` (cost 2) and `2` (cost 3), but nothing connects to `3`.
- `end` stays at distance infinity, so the answer is `-1`.

---

## Input Format

- Graph as an adjacency list of `Edge { int to; int weight; }`.
- Integers `start`, `end`, and `k` (every weight `w` satisfies `1 <= w <= k`).

## Output Format

- Minimum cost from `start` to `end`; `0` if equal, `-1` if unreachable.

---

## Constraints

- `1 <= V <= 10^5`, `0 <= E <= 2 * 10^5`
- `1 <= w <= k`, with `k` small (bucket count is `k * (V - 1) + 1`)
- Undirected: each `{u, v, w}` implies `{v, u, w}`.

---

## Key Points

1. **Bounded weights** are what make Dial's applicable — buckets replace the heap.
2. Allow duplicate bucket entries; **skip an outdated entry** when its recorded distance is already smaller.
3. Allocate buckets up to `k * (V - 1)`; scan forward to the next non-empty bucket for the current distance.

---

## Approach Hints

### Required idea: Dial's bucket-based shortest path

```text
dist[] = INF; dist[start] = 0; bucket[0].add(start)
d = 0
while d <= maxDist:
    while bucket[d] not empty:
        u = bucket[d].pop()
        if d > dist[u]: continue          // stale
        for (v, w) in adj[u]:
            if dist[u] + w < dist[v]:
                dist[v] = dist[u] + w
                bucket[dist[v]].add(v)
    d++
return dist[end] == INF ? -1 : dist[end]
```

---

## Complexity Analysis

- **Dial's (intended):** Time `O(V + E + k·V)`, Space `O(V + E + k·V)` for the buckets.
- **Binary-heap Dijkstra:** `O(E log V)` — correct but adds a log factor unnecessary for bounded weights.
