# Minimum Number of Refueling Stops

## Problem Description

A car starts at position `0` and wants to reach destination `A` miles to the right.

The car initially has `B` liters of fuel, consumes `1` liter per mile, and has an infinite tank capacity.

There are gas stations on the way:
- the `i-th` station is at position `C[i]`
- it provides `D[i]` liters of fuel

Find the minimum number of refueling stops needed to reach destination `A`.  
If reaching the destination is impossible, return `-1`.

Notes:
- If the car reaches a station with `0` fuel left, it can still refuel there.
- Reaching destination `A` with `0` fuel left is considered successful.

---

## Input Format

- First argument: integer `A` (destination position)
- Second argument: integer `B` (initial fuel)
- Third argument: integer array `C` (station positions)
- Fourth argument: integer array `D` (fuel available at stations)

---

## Output Format

Return a single integer: minimum number of refueling stops required to reach `A`, or `-1` if unreachable.

---

## Examples

### Example 1

**Input:**
```text
A = 1
B = 1
C = []
D = []
```

**Output:**
```text
0
```

**Explanation:** The car can directly reach destination `1` without any refuel.

### Example 2

**Input:**
```text
A = 100
B = 1
C = [10]
D = [100]
```

**Output:**
```text
-1
```

**Explanation:** The first station is unreachable, so destination cannot be reached.

### Example 3

**Input:**
```text
A = 100
B = 10
C = [10, 20, 30, 60]
D = [60, 30, 30, 40]
```

**Output:**
```text
2
```

**Explanation:** Refuel optimally at two stations to reach destination `100`.

---

## Constraints

- `1 <= A <= 10^9`
- `1 <= B <= 10^9`
- `0 <= C.length = D.length <= 10^5`
- `0 < C[i] < A`
- `1 <= D[i] <= 10^9`
- `C` is strictly increasing

---

## Key Points

1. **Lazy greedy with a max-heap:** don't refuel at a station immediately — bank its fuel as an option and spend it only when you'd otherwise run out.
2. When stuck short of the next point, pop the **largest** banked fuel; each pop is one stop.
3. If the heap is empty and you still can't advance, the destination is unreachable → `-1`.

---

## Approach Hints

### Required idea: max-heap of deferred fuel

```text
heap = max-heap;  fuel = B;  stops = 0;  i = 0
for target in [C[0], C[1], ..., A]:        // each station, then A
    while fuel < target:
        if heap empty: return -1
        fuel += heap.pop(); stops++        // use biggest banked fuel
    if target is a station: heap.push(D[i]); i++
return stops
```

### Reframing

- Passing a station "unlocks" its fuel as a future option; you commit to the best options only as far as needed to keep moving.

---

## Complexity Analysis

- **Max-heap greedy (intended):** Time `O(n log n)`, Space `O(n)`.
- **DP (`dp[t]` = farthest reach with `t` stops):** `O(n^2)` — correct but heavier.
