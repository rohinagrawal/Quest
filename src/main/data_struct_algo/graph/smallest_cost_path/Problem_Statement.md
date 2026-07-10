# Smallest cost path in an undirected weighted graph (bounded integer weights)

Problem
-------
Given an undirected weighted graph and two vertices (start and end), compute the smallest total cost required to travel from start to end. Edge weights are guaranteed to be small positive integers in the range [1..k]. Return the total cost of the minimum-cost path; if the end is unreachable from the start, return -1.

Important change from the generic statement
-----------------------------------------
- This variant assumes edge weights are integers in [1..k]. Because of the bounded small integer weights, it is preferable to use a bucket-based shortest-path algorithm (Dial's algorithm) instead of a general-purpose priority queue (Dijkstra). The problem statement and implementation notes below assume this bounded-weight model.

Input
-----
- A graph provided as an adjacency list: List<List<Edge>> or Map<Integer, List<Edge>>, where Edge { int to; int weight; }.
  - The repository prefers small Edge POJOs in `src/main/data_struct_algo/graph` — reuse them when available.
- Two integer vertex identifiers: start and end.
- Integer k: the maximum edge weight such that each edge weight w satisfies 1 <= w <= k.
- Vertices are numbered using a specified convention (document whether 0-based or 1-based in the implementation); the examples below use 0-based indexing.

Output
------
Return the minimum total weight (cost) of any path from start to end. If start == end, return 0. If the end is unreachable, return -1.

Examples
--------
1) Simple graph (weights in [1..3])

Vertices: 0,1,2
Edges:
0 - (1, weight=3)
0 - (2, weight=1)
2 - (1, weight=2)

Start: 0, End: 1
Answer: 3  // path 0 -> 2 -> 1 with cost 1 + 2

2) Unreachable

Vertices: 0,1,2
Edges:
0 - (1, weight=5)  // if k >= 5 allowed; otherwise inputs guarantee weight <= k

Start: 0, End: 2
Answer: -1

Constraints & assumptions
-----------------------
- Edge weights are integers and satisfy 1 <= weight <= k where k is small relative to V (e.g., k <= 1000).
- Number of vertices V >= 1 and number of edges E >= 0.
- The graph is undirected: each edge (u, v, w) implies (v, u, w) in the adjacency structure.
- Use 0-based internal indexing unless the instance requires otherwise (document any conversion).

Key points / Implementation notes (Dial's algorithm)
--------------------------------------------------
- Dial's algorithm is the recommended approach for bounded integer weights:
  - Maintain an array (list) of buckets where bucket[d] holds nodes whose tentative distance equals d.
  - The maximum distance we may need to represent is at most k * (V - 1) in worst-case simple paths. In practice you can use an array sized (k * V + 1) or maintain a circular set of k+1 buckets when using modulo arithmetic and careful scanning.
  - Initialize distance[start] = 0 and push start into bucket[0].
  - Keep a currentDistance pointer; repeatedly take the next non-empty bucket at or after currentDistance, pop a node u, and relax all edges (u, v, w) by updating distance[v] = distance[u] + w and pushing v into bucket[distance[v]]. Once a node is popped from a bucket at its finalized distance, it should not be processed again (mark finalized/visited).
  - Because weights are positive, bucket distances increase monotonically and every vertex is finalized once.

- Implementation details to match repo conventions:
  - Use an Edge POJO Edge{int to; int weight;} and adjacency lists stored as List<List<Edge>>.
  - Use an int[] distance array initialized to Integer.MAX_VALUE and a boolean[] finalized or visited to mark when a vertex is removed/finalized from a bucket.
  - When pushing into buckets, ensure you do not push nodes multiple times without checking whether the new distance improved the stored distance (or allow duplicates but skip outdated entries when popped).
  - For a safe, simple implementation, allocate buckets sized (k * V + 1). For memory-sensitive variants, use a circular array of k+1 buckets and map distances modulo (k+1) while scanning forward; ensure the scanning logic avoids infinite loops by bounding the scan to the maximum possible distance.
  - Return -1 if distance[end] remains Integer.MAX_VALUE after the algorithm finishes.

Complexity
----------
- Time: O(V + E + k * m) for naive bucket sizing, but commonly described as O(V + E + k) for Dial's algorithm where k is the maximum edge weight and the additional factor depends on chosen bucket scanning strategy. For practical small k (<< log V), Dial often outperforms Dijkstra's O((V + E) log V).
- Space: O(V + E + B) where B is the number of buckets (B = k * V + 1 for a straightforward implementation, or B = k + 1 for a circular optimization).

Correctness & edge cases
------------------------
- If start or end is out-of-bounds or if start/end map to blocked/invalid nodes (not applicable for pure graph), handle as invalid input — return -1 or throw an IllegalArgumentException depending on repo conventions.
- If start == end, return 0 immediately.
- If k is large (comparable to V or larger), the bucket approach may become less favorable; in that case the implementation can fall back to a standard priority-queue Dijkstra (note: for this problem we assume the inputs guarantee small k and the bucket approach is mandatory).

Hints
-----
- Reuse or add a small `Edge` class in this package to match other graph utilities in `src/main/data_struct_algo/graph`.
- For deterministic behavior in tests, iterate neighbors in ascending vertex id order when relaxing edges.
- Keep the bucket implementation simple and well-commented; prefer correctness and clarity over micro-optimizations unless profiling indicates bottlenecks.

References in repo
-----------------
- Look for other graph utilities and Edge POJOs under `src/main/data_struct_algo/graph` to reuse types and naming conventions.
