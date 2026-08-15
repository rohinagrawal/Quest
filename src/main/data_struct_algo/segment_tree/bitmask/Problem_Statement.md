# Bitmask Range Flip and Count

## Problem Description

A **bitmask** is a sequence of binary digits (`0` and `1`). For example, `"01010"` is a bitmask of length `5`.

You are given an integer `n`. Build a bitmask of length `n` that starts as all zeros: `"000...0"`.

You are given `q` operations in order. Each operation is described by three integers `(type, l, r)` with **0-based** indices and an inclusive range `[l, r]` (`l <= r`).

| `type` | Operation | Effect |
|--------|-----------|--------|
| `1` | **Update** | For every index `i` with `l <= i <= r`, replace `bit[i]` with `bit[i] XOR 1` (flip `0` ↔ `1`). |
| `2` | **Query** | Count how many indices `i` in `[l, r]` have `bit[i] == 1`. |

Process all operations in the given order. **Add up** the result of every query (type `2` only). Updates do not contribute to the answer.

Return that total. If there are no queries, return `0`. Because the sum can be large, return it **modulo** `1_000_000_007`.

---

## Examples

### Example 1

**Input:**
- `n = 5`
- Operations:
  - `(1, 0, 4)` — flip entire bitmask
  - `(2, 0, 4)` — count ones in `[0, 4]`
  - `(1, 1, 3)` — flip indices `1..3`
  - `(2, 0, 4)` — count ones again

**Explanation:**
- Start: `00000`
- After update 1: `11111` → query → **5**
- After update 2: `10001` → query → **2**

**Output:** `(5 + 2) mod 1_000_000_007` → **7**

### Example 2

**Input:**
- `n = 4`
- Operations:
  - `(1, 0, 1)` — flip `[0, 1]`
  - `(1, 2, 3)` — flip `[2, 3]`
  - `(2, 0, 3)` — count ones

**Explanation:**
- Start: `0000`
- After first flip: `1100`
- After second flip: `1111` → query → **4**

**Output:** **4**

### Example 3 (no queries)

**Input:**
- `n = 3`
- Operations: `(1, 0, 2)` only

**Output:** **0**

---

## Input Format

1. Integer `n` — length of the bitmask
2. Integer `q` — number of operations
3. `q` lines, each with three integers `type l r`:
   - `type` is `1` (update) or `2` (query)
   - `0 <= l <= r < n`

Equivalent API shape: `sumSetBitQueries(n, operations)` where `operations[i] = [type, l, r]`.

## Output Format

- A single integer: the sum of all query results, modulo `1_000_000_007`

---

## Constraints

- `1 <= n <= 10^5`
- `1 <= q <= 10^5`
- `0 <= l <= r < n`
- `type` ∈ `{1, 2}`

---

## Key Points

1. A range **flip** = XOR with `1`; on a segment node it just does `ones = length - ones` (zeros become ones).
2. **Lazy propagation:** store a pending-flip boolean per node; a second flip cancels the first (`lazy ^= 1`).
3. Query returns a **count of ones**; accumulate query answers modulo `1_000_000_007`.

---

## Approach Hints

### Required idea: segment tree with lazy range-flip

```text
node stores: ones = number of 1s in its segment
flip(node): ones = len(node) - ones; lazy[node] ^= 1
update(l, r): standard lazy range update calling flip on covered nodes
query(l, r): sum of ones over covered nodes (push lazy down first)
answer += query(l, r) for each type-2 op, mod 1e9+7
```

### Lazy push-down

- Before descending into children, if `lazy[node]` is set, `flip` both children and clear `lazy[node]`.

---

## Complexity Analysis

- **Segment tree + lazy (intended):** Time `O((n + q) log n)`, Space `O(n)`.
- **Naive array scan per op:** `O(n · q)` — up to `10^{10}`, far too slow.
