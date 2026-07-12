# Smallest Multiple with Digits 0 and 1

## Problem Description

You are given a positive integer `A`. Find the **smallest positive multiple** of `A` whose decimal representation uses only the digits **`0`** and **`1`**.

Return the multiple as a **string** with **no leading zeros**. The answer can be very large, so do not return it as an integer.

Equivalently: among all strings over `{0, 1}` that parse to a positive multiple of `A`, return the one with the smallest numeric value.

---

## Examples

### Example 1

**Input:**

```text
A = 2
```

**Output:**

```text
"10"
```

**Explanation:**

- Multiples of `2` with only `0`/`1` digits include `"10"`, `"100"`, `"110"`, …
- `"10"` is the smallest.

### Example 2

**Input:**

```text
A = 3
```

**Output:**

```text
"111"
```

**Explanation:**

- `"1"` → `1 mod 3 = 1`
- `"10"` → `10 mod 3 = 1`; `"11"` → `11 mod 3 = 2`
- `"110"` → `2 mod 3`; `"111"` → `0 mod 3` → **`111 = 37 × 3`**
- BFS on remainders finds `"111"` before any longer candidate.

---

## Input Format

- One integer `A`

## Output Format

- One string: the smallest valid multiple of `A` using only digits `0` and `1`

---

## Constraints

- `1 <= A <= 10^5`
- A solution always exists for the given constraints

---

## Key Points

1. **State = remainder mod `A`**, not the full number — the string can be huge, but there are only `A` remainder states.
2. **Start from `"1"`** — the smallest valid number with no leading zero; do not start from `"0"`.
3. **BFS on remainders:** from remainder `r`, append `0` → `(10 × r) mod A`, append `1` → `(10 × r + 1) mod A`; first time remainder `0` is reached, reconstruct the shortest string.
4. Track `parent` remainder and last digit (`0` or `1`) to rebuild the answer; mark a remainder visited when enqueued.
5. Prefer appending `0` before `1` at the same BFS level if you need deterministic tie-breaking among equal-length answers (numeric minimum favors shorter, then lexicographic).

---

## Complexity Analysis

- **Time:** `O(A)` — each remainder is enqueued at most once; two edges per state
- **Space:** `O(A)` for visited, parent, and queue
