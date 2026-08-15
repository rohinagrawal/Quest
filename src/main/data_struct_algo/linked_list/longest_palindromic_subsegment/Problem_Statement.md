# Longest Palindromic Subsegment in a Linked List

## Problem Description

Given a linked list of characters, find the length of the longest palindromic subsegment (contiguous sequence of nodes) in the linked list.

A palindrome is a sequence that reads the same forwards and backwards. For example, 'a->b->c->b->a' is a palindrome, while 'a->b->c' is not.

## Examples

### Example 1
**Input:** `a -> b -> c -> b -> a`  
**Output:** `5`  
**Explanation:**
- The entire linked list forms a palindrome
- Length of the longest palindromic subsegment: **5**

### Example 2
**Input:** `a -> b -> c -> d -> b -> a`  
**Output:** `3`  
**Explanation:**
- Multiple palindromic subsegments exist: `{a}`, `{b}`, `{c}`, `{d}`, `{b}`, `{a}`, `{b->a->b}` (length 3)
- The longest palindromic subsegment is `b->a->b`
- Length: **3**

### Example 3
**Input:** `a -> a -> a -> b -> b`  
**Output:** `3`  
**Explanation:**
- Palindromic subsegments: `{a}`, `{a->a}`, `{a->a->a}` (length 3), `{b}`, `{b->b}` (length 2)
- The longest palindromic subsegment is `a->a->a`
- Length: **3**

### Example 4
**Input:** `a -> b -> c -> d -> e`  
**Output:** `1`  
**Explanation:**
- No palindromic subsegment of length > 1 exists
- Each single character is a palindrome
- Length: **1**

## Input Format

- The `head` of a singly linked list; each node holds one lowercase letter.

## Output Format

- An integer: the length of the longest contiguous palindromic run of nodes.

---

## Constraints

- `1 <= length of linked list <= 10^5`
- Each node contains a lowercase English letter (`'a'` to `'z'`)

---

## Key Points

1. A linked list has **no random access**, so "expand around center" needs the sequence in an array — copy the characters out first.
2. Check **both** odd-length centers (single node) and even-length centers (between two nodes).
3. Every single node is a palindrome of length `1`, so the answer is at least `1`.

---

## Approach Hints

### Required idea: copy to array, expand around centers

```text
s = characters collected from the list (index 0..n-1)
best = 1
for c in 0..n-1:
    best = max(best, expand(s, c, c))     // odd-length center
    best = max(best, expand(s, c, c+1))   // even-length center
return best

expand(s, l, r): while l>=0 and r<n and s[l]==s[r]: l--; r++
                 return r - l - 1          // matched length
```

### Faster option

- Manacher's algorithm gives `O(n)` if the `O(n^2)` expand is too slow for the largest inputs.

---

## Complexity Analysis

- **Expand-around-center (intended):** Time `O(n^2)`, Space `O(n)` for the array copy.
- **Manacher's:** Time `O(n)`, Space `O(n)` — optimal but more intricate.
