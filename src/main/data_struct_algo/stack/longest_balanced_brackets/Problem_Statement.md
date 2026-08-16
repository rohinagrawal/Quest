# Longest Balanced Bracket Substring

## Problem Description

Given a string `A` of brackets drawn from the three types `()`, `[]`, and `{}`, find the **length of the longest contiguous substring** that forms a **balanced** (well-formed) bracket string. A string is balanced if it is empty, or is `(B)`/`[B]`/`{B}` for a balanced `B`, or is the concatenation `B1B2` of two balanced strings.

Solve it with a **stack of indices**: push a base marker `-1`, push the index of each opening bracket, and on a closing bracket check whether the index on top of the stack is a **matching** opening. If it matches, pop it and the current valid length is `i - stack.top()`; if it does not match (wrong type or nothing to match), push `i` as a **new base** to reset. Track the maximum length seen.

Matching by **type** is what separates this from single-type longest-valid-parentheses: `"(]"` must not count.

---

## Examples

### Example 1

**Input:**
```text
A = ")([]{})"
```

**Output:**
```text
6
```

**Explanation:**
- Index the string; the leading `)` has nothing to match and becomes a base:

```text
idx:  0  1  2  3  4  5  6
char: )  (  [  ]  {  }  )
```

- From index `1`, the substring `([]{})` is balanced — `[]` and `{}` are each balanced, concatenated and wrapped in `()`.
- The unmatched leading `)` is skipped; the answer is the length of `A[1..6] = 6`.

### Example 2

**Input:**
```text
A = "(){]"
```

**Output:**
```text
2
```

**Explanation:**
- `()` at indices `0..1` is balanced → length `2`.
- Then `{` is opened but `]` closes the **wrong type**, so the match fails and the stack resets — no longer balanced run continues. The best is `2`.

---

## Input Format

- A string `A` consisting only of the characters `(`, `)`, `[`, `]`, `{`, `}`.

## Output Format

- An integer: the length of the longest balanced contiguous substring (`0` if none).

---

## Constraints

- `1 <= |A| <= 10^5`
- `A[i] ∈ { '(', ')', '[', ']', '{', '}' }`

---

## Key Points

1. **Type must match** — a `)` only cancels a `(`, never a `[` or `{`; a mismatch resets the run.
2. The stack holds **indices**, not brackets, so lengths come out as `i - stack.top()`.
3. Seed the stack with `-1` as a base; on any unmatched closing bracket, push its index as the new base.
4. Length is measured from the element **below** the popped opener, which is why the base markers matter.

---

## Approach Hints

### Required idea: index stack with base markers

```text
stack = [-1];  best = 0
for i, c in A:
    if c is an opening bracket:
        stack.push(i)
    else:                                   // closing bracket
        top = stack.top()
        if top != -1 and A[top] is the opening that matches c:
            stack.pop()
            best = max(best, i - stack.top())
        else:
            stack.push(i)                   // unmatched → new base
return best
```

### Why lengths are correct

- After popping a matched opener, `stack.top()` is the index just before the current valid run, so `i - stack.top()` spans the whole balanced stretch — including earlier adjacent balanced pieces.

### DP alternative: longest valid ending at each index

Let `dp[i]` = length of the longest balanced substring **ending exactly at** index `i`. An opening bracket can never end a balanced string, so `dp[i] = 0` there. For a **closing** bracket, look past the balanced block already ending at `i-1` to find the character that must be its partner:

```text
dp[i] = 0                                  // default (openings, mismatches)
if A[i] is a closing bracket:
    j = i - 1 - dp[i-1]                    // index that must hold the matching opener
    if j >= 0 and A[j] is the opening matching A[i]:
        dp[i] = dp[i-1] + 2 + (j-1 >= 0 ? dp[j-1] : 0)
answer = max(dp)
```

- `dp[i-1]` skips the inner balanced block, `+2` counts the new matched pair, and `dp[j-1]` **chains** any balanced run immediately before the opener.
- The **type check** `A[j] matches A[i]` is the only change from single-type longest-valid-parentheses; a wrong-type partner leaves `dp[i] = 0`.
- Trace on `)([]{})`: `dp = [0,0,0,2,0,4,6]` → max `6`. On `(){]`: `dp = [0,2,0,0]` → max `2`.

---

## Complexity Analysis

- **Index stack (intended):** Time `O(n)` (each index pushed/popped once), Space `O(n)`.
- **DP (`dp[i]` = longest valid ending at `i`):** also `O(n)` time, `O(n)` space — one pass, no stack, using the recurrence above.
- **Naive (check every substring):** `O(n^3)` — infeasible.
