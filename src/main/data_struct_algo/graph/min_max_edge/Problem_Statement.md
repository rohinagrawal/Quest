# Minimum Bottleneck Path (Least Maximum Edge)

## Problem Description

You are given a **weighted, undirected** graph with `n` vertices, a `source`, and a `destination`. Among **all** paths from `source` to `destination`, each path has a "worst" (**heaviest**) edge. Find the path whose heaviest edge is as **small** as possible, and return that edge weight — the **minimum bottleneck** (least maximum edge).

Solve this with a **modified Dijkstra**: instead of accumulating a sum, the cost of reaching a vertex is the **maximum edge weight** used so far, and you relax with `max(currentBottleneck, w)`. The greedy "settle the smallest bottleneck first" order still holds because edge weights are non-negative. (Equivalently, sort edges ascending and union endpoints until `source` and `destination` connect — the last added weight is the answer.)

Return `-1` if `destination` is unreachable; return `0` if `source == destination`.

---

## Examples

### Example 1

**Input:**
```text
n = 5
source = 0
destination = 4
edges = [ {0,1,4}, {0,2,1}, {2,3,2}, {3,4,3}, {1,4,5} ]   // undirected {u, v, w}
```

**Output:**
```text
3
```

**Explanation:**
- Graph (undirected):

```text
(0) --4-- (1) --5-- (4)
 |                   ^
 1                   3
 v                   |
(2) --2-- (3) -------+
```

- Path `0→1→4` has bottleneck `max(4, 5) = 5`.
- Path `0→2→3→4` has bottleneck `max(1, 2, 3) = 3` — smaller, even though it is **longer** and has a larger total weight.
- The least maximum edge is **3**. Minimizing the worst edge is *not* the same as minimizing path sum.

### Example 2

**Input:**
```text
n = 4
source = 0
destination = 3
edges = [ {0,1,2}, {1,2,3} ]     // vertex 3 is isolated
```

**Output:**
```text
-1
```

**Explanation:**
- `0` can reach `1` (bottleneck 2) and `2` (bottleneck 3), but there is no edge to `3`.
- `destination` is unreachable, so the answer is `-1`.

---

## Input Format

- `n` — number of vertices (0-indexed, `0 .. n-1`).
- `source`, `destination` — start and target vertices.
- `edges` — list of undirected edges `{u, v, w}` with `w >= 0`. Build adjacency `List<List<Edge>>`, `Edge { int to; int weight; }`.

## Output Format

- A single integer: the minimum bottleneck edge weight on the best `source → destination` path; `-1` if unreachable, `0` if `source == destination`.

---

## Constraints

- `1 <= n <= 10^5`
- `0 <= edges.length <= 2 * 10^5`
- `0 <= w <= 10^9`
- Graph may be **disconnected**; each `{u, v, w}` is bidirectional.

---

## Key Points

1. **Cost is a max, not a sum** — relax with `max(dist[u], w)`; a longer path can win if its heaviest edge is lighter.
2. Once `destination` is popped from the priority queue, its bottleneck is final — you may stop early.
3. Same lazy-deletion rule as Dijkstra: skip a popped entry when `poppedBottleneck > dist[v]`.
4. **Union-Find alternative:** add edges in ascending weight order; the answer is the weight that first connects `source` and `destination`.

---

## Approach Hints

### Required idea: modified Dijkstra (minimax relaxation)

```text
dist[] = INF; dist[source] = 0
pq = { (0, source) }               // (bottleneck, vertex), min-heap
while pq not empty:
    (b, u) = pq.popMin()
    if b > dist[u]: continue        // stale
    if u == destination: return b
    for (v, w) in adj[u]:
        nb = max(b, w)              // worst edge on this route so far
        if nb < dist[v]:
            dist[v] = nb
            pq.push( (nb, v) )
return dist[destination] == INF ? -1 : dist[destination]
```

### Union-Find alternative (offline)

- Sort all edges by weight ascending; union `u, v` one at a time.
- The moment `find(source) == find(destination)`, the current edge's weight is the minimum bottleneck.

---

## Complexity Analysis

- **Modified Dijkstra:** Time `O(E log V)`, Space `O(V + E)` — single source-to-destination query.
- **Union-Find (Kruskal-style):** Time `O(E log E)` for the sort, near-`O(E α)` for unions — best when answering the bottleneck for **many** pairs on one graph.
- **Naive (enumerate all paths):** exponential — infeasible.
