# Implement Heap

## Problem Description

Implement a generic binary **heap** that supports both **min-heap** and **max-heap** variants, with the type fixed at construction. Back it with a dynamic array and keep the heap invariant after every operation (min-heap: parent ≤ children; max-heap: parent ≥ children).

Support these operations: `Heap(arr, type)` builds in `O(n)` via **bottom-up heapify**; `insert(v)` adds an element with **heapify-up** in `O(log n)`; `getTop()` returns the min/max in `O(1)`; `removeTop()` removes and returns it with **heapify-down** in `O(log n)`; `removeNode(v)` deletes an arbitrary value in `O(n + log n)`. Given a sequence of operations, return the output of each query call.

---

## Examples

### Example 1

**Input:**
```text
ops  = ["Heap", "getTop", "insert", "removeTop", "getTop"]
args = [[[4,10,3,5,1], "MIN"], [], [2], [], []]
```

**Output:**
```text
[null, 1, null, 1, 2]
```

**Explanation:**
- Build a **min-heap** from `[4,10,3,5,1]` → top is `1`; `getTop() = 1`.
- `insert(2)` → heap holds `{1,2,3,4,5,10}`; `removeTop()` pops and returns the min `1`.
- After removal the smallest remaining is `2`, so the final `getTop() = 2`.

### Example 2

**Input:**
```text
ops  = ["Heap", "getTop", "removeTop", "getTop"]
args = [[[4,10,3,5,1], "MAX"], [], [], []]
```

**Output:**
```text
[null, 10, 10, 5]
```

**Explanation:**
- Build a **max-heap** → top is `10`; `getTop() = 10`, `removeTop()` returns `10`.
- The next-largest `5` bubbles to the top via heapify-down, so `getTop() = 5`.

---

## Input Format

- `ops` — array of operation names; the matching `args[i]` holds that call's arguments.
- The `Heap` constructor takes an initial array and a type flag `"MIN"` / `"MAX"`.

## Output Format

- An array with one entry per operation: the query result, or `null` for constructor / `insert` / `removeNode`.

---

## Constraints

- `1 <= number of operations <= 10^5`
- `-2^31 <= value <= 2^31 - 1`
- `getTop` / `removeTop` are only called on a non-empty heap.

---

## Key Points

1. A single **comparator** parameterizes min vs max; the rest of the logic is identical.
2. **Bottom-up build** heapifies from the last internal node down for `O(n)`, beating `O(n log n)` repeated inserts.
3. `insert` sifts **up**; `removeTop` swaps the last element to the root and sifts **down**.
4. `removeNode(v)` finds `v` in `O(n)`, swaps it with the last element, then sifts up or down to restore order.

---

## Approach Hints

### Required idea: array-backed heap with sift up/down

```text
parent(i) = (i-1)/2;  left(i) = 2i+1;  right(i) = 2i+2

build(arr):   copy arr; for i from n/2-1 down to 0: siftDown(i)   // O(n)
insert(v):    append v; siftUp(last)
removeTop():  top = a[0]; a[0] = a.pop(); siftDown(0); return top
siftUp(i):    while i>0 and better(a[i], a[parent(i)]): swap; i = parent(i)
siftDown(i):  pick better child; if it beats a[i]: swap and recurse
```

`better(x, y)` = `x < y` for MIN, `x > y` for MAX.

---

## Complexity Analysis

- **Intended:** build `O(n)`, `insert`/`removeTop` `O(log n)`, `getTop` `O(1)`, `removeNode` `O(n + log n)`; space `O(n)`.
- **Sorted array alternative:** `getTop` `O(1)` but `insert` `O(n)` — worse for mixed workloads.
