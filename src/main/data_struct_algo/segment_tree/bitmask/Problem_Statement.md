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

**Trace:**
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

**Trace:**
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

Equivalent API shape: `solve(n, operations)` where `operations[i] = [type, l, r]`.

## Output Format

- A single integer: the sum of all query results, modulo `1_000_000_007`

---

## Constraints

- `1 <= n <= 10^5`
- `1 <= q <= 10^5`
- `0 <= l <= r < n`
- `type` ∈ `{1, 2}`

---

## Notes

- Flipping every bit in a range is the same as applying XOR with `1` at each position in `[l, r]`.
- A naive array scan per operation is `O(n · q)` and is too slow at these limits.
- Use a **segment tree with lazy propagation** (or similar) to support range flip and range popcount in `O(log n)` per operation.
