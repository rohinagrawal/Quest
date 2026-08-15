# Maximum XOR Subarray

## Problem Description

Given an array of integers `arr`, find the **subarray (contiguous)** which has the **maximum XOR value**.

Return the maximum XOR value.

---

## Examples

### Example 1:

**Input:** `arr = [1, 2, 3, 4]`
**Output:** `7`
**Explanation:**
The subarray [3, 4] has maximum XOR value = 3 XOR 4 = 7

### Example 2:

**Input:** `arr = [8, 1, 2, 12]`
**Output:** `15`
**Explanation:**
The subarray [1, 2, 12] has maximum XOR value = 1 XOR 2 XOR 12 = 15

### Example 3:

**Input:** `arr = [4, 6]`
**Output:** `6`
**Explanation:**
The subarray [6] has maximum XOR value = 6

---

## Input Format

- A single integer array `arr`.

## Output Format

- An integer: the maximum XOR over all contiguous subarrays.

---

## Constraints

- `1 <= arr.length <= 10^5`
- `0 <= arr[i] <= 2^31 - 1`

---

## Key Points

1. Subarray XOR `(l..r) = prefix[r] XOR prefix[l-1]`, reducing the task to "max XOR over pairs of prefix values".
2. Store prefixes in a **binary trie** (fixed 31-bit width, MSB first); greedily follow the **opposite** bit to maximize XOR.
3. Insert the **empty prefix `0`** first so subarrays starting at index `0` are covered.

---

## Approach Hints

### Required idea: prefix XOR + binary trie (max-XOR query)

```text
trie.insert(0)                       // empty prefix
pref = 0; best = 0
for x in arr:
    pref ^= x
    best = max(best, trie.maxXor(pref))   // greedy opposite-bit walk
    trie.insert(pref)
return best
```

- `maxXor(p)` walks bits `30..0`, choosing the child whose bit differs from `p`'s when it exists.

---

## Complexity Analysis

- **Prefix XOR + trie (intended):** Time `O(n · B)` with `B ≈ 31` bits, Space `O(n · B)`.
- **Naive (all subarrays):** `O(n^2)` XOR computations.