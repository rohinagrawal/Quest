# Topological Sort with Uniqueness Check

## Problem Description

You are given a **directed** graph with `n` vertices, where an edge `u → v` means `u` must come **before** `v`. Produce a **topological ordering** — a linear order of all vertices respecting every edge — and additionally report **whether that ordering is unique** or multiple valid orderings exist. If the graph has a **cycle**, no topological order exists; report that instead.

Solve this with **Kahn's algorithm (BFS on in-degrees)**: repeatedly remove a vertex whose in-degree is `0`, append it to the order, and decrement its neighbors. Two facts fall out for free:

- **Cycle detection** — if fewer than `n` vertices are emitted, a cycle remains.
- **Uniqueness** — if at **any** step the ready set (vertices with in-degree `0`) holds **more than one** vertex, there is a choice, so the ordering is **not unique**.

---

## Examples

### Example 1

**Input:**
```text
n = 4
edges = [ {0,1}, {0,2}, {1,3}, {2,3} ]   // directed u -> v  (u before v)
```

**Output:**
```text
order  = [0, 1, 2, 3]
unique = false
```

**Explanation:**
- Graph (directed):

```text
      (0)
     ╱   ╲
   (1)   (2)
     ╲   ╱
      (3)
```

- In-degrees: `0→0, 1→1, 2→1, 3→2`. Start with the only `0`-in-degree vertex `0`.
- After removing `0`, **both** `1` and `2` reach in-degree `0` — the ready set has size `2`, a genuine choice → **not unique** (`0,1,2,3` and `0,2,1,3` both work).

### Example 2

**Input:**
```text
n = 3
edges = [ {0,1}, {1,2}, {2,0} ]     // a directed cycle
```

**Output:**
```text
order  = []            // no valid topological sort
unique = false
```

**Explanation:**
- Every vertex has in-degree `1`, so Kahn's queue starts **empty** and emits `0` vertices.
- Fewer than `n = 3` vertices were output → a **cycle** exists → no topological ordering is possible.

---

## Input Format

- `n` — number of vertices (0-indexed, `0 .. n-1`).
- `edges` — list of directed edges `{u, v}` meaning `u → v` (`u` precedes `v`).

## Output Format

- `order` — a valid topological ordering of all `n` vertices, or an **empty list** if the graph is cyclic.
- `unique` — boolean: `true` if the topological order is the **only** one, else `false`.

---

## Constraints

- `1 <= n <= 10^5`
- `0 <= edges.length <= 2 * 10^5`
- Edges are **directed**; duplicate edges and self-loops (self-loop ⇒ cycle) may appear.
- A vertex with no edges is still part of the order (starts at in-degree `0`).

---

## Key Points

1. **Uniqueness = ready set never exceeds size 1.** If the queue ever holds ≥ 2 zero-in-degree vertices, multiple orderings exist.
2. **Cycle ⇒ emitted count `< n`.** Kahn's leaves cycle vertices stuck at in-degree `> 0`.
3. A **unique** order means the graph's precedence forms a single chain (a Hamiltonian path in the DAG) — every step forced.
4. Isolated vertices enter the queue immediately; if two are ready at once, that alone makes the order non-unique.

---

## Approach Hints

### Required idea: Kahn's algorithm (BFS on in-degrees)

```text
compute indeg[v] for all v
queue = all vertices with indeg == 0
order = [];  unique = true

while queue not empty:
    if queue.size() > 1: unique = false      // a choice existed
    u = queue.pop()
    order.add(u)
    for v in adj[u]:
        if --indeg[v] == 0:
            queue.push(v)

if order.size() < n: return ([], false)      // cycle
return (order, unique)
```

### DFS alternative

- Post-order DFS, pushing each vertex after its descendants; **reverse** the result for a valid order.
- Detect cycles with a recursion-stack (gray/black) coloring; DFS does **not** give the uniqueness flag for free — Kahn's is preferred here.

---

## Complexity Analysis

- **Kahn's (intended):** Time `O(V + E)`, Space `O(V + E)` — one pass yields the order, cycle check, and uniqueness together.
- **DFS-based:** also `O(V + E)`, but needs extra bookkeeping for uniqueness and cycle coloring.
- **Naive (permutation search):** `O(n! · n)` — infeasible beyond tiny `n`.
