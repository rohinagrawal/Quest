# Generate Spiral Matrix

## Problem Description

Given an integer `A`, generate an `A x A` square matrix filled with the numbers `1` to `A^2` in **spiral order** — starting at the top-left corner and moving right, down, left, and up, spiraling inward. Return the generated matrix.

Maintain four boundaries — `top`, `bottom`, `left`, `right` — and walk one edge at a time, shrinking the corresponding boundary after each edge until all `A^2` cells are filled.

---

## Examples

### Example 1

**Input:**
```text
A = 2
```

**Output:**
```text
[ [1, 2],
  [4, 3] ]
```

**Explanation:**
- Fill the top row left→right (`1, 2`), then the right column top→bottom (`3`), then the bottom row right→left (`4`).

```text
1 --> 2
      |
4 <-- 3
```

### Example 2

**Input:**
```text
A = 5
```

**Output:**
```text
[ [ 1,  2,  3,  4, 5],
  [16, 17, 18, 19, 6],
  [15, 24, 25, 20, 7],
  [14, 23, 22, 21, 8],
  [13, 12, 11, 10, 9] ]
```

**Explanation:**
- Each full loop peels one ring: outer ring `1..16`, next ring `17..24`, center `25`.
- Odd `A` leaves a single center cell filled last (`25`).

---

## Input Format

- A single integer `A` (matrix side length).

## Output Format

- An `A x A` matrix filled `1 .. A^2` in spiral order.

---

## Constraints

- `1 <= A <= 10^3`

---

## Key Points

1. Track four boundaries and shrink each after traversing its edge (`top++`, `right--`, `bottom--`, `left++`).
2. Guard the inner two edges with `top <= bottom` and `left <= right` so odd-sized matrices don't double-fill the center.
3. A single running counter `val = 1 .. A^2` drives all placements.

---

## Approach Hints

### Required idea: shrinking-boundary walk

```text
top, bottom, left, right = 0, A-1, 0, A-1;  val = 1
while top <= bottom and left <= right:
    for j in left..right:   grid[top][j]    = val++;   top++
    for i in top..bottom:   grid[i][right]  = val++;   right--
    if top <= bottom: for j in right..left: grid[bottom][j] = val++;  bottom--
    if left <= right: for i in bottom..top: grid[i][left]   = val++;  left++
return grid
```

---

## Complexity Analysis

- **Boundary walk (intended):** Time `O(A^2)` (each cell written once), Space `O(A^2)` for the output.
