# Add One Edge to Minimize All-Pairs Cost

## Problem Description

You are given a **weighted, directed** graph with `n` vertices and a set of existing edges. You are also given a list of **candidate edges**, each of the form `{{source, destination}, weight}`.

Add **exactly one** candidate edge to the graph so that the **cost of the graph** becomes as small as possible. The cost is the summation of the shortest distance between every ordered pair of vertices `(i, j)` with `i != j`. If a pair is unreachable, its distance contributes `INF`.

Because you must recompute all-pairs shortest paths after a hypothetical insertion, solve this using the **Floyd–Warshall** all-pairs shortest-path matrix, then evaluate each candidate with a constant-per-pair relaxation instead of rerunning the full cubic algorithm. Return the **minimum total cost** achievable across all candidate options; if several candidates tie, any of them is acceptable.

> **Undirected variant.** If the graph is undirected, a candidate `(u, v, w)` is usable in both directions, so the relaxation gains a second, symmetric term (see Approach Hints). Everything else — Floyd–Warshall, the ordered-pair cost, the complexity — is unchanged.

---

## Examples

### Example 1

**Input:**
```text
n = 4
edges      = [ {0,1,4}, {1,2,1}, {2,3,1}, {3,0,1} ]   // directed {u, v, w}
candidates = [ {{0,2},1}, {{0,3},2} ]
```

**Output:**
```text
30
```

**Explanation:**
- Base graph is a directed cycle; every vertex reaches every other by going around it.

```text
(0) --4--> (1) --1--> (2) --1--> (3)
 ^                                  |
 |_______________ 1 ________________|
```

- Base ordered-pair cost is `42` (e.g. `d(0,1)=4, d(0,2)=5, d(1,0)=3, d(2,1)=6, d(3,2)=6, ...`).
- Adding `{{0,2},1}` short-circuits the expensive `0→1→2` leg and, through the cycle, shortens rows `0` and `3`, dropping the cost to **30**.
- Adding `{{0,3},2}` only helps row `0` and yields `38`. The minimum is `30`.

### Example 2

**Input:**
```text
n = 3
edges      = [ {0,1,2}, {1,2,2} ]                     // 2 reaches nobody
candidates = [ {{2,0},1}, {{0,2},1} ]
```

**Output:**
```text
15
```

**Explanation:**
- Base graph reaches forward only: `2→0`, `2→1`, `1→0` are all `INF`, so the base cost is `INF`.
- `{{2,0},1}` closes the loop `0→1→2→0`, making every pair reachable: `d(0,1)=2, d(0,2)=4, d(1,0)=3, d(1,2)=2, d(2,0)=1, d(2,1)=3` → cost **15**.
- `{{0,2},1}` only adds a forward shortcut; the backward pairs stay `INF`, so its cost is `INF`. **Direction matters** — the useful edge is the one pointing back.

---

## Input Format

- `n` — number of vertices (0-indexed, `0 .. n-1`).
- `edges` — list of existing directed edges `{u, v, w}` (an edge goes `u → v`).
- `candidates` — list of options `{{source, destination}, weight}`; you add **one** directed edge `source → destination`.

## Output Format

- A single integer: the minimum achievable total cost (sum of shortest distances over all ordered pairs `i != j`) after adding one candidate edge.

---

## Constraints

- `1 <= n <= 500`
- `0 <= edges.length <= n * (n - 1)`
- `1 <= candidates.length <= 10^4`
- `1 <= w <= 10^5` for every existing and candidate edge
- Graph may be **disconnected** or one-way; unreachable pairs contribute `INF` to the cost.

---

## Key Points

1. The candidate is **directed** `source → destination`; the reverse direction is *not* added (that is the undirected variant).
2. Recompute cost with the closed-form relaxation below — do **not** rerun full Floyd–Warshall per candidate.
3. If the base graph has an unreachable pair that no candidate fixes, that pair keeps contributing `INF`.

---

## Approach Hints

### Required idea: Floyd–Warshall + per-candidate relaxation

1. Run Floyd–Warshall once to get the base distance matrix `D`, where `D[i][j]` is the shortest distance (or `INF`).
2. For a **directed** candidate `(u, v, w)`, the distance for pair `(i, j)` after insertion is:

```text
newDist(i,j) = min(
    D[i][j],
    D[i][u] + w + D[v][j]    // go i -> u, cross new edge to v, then v -> j
)
```

For the **undirected** variant, also consider `D[i][v] + w + D[u][j]` (the symmetric term).

3. Sum `newDist(i, j)` over all ordered pairs `i != j` to get that candidate's cost (guard against `INF` overflow before adding `w`).
4. Track and return the minimum cost across all candidates.

### Tie-break and output

- Output the minimum **cost value**, not the edge. Any candidate achieving that minimum is a valid choice.

---

## Complexity Analysis

- **Intended approach:** Floyd–Warshall `O(n^3)` once, then `O(n^2)` per candidate → `O(n^3 + C * n^2)` for `C` candidates, space `O(n^2)`.
- **Naive approach:** Rerun Floyd–Warshall for every candidate → `O(C * n^3)`, far too slow for `C = 10^4`.
