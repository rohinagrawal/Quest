# Busy Man

## Problem Description

You are given `N` activities. Each activity has:

- a start time
- an end time

A person can do only one activity at a time. An activity can be selected only if it does not overlap with the previously selected activity.

Choose the maximum number of activities that the person can complete.

---

## Input Format

- An integer array `start`, where `start[i]` is the start time of the `i-th` activity
- An integer array `end`, where `end[i]` is the end time of the `i-th` activity

Both arrays have length `N`.

---

## Output Format

Return a single integer: the maximum number of non-overlapping activities that can be completed.

---

## Examples

### Example 1

**Input:**
```text
start = [1, 3, 0, 5, 8, 5]
end   = [2, 4, 6, 7, 9, 9]
```

**Output:**
```text
4
```

**Explanation:** One optimal set of activities is:

- Activity `[1, 2]`
- Activity `[3, 4]`
- Activity `[5, 7]`
- Activity `[8, 9]`

These activities do not overlap, so the maximum count is `4`.

### Example 2

**Input:**
```text
start = [1, 2, 3]
end   = [4, 5, 6]
```

**Output:**
```text
1
```

**Explanation:** All activities overlap with each other, so only one activity can be selected.

---

## Constraints

- `1 <= N <= 10^5`
- `0 <= start[i] < end[i] <= 10^9`

---

## Key Points

1. **Sort by end time** — finishing earliest leaves the most room for later activities.
2. An activity starting exactly at the previous end is allowed (`start >= lastEnd`).
3. Sorting by start time or by shortest duration is **not** optimal; end time is the greedy key.

---

## Approach Hints

### Required idea: activity selection (greedy by earliest finish)

```text
pairs = zip(end, start);  sort by end ascending
count = 0;  lastEnd = -infinity
for (e, s) in pairs:
    if s >= lastEnd:          // no overlap with last chosen
        count++; lastEnd = e
return count
```

---

## Complexity Analysis

- **Greedy (intended):** Time `O(N log N)` for the sort, Space `O(N)`.
- **Naive (try all subsets):** `O(2^N)` — infeasible.
