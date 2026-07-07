# Graph Traversal

## Problem Description

Given a graph represented as either an **adjacency list** or an **adjacency matrix**, implement the common traversals used to visit all reachable vertices.

For this problem, support at least:

- **Breadth First Search (BFS)**
- **Depth First Search (DFS)**, either recursive or iterative

The graph may be **directed** or **undirected**, and it may be **disconnected**.

Start the traversal from a given source vertex. If the graph has multiple disconnected components, continue traversal from the smallest-index unvisited vertex until every vertex has been visited.

Return the order in which vertices are visited for each traversal.

---

## Examples

### Example 1

**Input:**

- `V = 5`
- `source = 0`
- Adjacency list:

```text
0: [1, 2]
1: [3, 4]
2: [4]
3: []
4: []
```

**Output:**

- `BFS = [0, 1, 2, 3, 4]`
- `DFS = [0, 1, 3, 4, 2]`

**Explanation:**

- BFS visits vertices level by level.
- DFS follows one branch as deep as possible before backtracking.
- Neighbor order matters; in this example, neighbors are visited in the order they appear in the adjacency list.

### Example 2

**Input:**

- `V = 4`
- `source = 1`
- Adjacency matrix:

```text
0 1 0 0
1 0 0 0
0 0 0 1
0 0 1 0
```

**Output:**

- `BFS = [1, 0, 2, 3]`
- `DFS = [1, 0, 2, 3]`

**Explanation:**

- Vertices `0` and `1` form one component, and `2` and `3` form another.
- After finishing the component containing the source, the traversal continues from the next unvisited vertex in increasing index order.

---

## Input Format

1. Integer `V` — number of vertices
2. Graph representation:
   - either an adjacency list, or
   - an adjacency matrix
3. Integer `source` — starting vertex for the traversal

Assume vertices are labeled from `0` to `V - 1`.

## Output Format

- Return the visitation order for BFS
- Return the visitation order for DFS

If the graph is disconnected, include every vertex exactly once across each traversal.

---

## Constraints

- `1 <= V <= 10^5` for adjacency-list based traversal
- `1 <= V <= 10^3` for adjacency-matrix based traversal
- The graph may contain zero or more edges
- Self-loops and repeated edges may be present unless the problem statement for a specific implementation says otherwise

---

## Key Points

1. **BFS uses a queue** and visits vertices in increasing distance from the source.
2. **DFS uses recursion or a stack** and visits one path as deep as possible before backtracking.
3. **Mark vertices as visited** before enqueuing/pushing them to avoid repeated work.
4. For adjacency matrices, scan neighbors from left to right so the traversal order is deterministic.
5. For disconnected graphs, continue from the next unvisited vertex in increasing index order.

---

## Approach Hints

### BFS

- Initialize a queue with the source vertex
- Pop a vertex, visit it, and push all unvisited neighbors
- Repeat until the queue is empty

### DFS

- Start from the source vertex
- Visit the current vertex and recursively/iteratively explore each unvisited neighbor
- Backtrack when a vertex has no unvisited neighbors left

---

## Complexity Analysis

**Adjacency List**

- **Time Complexity:** `O(V + E)`
- **Space Complexity:** `O(V)` for the visited array and traversal queue/stack

**Adjacency Matrix**

- **Time Complexity:** `O(V^2)`
- **Space Complexity:** `O(V)` for the visited array and traversal queue/stack

