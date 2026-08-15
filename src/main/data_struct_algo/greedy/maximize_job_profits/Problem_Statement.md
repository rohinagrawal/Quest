# Maximize Job Profits

## Problem Description

You are given `N` jobs. Each job has:

- a deadline by which it must be completed
- a profit earned only if the job is completed on or before its deadline

Each job takes exactly `1` unit of time, and only one job can be done at a time.

Choose and schedule jobs so that the total profit is maximized.

---

## Input Format

- An integer array `deadlines`, where `deadlines[i]` is the deadline of the `i-th` job
- An integer array `profits`, where `profits[i]` is the profit of the `i-th` job

Both arrays have length `N`.

---

## Output Format

Return a single integer: the maximum total profit that can be earned.

---

## Examples

### Example 1

**Input:**
```text
deadlines = [2, 1, 2, 1, 3]
profits   = [100, 19, 27, 25, 15]
```

**Output:**
```text
142
```

**Explanation:** One valid schedule is:

- Time slot `1`: job with profit `25` and deadline `1`
- Time slot `2`: job with profit `100` and deadline `2`
- Time slot `3`: job with profit `15` and deadline `3`

Total profit = `25 + 100 + 15 = 140`.

Another better schedule is:

- Time slot `1`: job with profit `27` and deadline `2`
- Time slot `2`: job with profit `100` and deadline `2`
- Time slot `3`: job with profit `15` and deadline `3`

Total profit = `27 + 100 + 15 = 142`.

### Example 2

**Input:**
```text
deadlines = [1, 1, 1]
profits   = [10, 20, 30]
```

**Output:**
```text
30
```

**Explanation:** Since all jobs have deadline `1`, only one job can be completed. Pick the job with profit `30`.

---

## Constraints

- `1 <= N <= 10^5`
- `1 <= deadlines[i] <= N`
- `1 <= profits[i] <= 10^9`

---

## Key Points

1. Consider jobs by **descending profit**; place each in the **latest** free slot `<= deadline`.
2. Latest-slot placement keeps earlier slots open for other tight-deadline jobs.
3. Use a **DSU over time slots** (find the nearest free slot `<= t`) for near-linear, or a boolean slot array for `O(N * maxDeadline)`.

---

## Approach Hints

### Required idea: greedy by profit + latest-slot assignment

```text
sort jobs by profit descending
for job (deadline d, profit p):
    slot = latest free time <= d          // DSU find(d)
    if slot >= 1:
        total += p
        mark slot used; union(slot -> slot - 1)   // next search skips it
return total
```

### Finding the latest free slot

- DSU parent `t` points to the nearest free slot `<= t`; after filling `slot`, union it to `slot - 1` so future finds jump over it.

---

## Complexity Analysis

- **Greedy + DSU (intended):** Time `O(N log N)` sort + `O(N α(N))`, Space `O(N)`.
- **Boolean-slot scan:** `O(N * maxDeadline)` — simpler but slower when deadlines are large.
