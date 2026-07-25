# Good Graph — Minimum Pointer Changes

## Problem Description

You are given a **directed functional graph** of `n` nodes (numbered `1 .. n`): node `i` points to **exactly one** node `A[i]` (possibly itself). Node `1` is the **special** node. A node is **good** if any of the following holds:

1. it is node `1`, **or**
2. it points to node `1`, **or**
3. it points to a good node.

Unfolded, a node is good **iff following its pointer chain eventually reaches node 1**. You may redirect the single out-pointer of any node to any target. Return the **minimum number of pointers to change** so that **every** node becomes good (each node must still point to exactly one node).

Because each node has out-degree 1, the graph is a set of **basins**, each draining into exactly one cycle. A node is bad iff its chain terminates in a cycle that does **not** contain node 1. Fixing one bad basin costs exactly **one** redirect, so the answer is **the number of cycles not containing node 1**. This is computed cleanly with **Disjoint Set Union (Union-Find)** after cutting node 1's own out-edge.

---

## Examples

### Example 1

**Input:**
```text
A = [1, 2, 1, 2]     // 1-indexed: node i points to A[i]
```

**Output:**
```text
1
```

**Explanation:**
- Edges: `1→1`, `2→2`, `3→1`, `4→2`.

```text
   (3) --> (1) --> (1)      [self-loop cycle {1} — contains node 1 → good]
   (4) --> (2) --> (2)      [self-loop cycle {2} — no node 1 → BAD]
```

- Basin `{1,3}` reaches node 1 → good. Basin `{2,4}` loops forever at node 2 → bad.
- One bad cycle (`{2}`); redirect node `2` (or `4`) to a good node → **1** change.

### Example 2

**Input:**
```text
A = [3, 1, 3, 1]
```

**Output:**
```text
1
```

**Explanation:**
- Edges: `1→3`, `2→1`, `3→3`, `4→1`. Node 1 is a **tail** feeding into cycle `{3}`.
- Nodes `2` and `4` point straight to node 1 → good. Node 1 is good by rule 1. Node `3` self-loops without reaching node 1 → bad.
- The single bad cycle is `{3}` — note it does **not** contain node 1 even though node 1 points into it. Redirect node `3` → **1** change. (Node 1's own pointer is irrelevant: node 1 is good regardless of where it points.)

---

## Input Format

- `A` — integer array of size `n`, `1-indexed` in meaning: the `i`-th value is the node that node `i` points to, `1 <= A[i] <= n`.

## Output Format

- A single integer: the minimum number of pointer changes to make all nodes good.

---

## Constraints

- `1 <= n <= 10^5`
- `1 <= A[i] <= n` (self-pointers allowed)

---

## Key Points

1. **Answer = number of cycles not containing node 1.** Each such cycle is one bad basin, fixable with exactly one redirect (and never zero).
2. **Cut node 1's out-edge before unioning.** Node 1 is good unconditionally (rule 1); unioning `1 → A[1]` would wrongly merge node 1 into a bad cycle it points into (see Example 2).
3. **Why undirected DSU is valid on a directed graph:** with node 1's out-edge removed it becomes a pure sink, so its weakly-connected component is a tree with all edges pointing toward node 1 — exactly the set of nodes that can reach node 1. Every *other* component has one node per out-edge → contains exactly one (bad) cycle.
4. Self-loops (`A[i] == i`) are no-op unions; parallel structure is handled naturally.

---

## Approach Hints

### Required idea: DSU over all edges except node 1's

```text
init DSU over nodes 1..n
for i in 2..n:            // SKIP node 1's own out-edge
    union(i, A[i])

r1 = find(1)
answer = count of distinct roots find(i) that are != r1   // components without node 1
return answer
```

- Use **path compression + union by size** for near-`O(α(n))` per operation.
- Count components without node 1 by collecting `find(i) != r1` roots into a set, or by tallying roots and excluding node 1's.

### Why one change per bad basin

- A bad basin's cycle has no node reaching node 1; at least one pointer on it must change (≥1).
- Redirecting one cycle node to any good node makes it good; goodness propagates backward around the cycle and up every in-tree (≤1). So exactly 1.

---

## Complexity Analysis

- **Intended approach:** `O(n · α(n))` for the DSU pass, `O(n)` to count roots; space `O(n)`. Effectively linear.
- **Alternative (cycle detection by coloring):** iterative DFS over the single out-edge, count cycles excluding node 1's — also `O(n)`, but trickier to get the node-1 special case right.
