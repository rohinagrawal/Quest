# Set Matrix Zeroes (In Place)

## Problem Description

Given an `M x N` matrix `A` of `0`s and `1`s, if any element is `0`, set its **entire row and column** to `0`. The transformation must happen **in place** and is evaluated on extra memory — aim for `O(1)` additional space beyond the matrix itself.

The trap is doing it naively: zeroing a row while scanning corrupts cells you haven't inspected yet. The `O(1)`-space trick uses the matrix's **first row and first column as marker storage** for which rows/columns must be zeroed, with two extra flags for the first row and first column themselves.

---

## Examples

### Example 1

**Input:**
```text
A = [ [1, 0, 1],
      [1, 1, 1],
      [1, 1, 1] ]
```

**Output:**
```text
[ [0, 0, 0],
  [1, 0, 1],
  [1, 0, 1] ]
```

**Explanation:**
- The only `0` is at `(0,1)`, so **row 0** and **column 1** become all zeros.
- Every other cell is untouched.

### Example 2

**Input:**
```text
A = [ [1, 0, 1],
      [1, 1, 1],
      [1, 0, 1] ]
```

**Output:**
```text
[ [0, 0, 0],
  [1, 0, 1],
  [0, 0, 0] ]
```

**Explanation:**
- Zeros at `(0,1)` and `(2,1)` zero out rows `0` and `2`, plus column `1`.
- Two zeros in the same column show that markers must be collected **before** any writing begins.

---

## Input Format

- A 2D integer matrix `A` of size `M x N` with entries in `{0, 1}`.

## Output Format

- The same matrix, modified in place per the rule.

---

## Constraints

- `1 <= N, M <= 10^3`
- `0 <= A[i][j] <= 1`

---

## Key Points

1. **Collect first, write later** — deciding and zeroing in one pass corrupts markers.
2. Use **row 0 and column 0** as the marker arrays; track the first row and first column with two separate boolean flags.
3. Apply the interior updates before finally zeroing row 0 / column 0, or the markers get destroyed early.

---

## Approach Hints

### Required idea: first row/column as markers

```text
rowFlag = any zero in row 0;  colFlag = any zero in column 0
for i in 1..M-1, j in 1..N-1:
    if A[i][j] == 0: A[i][0] = 0; A[0][j] = 0     // mark
for i in 1..M-1, j in 1..N-1:
    if A[i][0] == 0 or A[0][j] == 0: A[i][j] = 0   // apply
if rowFlag: zero all of row 0
if colFlag: zero all of column 0
```

---

## Complexity Analysis

- **In-place markers (intended):** Time `O(M * N)`, Space `O(1)`.
- **Marker sets:** `O(M * N)` time but `O(M + N)` extra space for the row/column sets.
