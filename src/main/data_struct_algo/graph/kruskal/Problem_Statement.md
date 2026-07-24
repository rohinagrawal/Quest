# Minimum Spanning Tree (Kruskal's Algorithm)

## Problem Description

You are given a **weighted, undirected**, connected graph with `n` vertices. Select a subset of edges that connects **all** vertices with the **minimum possible total weight** and no cycles — a **Minimum Spanning Tree (MST)**. Return the total weight of that tree. If the graph is **disconnected** (no spanning tree exists), return `-1`.

Solve this with **Kruskal's algorithm**: sort all edges by weight ascending, then greedily add each edge **only if it connects two currently-separate components** (checked with a **Disjoint Set Union / Union-Find**). An edge whose endpoints are already in the same set would form a cycle and is skipped. Stop once `n - 1` edges have been added.

---

## Examples

### Example 1

**Input:**
```text
n = 4
edges = [ {0,1,1}, {1,2,2}, {2,3,3}, {0,3,4}, {0,2,5} ]   // undirected {u, v, w}
```

**Output:**
```text
6
```

**Explanation:**
- Graph (undirected):

```text
(0) --1-- (1) --2-- (2) --3-- (3)
 |                    |         |
 +--------4-----------|---------+
 +--------5-----------+
```

- Sort edges: `1(0-1), 2(1-2), 3(2-3), 4(0-3), 5(0-2)`.
- Take `0-1 (1)`, `1-2 (2)`, `2-3 (3)` → all 4 vertices joined with `3 = n-1` edges, total `1+2+3 = 6`.
- Edges `0-3 (4)` and `0-2 (5)` are **skipped** — both endpoints already connected (would make a cycle). MST weight = **6**.

### Example 2

**Input:**
```text
n = 4
edges = [ {0,1,1}, {2,3,2} ]     // two separate pieces
```

**Output:**
```text
-1
```

**Explanation:**
- After processing every edge, only `2` edges are added: `{0,1}` and `{2,3}` remain **two** components.
- Fewer than `n-1 = 3` edges were used, so no spanning tree exists → `-1`.

---

## Input Format

- `n` — number of vertices (0-indexed, `0 .. n-1`).
- `edges` — list of undirected edges `{u, v, w}`.

## Output Format

- A single integer: the total weight of the MST, or `-1` if the graph is disconnected.

---

## Constraints

- `1 <= n <= 10^5`
- `0 <= edges.length <= 2 * 10^5`
- `1 <= w <= 10^9` (accumulate the total in a `long` to avoid overflow)
- Graph is undirected; parallel edges and self-loops may appear (self-loops always form a cycle → skipped).

---

## Key Points

1. **Greedy is correct** — sorting by weight and rejecting cycle edges yields a provably optimal MST (cut property).
2. Use **Union-Find** (path compression + union by size) for the "same component?" test in near-`O(α)` time.
3. **Early stop** at `n-1` added edges; if you exhaust all edges before that, the graph is disconnected → `-1`.
4. Accumulate weight in a `long`: `n · maxWeight` can exceed 32-bit range.

---

## Approach Hints

### Required idea: sort edges + Union-Find cycle rejection

```text
sort edges by weight ascending
init DSU over n vertices;  total = 0;  used = 0

for (u, v, w) in edges:
    if find(u) != find(v):          // different components → safe to add
        union(u, v)
        total += w
        used += 1
        if used == n - 1: break     // MST complete

return used == n - 1 ? total : -1   // -1 if graph was disconnected
```

### Why Union-Find

- Each accepted edge merges two trees; `find(u) == find(v)` means adding `(u,v)` closes a cycle.
- Path compression + union by size keep the whole run near-linear after the sort.

---

## Complexity Analysis

- **Intended approach:** `O(E log E)` for the sort, then `O(E · α(n))` for the Union-Find scan → dominated by the sort; space `O(n)`.
- **Prim's alternative:** `O(E log V)` with a heap — better on **dense** graphs; Kruskal shines on **sparse**, edge-list inputs.
- **Naive (check connectivity per edge with BFS):** `O(E · (V + E))` — far too slow.
