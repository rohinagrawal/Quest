# Minimum Count of Perfect Squares Summing to A

## Problem Description

Given a positive integer `A`, return the **minimum count** of perfect-square numbers (`1, 4, 9, 16, ...`) whose sum equals `A`. The same square may be used multiple times.

Solve it as an **unbounded-coin DP** where the "coins" are the perfect squares `<= A`: `dp[i]` = fewest squares summing to `i`, built as `dp[i] = min over j*j <= i of dp[i - j*j] + 1`. This is the shortest-path-in-counts view of the problem — each square is one step.

By **Lagrange's four-square theorem** the answer is always `1`, `2`, `3`, or `4`, which gives a fast `O(sqrt(A))` shortcut, but the DP is the expected general solution.

---

## Examples

### Example 1

**Input:**
```text
A = 12
```

**Output:**
```text
3
```

**Explanation:**
- `12 = 4 + 4 + 4` uses three squares; `12 = 9 + 1 + 1 + 1` uses four.
- The minimum is **3**.

```text
dp[0]=0
dp[4]=1  (4)
dp[8]=2  (4+4)
dp[12]=3 (dp[8] + one more 4)
```

### Example 2

**Input:**
```text
A = 13
```

**Output:**
```text
2
```

**Explanation:**
- `13 = 4 + 9`, both perfect squares → count **2**.
- No single square equals `13` (it is not a perfect square), so `1` is impossible and `2` is optimal.

---

## Input Format

- A single positive integer `A`.

## Output Format

- An integer: the minimum number of perfect squares that sum to `A`.

---

## Constraints

- `1 <= A <= 10^5`
- Squares may repeat; every `A >= 1` is representable (worst case four `1`s or the four-square bound).

---

## Key Points

1. This is an **unbounded** knapsack/coin problem — each square can be reused any number of times.
2. Precompute squares `1, 4, 9, ...` up to `A`; there are only `O(sqrt(A))` of them.
3. **Four-square theorem:** the answer never exceeds `4`; `1` iff `A` is a perfect square, and Legendre's rule (`A = 4^k(8m+7)`) detects exactly the `4` cases.

---

## Approach Hints

### Required idea: 1D DP over values `0..A`

```text
dp[0] = 0
for i in 1..A:
    dp[i] = INF
    j = 1
    while j*j <= i:
        dp[i] = min(dp[i], dp[i - j*j] + 1)
        j++
return dp[A]
```

### Faster: math shortcut (optional)

- If `A` is a perfect square → `1`.
- If `A = 4^k(8m + 7)` → `4` (Legendre's three-square theorem).
- Else check if `A = x^2 + y^2` for some `x` → `2`; otherwise `3`.

---

## Complexity Analysis

- **DP (intended):** Time `O(A * sqrt(A))`, Space `O(A)`.
- **BFS (level = count):** also `O(A * sqrt(A))`, finds the answer layer by layer.
- **Math (four-square):** `O(sqrt(A))` time, `O(1)` space — fastest but relies on number theory.
