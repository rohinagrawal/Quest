# Wine Seller Profit

## Problem Description

A wine seller has `N` bottles of wine stored in a row, given as an array `price` where `price[i]` is the **base value** of the `i`th bottle (indices increase left to right).

He sells exactly **one bottle per year**, starting at year `1`. Because the cellar is a row, only the two ends are reachable — each year he must sell either the **first** or the **last** remaining bottle. Selling continues until all `N` bottles are gone, so the last bottle is always sold in year `N`.

Every bottle's value increases by the **same multiplier each year**: a bottle sold in year `y` earns `price[i] * y`, regardless of which bottle it is. Find the seller's **maximum total profit** over the whole sale, choosing which end to sell from each year.

Solve it with **interval dynamic programming**: `dp[i][j]` = the best profit obtainable from the bottles still in the range `price[i..j]`. Whatever bottles remain in `[i, j]` are always sold in the *last* `(j - i + 1)` years of the sale, so the current year is fixed by the window size — the only choice is which end to sell first.

---

## Examples

### Example 1

**Input:**
```text
price = [1, 4, 6, 2]
```

**Output:**
```text
41
```

**Explanation:**
- `N = 4`, so the sale runs across years `1..4`.
- One optimal order: sell index `0` (`1`) in year 1 → `1`; sell index `3` (`2`) in year 2 → `4`; sell index `1` (`4`) in year 3 → `12`; sell index `2` (`6`) in year 4 → `24`.
- Total profit: `1 + 4 + 12 + 24 = 41`.

### Example 2

**Input:**
```text
price = [2, 4, 6, 2, 5]
```

**Output:**
```text
64
```

**Explanation:**
- `N = 5`, years `1..5`.
- One optimal order: sell index `0` (`2`) year 1 → `2`; sell index `4` (`5`) year 2 → `10`; sell index `3` (`2`) year 3 → `6`; sell index `1` (`4`) year 4 → `16`; sell index `2` (`6`) year 5 → `30`.
- Total profit: `2 + 10 + 6 + 16 + 30 = 64`.

---

## Input Format

- An integer array `price` of length `N`, the base value of each bottle from left to right.

## Output Format

- An integer: the maximum total profit obtainable by selling all bottles, one per year, always from either end.

---

## Constraints

- `1 <= N <= 500`
- `1 <= price[i] <= 10^3`

---

## Key Points

1. **Only the two ends are ever sellable** — this is what makes the state a contiguous window `[i, j]`, not an arbitrary subset.
2. **The year is implied by the window, not tracked separately.** If `N` bottles start in the cellar and the remaining window has `(j - i + 1)` bottles, then `(N - (j - i + 1))` have already been sold, so the *next* sale happens in year `N - (j - i + 1) + 1`.
3. At each step there are exactly two choices — sell `price[i]` now or sell `price[j]` now — so `dp[i][j] = max` of the two resulting profits.
4. Base case: a single remaining bottle `dp[i][i]` must be sold in year `N`, earning `price[i] * N`.
5. Greedily always selling the cheaper/costlier end does **not** work in general — the choice at each step affects the year multiplier every remaining bottle will get, which is why this needs DP rather than a greedy scan.

---

## Approach Hints

### Required idea: interval DP over the remaining window `price[i..j]`

```text
n = price.length
for i in 0 .. n-1:
    dp[i][i] = price[i] * n          // last bottle standing, sold in year n

for len in 2 .. n:
    for i in 0 .. n-len:
        j = i + len - 1
        year = n - len + 1            // bottles remaining = len, so this is the sale about to happen
        sellLeft  = price[i] * year + dp[i+1][j]
        sellRight = price[j] * year + dp[i][j-1]
        dp[i][j] = max(sellLeft, sellRight)

return dp[0][n-1]
```

### Alternative: top-down memoized recursion

- Same recurrence, expressed as `profit(i, j, year)` with `year = n - (j - i + 1) + 1`, memoized on `(i, j)`.
- Cleaner to reason about, same complexity.

---

## Complexity Analysis

- **Interval DP (intended):** Time `O(n^2)`, Space `O(n^2)`.
- **Naive recursion without memoization:** exponential (`O(2^n)`), since each `(i, j)` pair is recomputed many times.
