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

## Notes

- A job can be scheduled in any free time slot `t` such that `1 <= t <= deadline`.
- The greedy strategy is to consider jobs by higher profit first and place each job in the latest available slot before its deadline.
