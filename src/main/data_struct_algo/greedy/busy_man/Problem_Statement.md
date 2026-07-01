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

## Notes

- If an activity ends at time `t`, another activity starting at time `t` can be selected.
- The greedy strategy is to sort activities by increasing end time and always pick the next activity that starts after or at the end time of the last selected activity.
