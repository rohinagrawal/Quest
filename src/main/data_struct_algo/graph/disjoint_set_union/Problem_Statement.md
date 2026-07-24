# Disjoint Set Union — Union and Connected-Component Count

## Problem Description

You start with `n` isolated elements labeled `0 .. n-1`, each its own group. Process a sequence of queries of **two types**:

1. **`union(u, v)`** — merge the groups containing `u` and `v`.
2. **`count`** — report the current number of connected components (distinct groups).

Implement this with the **Disjoint Set Union (Union-Find)** structure, using **path compression** in `find` and **union by rank/size** in `union` so every operation runs in near-constant **amortized `O(α(n))`** time. Maintain a running `components` counter: start it at `n` and **decrement it only when a union actually merges two different groups**. Return the answer to each `count` query in order.

---

## Examples

### Example 1

**Input:**
```text
n = 5
queries = [ {union,0,1}, {union,1,2}, {count}, {union,3,4}, {count} ]
```

**Output:**
```text
[3, 2]
```

**Explanation:**
- Start: 5 singletons — `{0} {1} {2} {3} {4}`, `components = 5`.
- `union(0,1)` → `{0,1} {2} {3} {4}`, `components = 4`.
- `union(1,2)` → `{0,1,2} {3} {4}`, `components = 3` → first `count` = **3**.

```text
{0,1,2}      {3}      {4}
  root 0    root 3   root 4
```

- `union(3,4)` → `{0,1,2} {3,4}`, `components = 2` → second `count` = **2**.

### Example 2

**Input:**
```text
n = 3
queries = [ {union,0,1}, {union,0,1}, {count} ]
```

**Output:**
```text
[2]
```

**Explanation:**
- `union(0,1)` merges two singletons → `components = 3 → 2`.
- The second `union(0,1)` finds `0` and `1` **already in the same group**, so it does **not** decrement — `components` stays `2`.
- `count` = **2**. Redundant unions must not double-count.

---

## Input Format

- `n` — number of elements (0-indexed, `0 .. n-1`).
- `queries` — ordered list; each is either `{union, u, v}` or `{count}`.

## Output Format

- A list of integers: one entry per `count` query, in the order the `count` queries appear.

---

## Constraints

- `1 <= n <= 10^5`
- `1 <= queries.length <= 2 * 10^5`
- `0 <= u, v < n` for every `union` query
- Unions are undirected: `union(u, v)` == `union(v, u)`.

---

## Key Points

1. **Decrement only on a real merge** — if `find(u) == find(v)`, the union is a no-op for the component count.
2. Use **both** optimizations: path compression (in `find`) and union by rank/size — one alone loses the near-constant bound.
3. `union(u, v)` where `u == v` or already-connected changes nothing; guard before decrementing.
4. Track `components` incrementally; never recompute it by scanning all roots per `count` query.

---

## Approach Hints

### Required idea: Union-Find with path compression + union by size

```text
parent[i] = i;  size[i] = 1;  components = n

find(x):
    while parent[x] != x:
        parent[x] = parent[parent[x]]   // path compression (halving)
        x = parent[x]
    return x

union(u, v):
    ru, rv = find(u), find(v)
    if ru == rv: return               // already together — no decrement
    if size[ru] < size[rv]: swap(ru, rv)
    parent[rv] = ru                   // attach smaller under larger
    size[ru] += size[rv]
    components -= 1                    // a real merge happened

count(): return components
```

### Answering queries

- Iterate the query list once; on `union` call `union(...)`, on `count` append `components` to the output.

---

## Complexity Analysis

- **Intended approach:** `O(α(n))` amortized per operation → `O((n + q) · α(n))` total, effectively linear; space `O(n)`.
- **Naive (recount per query):** scanning for distinct roots on every `count` is `O(n)` each → `O(n · q)`, far slower.
- **No compression / no rank:** degrades toward `O(n)` per `find` in the worst case (a degenerate chain).
