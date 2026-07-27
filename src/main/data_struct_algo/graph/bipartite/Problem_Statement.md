# Check if a Graph is Bipartite

## Problem Description

Given an **undirected** graph with `n` vertices, determine whether it is **bipartite** — i.e. whether the vertices can be split into two groups such that **every edge connects a vertex in one group to a vertex in the other** (no edge stays within a group). Return `true` if bipartite, `false` otherwise.

Solve it with **2-coloring via BFS or DFS**: color a start vertex, then give every neighbor the opposite color. If you ever find an edge whose two endpoints already share a color, an **odd-length cycle** exists and the graph is not bipartite. Repeat for every component so disconnected graphs are fully covered.

---

## Examples

### Example 1

**Input:**
```text
n = 4
edges = [ {0,1}, {1,2}, {2,3}, {3,0} ]   // undirected
```

**Output:**
```text
true
```

**Explanation:**
- Graph is a 4-cycle (even length):

```text
(0) --- (1)
 |       |
(3) --- (2)
```

- Color `0 = A`; then `1 = B`, `2 = A`, `3 = B`. Edge `3–0` joins `B–A` — consistent.
- Every edge crosses colors, so the graph is bipartite (`{0,2}` vs `{1,3}`).

### Example 2

**Input:**
```text
n = 3
edges = [ {0,1}, {1,2}, {2,0} ]   // odd cycle (triangle)
```

**Output:**
```text
false
```

**Explanation:**
- Color `0 = A`, `1 = B`, `2 = A`; but edge `2–0` now joins `A–A`.
- The odd-length cycle forces two same-colored vertices to be adjacent → **not** bipartite.

---

## Input Format

- `n` — number of vertices (0-indexed, `0 .. n-1`).
- `edges` — list of undirected edges `{u, v}`. Build an adjacency list.

## Output Format

- A boolean: `true` if the graph is bipartite, else `false`.

---

## Constraints

- `1 <= n <= 10^5`
- `0 <= edges.length <= 2 * 10^5`
- Graph may be **disconnected**; self-loops make it non-bipartite immediately.

---

## Key Points

1. **Bipartite ⇔ no odd-length cycle** — the 2-coloring conflict is exactly an odd cycle.
2. Iterate over **all** vertices as potential BFS/DFS roots to cover every component.
3. Use a `color[]` array initialized to "uncolored"; a neighbor with the **same** color is the failure signal.

---

## Approach Hints

### Required idea: 2-coloring by BFS

```text
color[] = -1 (uncolored)
for s in 0..n-1:
    if color[s] != -1: continue
    color[s] = 0; queue = [s]
    while queue:
        u = queue.pop()
        for v in adj[u]:
            if color[v] == -1:
                color[v] = color[u] ^ 1   // opposite color
                queue.push(v)
            elif color[v] == color[u]:
                return false               // same-color edge → odd cycle
return true
```

---

## Complexity Analysis

- **BFS/DFS 2-coloring (intended):** Time `O(V + E)`, Space `O(V + E)`.
- **Union-Find alternative:** union each vertex with the "opposite set" of its neighbors; conflict ⇒ not bipartite.
