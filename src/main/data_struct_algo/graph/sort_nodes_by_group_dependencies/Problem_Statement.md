# Sort Nodes by Dependencies, Keeping Groups Together

## Problem Description

You are given `n` nodes labeled `0 .. n-1`, an `n x n` **dependency matrix** `dep` where `dep[i][j] = 1` means node `i` **depends on** node `j` (so `j` must appear **before** `i`), and an array `group` where `group[i]` is the group id of node `i`. Output an ordering of all nodes such that (1) every dependency is respected — no node appears before something it depends on — **and** (2) all nodes sharing a group are **contiguous** in the output. If no valid ordering exists, return an **empty list**.

Solve this with a **two-level topological sort**: build a **group-level** dependency graph (a cross-group edge `j → i` induces `group[j] → group[i]`) and an **item-level** graph, then run **Kahn's algorithm** on each. A cycle at *either* level — groups or items — makes the ordering impossible. Finally, bucket the item-topo order by group and emit the groups in group-topo order.

---

## Examples

### Example 1

**Input:**
```text
n = 6
group = [1, 1, 0, 0, 2, 2]
dep (i depends on j):  {0→2, 1→0, 3→2, 5→0}   // only the 1-entries of the matrix
```

**Output:**
```text
[2, 3, 0, 1, 4, 5]
```

**Explanation:**
- Cross-group edges: `0→2` gives `group0` before `group1`; `5→0` gives `group1` before `group2`. Group order: `g0, g1, g2`.

```text
group0 {2,3} --> group1 {0,1} --> group2 {4,5}
   3→2 inside        1→0 inside       (no inner dep)
```

- Within `g0`, `3` depends on `2` → `[2,3]`; within `g1`, `1` depends on `0` → `[0,1]`; `g2` → `[4,5]`.
- Concatenating groups in order gives `[2,3,0,1,4,5]` — every dependency points left-to-right and each group is contiguous.

### Example 2

**Input:**
```text
n = 4
group = [0, 0, 1, 1]
dep (i depends on j):  {2→0, 0→2}
```

**Output:**
```text
[]
```

**Explanation:**
- `2→0` forces `group0` before `group1`, while `0→2` forces `group1` before `group0`.
- The group graph has a cycle `g0 ↔ g1`, so no contiguous-group ordering can satisfy both — return empty.

---

## Input Format

- `n` — number of nodes.
- `dep` — an `n x n` matrix; `dep[i][j] = 1` iff node `i` depends on node `j`.
- `group` — array of length `n`; `group[i]` is node `i`'s group id (nodes with id `-1` each get a fresh **virtual group**).

## Output Format

- A list of all `n` nodes in valid order with groups contiguous, or an empty list if impossible.

---

## Constraints

- `1 <= n <= 3 * 10^4`
- `0 <= number of groups m <= n`
- `dep[i][j] ∈ {0, 1}`, `dep[i][i] = 0` (a node never depends on itself)
- `group[i] ∈ {-1} ∪ [0, m-1]`

---

## Key Points

1. **Two cycles to check** — a cycle among groups *or* among items both make the answer empty.
2. Assign each `-1` (ungrouped) node its **own virtual group** so grouping logic is uniform.
3. A dependency `i` on `j` only becomes a **group** edge when `group[i] != group[j]`; same-group deps are item-level edges.
4. Emit items in **item-topo order but bucketed by group**, then output buckets in **group-topo order** — sorting items alone does not guarantee contiguity.

---

## Approach Hints

### Required idea: two-level topological sort (Kahn)

```text
give every group[i] == -1 a unique new group id
for each dep edge j -> i (i depends on j):
    if group[i] != group[j]: add group edge group[j] -> group[i]
    else:                    add item edge  j -> i   (within same group)
groupOrder = kahnTopo(groupGraph)      // [] if group cycle
itemOrder  = kahnTopo(itemGraph)       // [] if item cycle
if either is empty and had a cycle: return []
```

### Assembling the answer

- Walk `itemOrder`, appending each node to `bucket[group[node]]` (preserves intra-group order).
- Concatenate `bucket[g]` for `g` in `groupOrder` to get the final list.

---

## Complexity Analysis

- **Two-level Kahn (intended):** Time `O(n^2)` dominated by scanning the `n x n` matrix (`O(V + E)` on the built graphs), Space `O(n + E)`.
- **Naive (permute and check):** factorial — infeasible.
