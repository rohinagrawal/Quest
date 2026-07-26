# Range Update Sum (Beggars and Devotees)

## Problem Description

There are `A` beggars sitting in a row, each starting with an empty pot. Each devotee `B[i] = {L, R, P}` donates `P` coins to **every** beggar in the inclusive index range `L .. R` (1-indexed). After processing all devotees, return the final amount in each beggar's pot as a 0-indexed array.

Doing each donation with a loop is `O(len(B) * A)`. Instead use a **difference array**: record each range update in `O(1)` at its two endpoints, then take a single **prefix sum** at the end — total `O(A + len(B))`.

---

## Examples

### Example 1

**Input:**
```text
A = 5
B = [[1, 2, 10], [2, 3, 20], [2, 5, 25]]
```

**Output:**
```text
[10, 55, 45, 25, 25]
```

**Explanation:**
- Devotee 1 adds `10` to beggars `1..2` → `[10, 10, 0, 0, 0]`.
- Devotee 2 adds `20` to beggars `2..3` → `[10, 30, 20, 0, 0]`.
- Devotee 3 adds `25` to beggars `2..5` → `[10, 55, 45, 25, 25]`.

### Example 2

**Input:**
```text
A = 3
B = []
```

**Output:**
```text
[0, 0, 0]
```

**Explanation:**
- No devotees donate, so every pot stays empty. The difference array remains all zeros and its prefix sum is all zeros.

---

## Input Format

- Integer `A` — number of beggars.
- 2D array `B` — each row is `{L, R, P}` with `1 <= L <= R <= A`.

## Output Format

- A 0-indexed array of length `A` with the total coins per beggar.

---

## Constraints

- `1 <= A <= 2 * 10^5`
- `1 <= L <= R <= A`
- `1 <= P <= 10^3`
- `0 <= len(B) <= 10^5`

---

## Key Points

1. Input is **1-indexed**; the output array is **0-indexed** — convert carefully.
2. **Difference array:** for update `{L, R, P}` do `diff[L] += P` and `diff[R+1] -= P`, then prefix-sum.
3. Size `diff` to `A + 2` so `R + 1` never overflows the array bounds.

---

## Approach Hints

### Required idea: difference array + prefix sum

```text
diff = new int[A + 2]          // 1-indexed with room for R+1
for (L, R, P) in B:
    diff[L]   += P
    diff[R+1] -= P
run = 0
for i in 1..A:
    run += diff[i]
    result[i-1] = run          // store 0-indexed
return result
```

---

## Complexity Analysis

- **Difference array (intended):** Time `O(A + len(B))`, Space `O(A)`.
- **Naive (loop each range):** `O(A * len(B))` — up to `2*10^10` ops, far too slow.
