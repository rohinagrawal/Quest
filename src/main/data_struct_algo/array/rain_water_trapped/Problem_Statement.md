# Trapping Rain Water

## Problem Description

Given an array `A` of non-negative integers representing a histogram where each bar has width `1`, compute the total units of water trapped between the bars after rain. Water above any index `i` is bounded by the **tallest bar to its left** and the **tallest bar to its right**: the level there is `min(leftMax, rightMax)`, and the trapped amount is that level minus `A[i]` (if positive).

Solve it in `O(N)` time and `O(1)` space with the **two-pointer** technique, advancing whichever side has the smaller running max.

---

## Examples

### Example 1

**Input:**
```text
A = [0, 1, 0, 2]
```

**Output:**
```text
1
```

**Explanation:**
- Histogram:

```text
      #
 #  ~ #      (~ marks trapped water)
```

- Index `2` (height `0`) has `leftMax = 1`, `rightMax = 2` → holds `min(1,2) - 0 = 1` unit.
- No other index traps water, so the total is **1**.

### Example 2

**Input:**
```text
A = [1, 2]
```

**Output:**
```text
0
```

**Explanation:**
- The bars only ascend, so there is no right-hand wall to trap water against.
- Every index has `min(leftMax, rightMax) <= A[i]`, so **0** units are trapped.

---

## Input Format

- A single integer array `A`.

## Output Format

- An integer: the total trapped water.

---

## Constraints

- `1 <= |A| <= 10^5`
- `0 <= A[i] <= 10^5`

---

## Key Points

1. Water at `i` is `max(0, min(leftMax, rightMax) - A[i])` — a bar taller than both walls traps nothing.
2. **Two pointers:** move the side with the smaller max inward; that side's max is the true bound there.
3. Endpoints (first and last bars) can never trap water — they have no outer wall.

---

## Approach Hints

### Required idea: two-pointer running maxima

```text
l, r = 0, N-1;  leftMax = rightMax = 0;  water = 0
while l < r:
    if A[l] <= A[r]:
        leftMax = max(leftMax, A[l]);  water += leftMax - A[l];  l++
    else:
        rightMax = max(rightMax, A[r]); water += rightMax - A[r]; r--
return water
```

---

## Complexity Analysis

- **Two-pointer (intended):** Time `O(N)`, Space `O(1)`.
- **Prefix max arrays:** `O(N)` time but `O(N)` space for `leftMax[]` / `rightMax[]`.
