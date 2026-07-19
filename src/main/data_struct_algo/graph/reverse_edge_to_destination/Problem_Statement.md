# Minimum Edge Reversals to Reach Destination

## Problem Description

You are given a **directed** graph with `n` vertices, a `source`, and a `destination`. You may **reverse** the direction of any edge, at a cost of `1` per reversal. Find the **minimum number of edge reversals** needed so that a path exists from `source` to `destination`. If `destination` cannot be reached even after reversing edges, return `-1`.

Model this as a shortest-path problem and solve it with **0-1 BFS**: for every original directed edge `u → v`, add a **weight-0** edge `u → v` (traverse for free) and a **weight-1** edge `v → u` (traverse by paying one reversal). The minimum reversals equal the shortest 0-1 distance from `source` to `destination`.

Because every edge weight is `0` or `1`, use a **deque** — push 0-weight relaxations to the **front** and 1-weight ones to the **back** — instead of a heap.

---

## Examples

### Example 1

**Input:**
```text
n = 5
source = 0
destination = 4
edges = [ {0,1}, {1,2}, {3,2}, {3,4} ]   // directed u -> v
```

**Output:**
```text
1
```

**Explanation:**
- Original directions:

```text
(0) --> (1) --> (2) <-- (3) --> (4)
```

- Free-travel gets us `0 → 1 → 2`, but the edge `3 → 2` points the wrong way to continue.
- Reverse `3→2` into `2→3` (cost **1**), then take `3 → 4` for free: path `0→1→2→3→4`.
- No zero-reversal path reaches `4`, so the minimum is **1**.

### Example 2

**Input:**
```text
n = 4
source = 0
destination = 3
edges = [ {0,1}, {1,0} ]     // vertex 3 has no edges at all
```

**Output:**
```text
-1
```

**Explanation:**
- Vertex `3` is isolated — no edge touches it in either direction.
- Reversing edges can never create a connection to `3`, so it stays unreachable: `-1`.

---

## Input Format

- `n` — number of vertices (0-indexed, `0 .. n-1`).
- `source`, `destination` — start and target vertices.
- `edges` — list of directed edges `{u, v}` meaning `u → v`. Build the 0-1 graph: `u →(0) v` and `v →(1) u`.

## Output Format

- A single integer: the minimum number of edge reversals; `-1` if `destination` is unreachable; `0` if `source == destination`.

---

## Constraints

- `1 <= n <= 10^5`
- `0 <= edges.length <= 2 * 10^5`
- Edges are **directed**; the graph may be disconnected.
- Do **not** actually mutate the graph — reversal is modeled as a weight-1 traversal.

---

## Key Points

1. Each original edge becomes **two** edges: forward weight `0`, backward weight `1`.
2. **0-1 BFS, not plain BFS:** a naive BFS ignores that reversals cost while free moves do not.
3. Deque discipline: weight-0 relaxation → **push front**; weight-1 → **push back**; pop from front.
4. Skip a popped vertex whose recorded distance is already smaller (lazy deletion).

---

## Approach Hints

### Required idea: 0-1 BFS on the augmented graph

```text
build adj: for each edge (u, v):
    adj[u].add( (v, 0) )      // traverse original direction, free
    adj[v].add( (u, 1) )      // reverse this edge, cost 1

dist[] = INF; dist[source] = 0
deque = [ source ]
while deque not empty:
    u = deque.popFront()
    for (v, w) in adj[u]:
        if dist[u] + w < dist[v]:
            dist[v] = dist[u] + w
            if w == 0: deque.pushFront(v)
            else:      deque.pushBack(v)
return dist[destination] == INF ? -1 : dist[destination]
```

### Why the deque works

- All weights are `0` or `1`, so the frontier stays sorted with at most two distinct distances.
- Front-loading 0-cost moves keeps the pop order monotonic — same guarantee as Dijkstra, in `O(V + E)`.

---

## Complexity Analysis

- **Intended approach (0-1 BFS):** Time `O(V + E)`, Space `O(V + E)`.
- **Dijkstra alternative:** `O(E log V)` — correct but adds an unnecessary log factor for 0/1 weights.
- **Plain BFS:** wrong — it cannot account for the reversal cost difference between edges.
