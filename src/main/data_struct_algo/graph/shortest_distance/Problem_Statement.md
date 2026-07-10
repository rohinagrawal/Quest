# Shortest path on a grid with obstacles

Problem
-------
Given a 2D game board represented as a grid, compute the minimum number of steps required to move from a given start cell to a target cell while avoiding obstacles. Each cell is either walkable or blocked. Movement is allowed in four cardinal directions: up, down, left and right. If no path exists, return -1.

Input
-----
- A 2D grid (matrix) of integers or booleans where each entry indicates whether the cell is walkable or blocked. Common representations:
  - 0 = walkable, 1 = obstacle, or
  - false = walkable, true = obstacle
- A start coordinate: (startRow, startCol)
- A target coordinate: (targetRow, targetCol)

Output
------
Return the minimum number of steps (edges) required to reach the target from the start. If the start equals the target, return 0. If the target is unreachable, return -1.

Examples
--------
1) Matrix representation (0 = empty, 1 = obstacle)

Grid:
[[0, 0, 0],
 [1, 0, 1],
 [0, 0, 0]]

Start: (0,0), Target: (2,2)
Answer: 4  // one shortest path: (0,0)->(0,1)->(1,1)->(2,1)->(2,2)

2) Start equals target

Grid:
[[0]]

Start: (0,0), Target: (0,0)
Answer: 0

3) No path

Grid:
[[0,1,0],
 [1,1,0],
 [0,1,0]]

Start: (0,0), Target: (2,2)
Answer: -1

Constraints & assumptions
-----------------------
- Grid dimensions: rows >= 1, cols >= 1. Coordinates are 0-based and guaranteed to be within bounds.
- Cells marked as blocked cannot be traversed or entered.
- Movement cost between adjacent walkable cells is 1 (unweighted grid).

Key points / Implementation notes
--------------------------------
- Use Breadth-First Search (BFS) from the start to find the shortest number of steps on an unweighted grid.
- Track visited cells to avoid cycles and repeated work. Mark visited when enqueued.
- Validate trivial cases early: if start or target is blocked, return -1; if start == target return 0.
- Use a queue storing (row, col, distance) or maintain a level-size loop to increment distance per BFS layer.

Time & Space complexity
-----------------------
- Time: O(R * C) where R and C are grid rows and columns — each cell is visited at most once.
- Space: O(R * C) for the queue / visited set in the worst case.

Hints
-----
- Prefer integer coordinate pairs rather than flattening indices unless helpful for cache locality.
- When multiple neighbor orderings are possible, use the consistent ordering used elsewhere in the repo (e.g. up, left, down, right) to keep deterministic behavior for tests.

References in repo
-----------------
- Look at other graph BFS/shortest-path examples under `src/main/data_struct_algo/graph` to match style and helper utilities.
