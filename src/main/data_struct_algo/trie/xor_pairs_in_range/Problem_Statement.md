# Count Pairs With XOR in a Range

## Problem Description

Given a **(0-indexed)** integer array `nums` and two integers `low` and `high`, return the number of **nice pairs**.

A **nice pair** is a pair `(i, j)` where:
- `0 <= i < j < nums.length`
- `low <= (nums[i] XOR nums[j]) <= high`

---

## Examples

### Example 1:

**Input:** `nums = [1,4,2,7], low = 2, high = 6`
**Output:** `6`
**Explanation:**
All nice pairs (i, j) are as follows:
- (0, 1): nums[0] XOR nums[1] = 5
- (0, 2): nums[0] XOR nums[2] = 3
- (0, 3): nums[0] XOR nums[3] = 6
- (1, 2): nums[1] XOR nums[2] = 6
- (1, 3): nums[1] XOR nums[3] = 3
- (2, 3): nums[2] XOR nums[3] = 5

### Example 2:

**Input:** `nums = [9,8,4,2,1], low = 5, high = 14`
**Output:** `8`
**Explanation:**
All nice pairs (i, j) are as follows:
- (0, 2): nums[0] XOR nums[2] = 13
- (0, 3): nums[0] XOR nums[3] = 11
- (0, 4): nums[0] XOR nums[4] = 8
- (1, 2): nums[1] XOR nums[2] = 12
- (1, 3): nums[1] XOR nums[3] = 10
- (1, 4): nums[1] XOR nums[4] = 9
- (2, 3): nums[2] XOR nums[3] = 6
- (2, 4): nums[2] XOR nums[4] = 5

---

## Input Format

- `nums` — a 0-indexed integer array.
- `low`, `high` — the inclusive XOR range bounds.

## Output Format

- An integer: the count of pairs `(i, j)` with `i < j` and `low <= nums[i] XOR nums[j] <= high`.

---

## Constraints

- `1 <= nums.length <= 2 * 10^4`
- `1 <= nums[i] <= 2 * 10^4`
- `1 <= low <= high <= 2 * 10^4`

---

## Key Points

1. **Range → prefix difference:** `count(low <= XOR <= high) = countLE(high) - countLE(low - 1)`.
2. A **binary trie** with per-node subtree counts answers `countLE(x, limit)` = how many stored values `y` have `x XOR y <= limit`, in `O(B)`.
3. Insert elements **incrementally** (query before insert) so each unordered pair is counted exactly once.

---

## Approach Hints

### Required idea: binary trie with counts + countXorLessOrEqual

```text
answer = 0; trie (nodes carry a count)
for x in nums:
    answer += query(x, high) - query(x, low - 1)   // over already-inserted values
    trie.insert(x)
return answer
```

### query(x, limit)

- Walk bits MSB→LSB; at each bit look at `limit`'s bit: if it is `1`, all values in the child matching `x`'s bit are guaranteed `<=` (add that subtree's count) and descend the other child; if `0`, descend the child that keeps XOR's bit `0`.

---

## Complexity Analysis

- **Binary trie (intended):** Time `O(n · B)` with `B ≈ 15` bits, Space `O(n · B)`.
- **Naive (all pairs):** `O(n^2)` XOR checks.