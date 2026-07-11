# Smallest cost path in an undirected weighted graph (weights in [1..k])

Problem
-------
Given an undirected weighted graph and two vertices (`start`, `end`), find the minimum total path cost from `start` to `end`.

This variant has a strict constraint:
- Every edge weight is an integer in the range **[1..k]**.

Because weights are bounded small integers, solve this using a **bucket-based shortest path approach (Dial's algorithm)**.
Return `-1` if `end` is unreachable.

Input
-----
- Graph as adjacency list: `List<List<Edge>>` or `Map<Integer, List<Edge>>`
- `Edge { int to; int weight; }`
- Integer vertices: `start`, `end`
- Integer `k` such that every edge weight `w` satisfies `1 <= w <= k`
- Vertex indexing is either 0-based or 1-based (document conversion in code)

Output
------
- Minimum cost from `start` to `end`
- `0` if `start == end`
- `-1` if no path exists

Example
-------
Vertices: `0, 1, 2`

Edges:
- `0 --3-- 1`
- `0 --1-- 2`
- `2 --2-- 1`

Start: `0`, End: `1`
Answer: `3` via `0 -> 2 -> 1`

Graph view:

```text
(0) --3-- (1)
 |          ^
 1          |
 |          2
 v          |
(2) --------
```

Constraints & assumptions
-------------------------
- `1 <= V`, `0 <= E`
- Undirected graph: each `(u, v, w)` implies `(v, u, w)`
- All weights satisfy `1 <= w <= k`
- `k` is small enough to make bucket processing efficient

Dial's algorithm (required)
---------------------------
- Maintain buckets by distance: `bucket[d]` stores vertices with tentative distance `d`
- Initialize `dist[start] = 0`, push `start` in `bucket[0]`
- Keep `currentDistance` and scan to next non-empty bucket
- Pop a vertex `u`; if entry is outdated, skip
- Relax each edge `(u, v, w)`:
  - if `dist[u] + w < dist[v]`, update `dist[v]` and push `v` into `bucket[dist[v]]`
- Stop when all reachable nodes are processed (or once `end` is finalized)

Bucket progression sketch:

```text
bucket[0]: [start]
bucket[1]: []
bucket[2]: [ ... ]
bucket[3]: [ ... ]
...
currentDistance -> next non-empty bucket -> process -> relax -> push forward
```

Implementation notes (repo style)
---------------------------------
- Use `Edge{int to; int weight;}` with adjacency lists under `src/main/data_struct_algo/graph`
- Use `int[] dist` initialized to `Integer.MAX_VALUE`
- Either:
  - allocate buckets up to `k * V` (simple), or
  - use circular buckets with careful forward scanning (optimized)
- Allow duplicate bucket entries if needed; skip outdated entries on pop
- Return `-1` when `dist[end]` remains `Integer.MAX_VALUE`

Complexity
----------
- Time: commonly `O(V + E + k)` for bounded-weight Dial variant (exact bound depends on bucket strategy)
- Space: `O(V + E + B)`, where `B` is number of buckets

Edge cases
----------
- `start == end` -> return `0`
- Disconnected graph -> return `-1`
- Invalid vertex ids -> handle per repo convention (`-1` or exception)

References in repo
------------------
- Reuse graph helper/Edge patterns from `src/main/data_struct_algo/graph`
