# Ways to Party

## Problem Description

There are `n` people at a party, numbered `1..n`. Every person either parties **alone** or pairs up with **exactly one** other person. Count the number of distinct ways the whole group can be arranged, where an arrangement is the set of singles and unordered pairs formed — pair `(1,2)` is the same as `(2,1)`, and the order in which pairs are listed does not matter.

Solve this with **linear dynamic programming** on the recurrence `f(n) = f(n-1) + (n-1) * f(n-2)`: person `n` either stays alone (leaving `f(n-1)` ways for the rest) or pairs with any one of the other `n-1` people (leaving `f(n-2)` ways). Because the count grows factorially, return the answer **modulo `10^9 + 7`**.

---

## Examples

### Example 1

**Input:**
```text
n = 3
```

**Output:**
```text
4
```

**Explanation:**

```text
{1} {2} {3}      all three alone
{1,2} {3}        1 pairs with 2, 3 alone
{1,3} {2}        1 pairs with 3, 2 alone
{2,3} {1}        2 pairs with 3, 1 alone
```

- Person `3` alone → the remaining `{1,2}` can be arranged in `f(2) = 2` ways.
- Person `3` pairs with one of the `2` others → each choice leaves `f(1) = 1` way, adding `2 * 1 = 2`.
- Total `f(3) = f(2) + 2 * f(1) = 2 + 2 = 4`, exactly the enumeration above.

### Example 2

**Input:**
```text
n = 4
```

**Output:**
```text
10
```

**Explanation:**
- `f(4) = f(3) + 3 * f(2) = 4 + 3 * 2 = 10`.
- The `3 * f(2)` term shows why the multiplier matters: person `4` has **3** distinct partners, and each choice is a different arrangement even though the leftover pair contributes the same `f(2)` count.
- Base cases anchor the recurrence: `n = 0` (empty party) has `f(0) = 1` — the single empty arrangement — and `n = 1` has `f(1) = 1`.

---

## Input Format

- A single integer `n` — the number of people at the party.

## Output Format

- A single integer: the number of valid arrangements, taken **modulo `10^9 + 7`**.

---

## Constraints

- `0 <= n <= 10^6`
- Answer must be reported modulo `10^9 + 7`
- Pairs are **unordered**; the ordering of singles and pairs within an arrangement is irrelevant

---

## Key Points

1. The multiplier is `n-1`, not `n` — person `n` is fixed as the one being placed, so only the *partner* choice is free.
2. Each pair is counted once, not twice: anchoring the pair on person `n` removes the `(a,b)` / `(b,a)` duplication automatically.
3. `f(0) = 1` (not `0`) — the empty arrangement must count, or `f(2) = f(1) + 1 * f(0)` collapses to `1` instead of `2`.
4. The product `(n-1) * f(n-2)` overflows 32-bit and needs 64-bit intermediates; take the modulus at every step.

---

## Approach Hints

### Required idea: linear DP on the pairing recurrence

```text
MOD = 1_000_000_007
if n <= 1: return 1
prev2 = 1        # f(0)
prev1 = 1        # f(1)
for i in 2..n:
    cur = (prev1 + (i - 1) * prev2) % MOD
    prev2 = prev1
    prev1 = cur
return prev1
```

- Every `f(i)` depends only on `f(i-1)` and `f(i-2)`, so two rolling variables replace the full table.
- Compute `(i - 1) * prev2` in 64-bit before reducing — with `prev2 < 10^9` and `i` up to `10^6` the product reaches `~10^15`.

### If all values are needed

- Fill a table `dp[0..n]` instead of rolling variables when the caller wants every `f(i)`; the recurrence and per-entry cost are unchanged.

---

## Complexity Analysis

- **Intended approach:** Time `O(n)`, Space `O(1)` with rolling variables (`O(n)` if the full table is kept).
- **Naive approach:** Enumerating every set partition into singles and pairs is super-exponential and infeasible beyond `n ≈ 20`.