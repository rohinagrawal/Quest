# Min Stack

## Problem Description

Design a stack that supports push, pop, top, and retrieving the minimum element in constant time.

Implement the `Code` class:

- `Code()` - initializes the stack object.
- `void push(int val)` - pushes the element val onto the stack.
- `void pop()` - removes the element on the top of the stack.
- `int top()` - gets the top element of the stack.
- `int getMin()` - retrieves the minimum element in the stack.

**You must implement a solution with O(1) time complexity for each function.**

---

## Examples

### Example 1

**Input:**
```text
["MinStack","push","push","push","getMin","pop","top","getMin"]
[[],[-2],[0],[-3],[],[],[],[]]
```

**Output:**
```text
[null,null,null,null,-3,null,0,-2]
```

**Explanation:**
```c++
Code minStack = new Code();
minStack.push(-2);
minStack.push(0);
minStack.push(-3);
minStack.getMin(); // return -3
minStack.pop();
minStack.top();    // return 0
minStack.getMin(); // return -2
```

---

## Input Format

- A sequence of method calls: `push(val)`, `pop()`, `top()`, `getMin()`, applied to one `Code` instance.

## Output Format

- For each `top()` / `getMin()` call, its returned value (constructor / `push` / `pop` return nothing).

---

## Constraints

- `-2^31 <= val <= 2^31 - 1`
- Methods `pop`, `top` and `getMin` operations will always be called on non-empty stacks.
- At most `3 * 10^4` calls will be made to `push`, `pop`, `top`, and `getMin`.

---

## Key Points

1. Every operation must be `O(1)` — you cannot scan the stack to find the min.
2. Simplest approach: keep an **auxiliary stack** of running minimums parallel to the main stack.
3. Space-optimized approach: store an **encoded delta** (`2*val - min`) when a new min arrives, and decode on pop to recover the previous min — one stack, `O(1)` extra.

---

## Approach Hints

### Required idea: track the minimum alongside the stack

```text
push(v):  minStack.push(min(v, minStack.top or +inf)); stack.push(v)
pop():    stack.pop(); minStack.pop()
top():    return stack.top()
getMin(): return minStack.top()
```

### Single-stack encoding (optional)

- On `push(v)` when `v < min`: store `2*v - min` and set `min = v`.
- On `pop()` when `top < min` (an encoded marker): recover `min = 2*min - top`.

---

## Complexity Analysis

- **Two-stack / encoded (intended):** Time `O(1)` per operation, Space `O(n)` (or `O(1)` extra with the encoding trick).
- **Naive `getMin` (scan):** `O(n)` per `getMin` — violates the constant-time requirement.
