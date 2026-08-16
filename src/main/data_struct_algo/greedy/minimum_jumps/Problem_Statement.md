# Minimum Jumps to Reach the Last Index

## Problem Description

You are given an array `A` of `N` non-negative integers and start at index `0`. `A[i]` is the **maximum** jump length from index `i` — from `i` you may move to any index in `i+1 .. i+A[i]`. Return the **minimum number of jumps** to reach the last index `N-1`, or `-1` if it is unreachable.

Solve it greedily as a **BFS over ranges**: think of each "jump count" as a BFS layer. Track the farthest index reachable within the current number of jumps (`curEnd`) and the farthest reachable with **one more** jump (`farthest`). When your scan reaches `curEnd`, you must spend a jump and extend the frontier to `farthest`. If the frontier ever fails to move past the current index, the end is unreachable.

---

## Examples

### Example 1

**Input:**
```text
A = [2, 3, 1, 1, 4]
```

**Output:**
```text
2
```

**Explanation:**
- From index `0` (value `2`) you can reach index `1` or `2`.

```text
idx:   0    1    2    3    4
val:   2    3    1    1    4
jump:  0 -------> 1 --------------> 4
```

- Jump `0 → 1` (value `3` reaches up to index `4`), then `1 → 4`. That is **2** jumps — fewer than `0→2→3→4`.

### Example 2

**Input:**
```text
A = [3, 2, 1, 0, 4]
```

**Output:**
```text
-1
```

**Explanation:**
- The best frontier from the start reaches index `3` (`0 + 3`), but `A[3] = 0`, so you cannot move past index `3`.
- Index `4` is never reachable, so the answer is `-1`.

---

## Input Format

- `A` — an array of `N` non-negative integers (jump lengths).

## Output Format

- An integer: the minimum number of jumps to reach index `N-1`, or `-1` if impossible.

---

## Constraints

- `1 <= N <= 10^5`
- `0 <= A[i] <= 10^5`
- A single element (`N == 1`) needs `0` jumps (already at the last index).

---

## Key Points

1. **Greedy = BFS layers:** each jump is one BFS level; within a level you extend to the farthest index reachable so far.
2. Spend a jump only when the scan index **hits `curEnd`** — the boundary of what the current jump count can reach.
3. **Unreachable check:** if the frontier `farthest` never exceeds the current index (e.g. a `0` blocks the way), return `-1`.
4. You never need to jump *from* the last index, so the scan runs to `N-2`.

---

## Approach Hints

### Required idea: greedy frontier expansion (BFS by ranges)

```text
if N == 1: return 0
jumps = 0; curEnd = 0; farthest = 0
for i in 0 .. N-2:
    farthest = max(farthest, i + A[i])
    if i == curEnd:                 // reached edge of current jump's range
        jumps++
        curEnd = farthest
        if curEnd >= N-1: return jumps
    if curEnd <= i and i < N-1 and farthest <= i:
        return -1                   // stuck: cannot advance past i
return curEnd >= N-1 ? jumps : -1
```

### Why greedy is optimal

- All indices reachable with `k` jumps form a contiguous prefix `0 .. curEnd`; extending to the farthest reachable index is never worse, so the layer count is minimal.

### DP variant: min jumps to each index

Let `dp[i]` = minimum jumps to reach index `i` (`INF` if unreachable). Relax forward from every position to all indices it can jump to:

```text
dp[0] = 0;  dp[1..N-1] = INF
for i in 0 .. N-1:
    if dp[i] == INF: continue                 // i itself unreachable
    for j in i+1 .. min(N-1, i + A[i]):
        dp[j] = min(dp[j], dp[i] + 1)
return dp[N-1] == INF ? -1 : dp[N-1]
```

- Trace on `[2,3,1,1,4]`: `dp = [0,1,1,2,2]` → `dp[4] = 2`.
- Trace on `[3,2,1,0,4]`: `dp = [0,1,1,1,INF]` → `dp[4] = INF` → `-1`.
- Simpler to reason about, but `O(N^2)`; the greedy above is the same answer in `O(N)`.

---

## Complexity Analysis

- **Greedy (intended):** Time `O(N)`, Space `O(1)`.
- **DP (`dp[i]` = min jumps to `i`):** Time `O(N^2)` worst case, Space `O(N)` — clearer but slower.
- **BFS with explicit queue:** `O(N)` time but `O(N)` space and more overhead than the greedy frontier.
