# Sum of Squares in Range with Updates

## Problem Description

You are given an integer array `arr` of size `n` and `q` range operations.

Each operation is one of the following:

| Type | Operation | Meaning |
|------|-----------|---------|
| `1 l r` | Query | Return `arr[l]^2 + arr[l+1]^2 + ... + arr[r]^2` |
| `2 l r x` | Range set | Set every `arr[i] = x` for `l <= i <= r` |
| `3 l r k` | Range add | Set every `arr[i] = arr[i] + k` for `l <= i <= r` |

Indices are 0-based, and ranges are inclusive.

Process all operations in order. For every type `1` operation, output the range sum of squares.

---

## Example

**Input:**
- `arr = [1, 2, 3, 4]`
- Operations:
  - `1 0 3`
  - `3 1 2 2`
  - `1 0 3`
  - `2 0 1 5`
  - `1 0 2`

**Output:** `[30, 58, 75]`

**Explanation:**
- Initial query `[0,3]`: `1^2 + 2^2 + 3^2 + 4^2 = 30`
- After add `+2` on `[1,2]`, array becomes `[1,4,5,4]`
- Query `[0,3]`: `1 + 16 + 25 + 16 = 58`
- After set `[0,1] = 5`, array becomes `[5,5,5,4]`
- Query `[0,2]`: `25 + 25 + 25 = 75`

---

## Input Format

1. Integer `n`
2. Array `arr` of length `n`
3. Integer `q`
4. `q` operations, each in one of these forms:
   - `1 l r`
   - `2 l r x`
   - `3 l r k`

Equivalent API shape: `solve(arr, operations)` where each operation is an integer list.

## Output Format

- Return/print one integer for each type `1` query, in order.

---

## Constraints

- `1 <= n <= 10^5`
- `1 <= q <= 10^5`
- `0 <= l <= r < n`
- `|arr[i]|, |x|, |k| <= 10^9`

---

## Notes

Use a segment tree with lazy propagation. For each segment of length `m`, maintain:
- `sum = a1 + a2 + ... + am`
- `sqSum = a1^2 + a2^2 + ... + am^2`

Range-set update (`arr[i] = x`):
- `sum = m * x`
- `sqSum = m * x^2`
<code>
a^2 + b^2 + c^2 =>
j^2 + j^2 + j^2
n*(j^2)
</code>

Range-add update (`arr[i] = arr[i] + k`):
- `(a1 + k)^2 + ... + (am + k)^2`
- `= (a1^2 + ... + am^2) + 2k(a1 + ... + am) + m*k^2`
<code>
a^2 + b^2 + c^2 =>
(a + k)^2 + (b + k)^2 + (c + k)^2
a^2 + 2ka + k^2 + b^2 +2kb + k^2 + c^2 + 2kc + k^2
(a^2 + b^2 + c^2) + k^2 + k^2 + k^2 + 2ka + 2kb + 2kc
(a^2 + b^2 + c^2) + n*(k^2) + 2k(a + b + c)
</code>

So:
- `sqSum = sqSum + 2*k*sum + m*k^2`
- `sum = sum + m*k`

This gives `O(log n)` per update/query.
