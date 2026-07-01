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

## Constraints

- `1 ≤ length of linked list ≤ 10^5`
- Each node contains a lowercase English letter ('a' to 'z')

## Approach

There are multiple approaches to solve this problem:

### Approach 1: Brute Force
- For each node, consider it as a potential center or start of a palindrome
- Expand around the center or check all subsegments starting from that node
- Time Complexity: O(n^2), Space Complexity: O(1)

### Approach 2: Convert to Array
- Convert the linked list to an array/string
- Apply standard longest palindromic substring algorithms
- Time Complexity: O(n^2) or O(n) with Manacher's algorithm
- Space Complexity: O(n)

### Approach 3: Two-Pointer with Reverse
- For each possible center, use two pointers to expand outwards
- Check both odd-length and even-length palindromes
- Time Complexity: O(n^2), Space Complexity: O(1)

## Note

This problem requires careful handling of linked list traversal and comparison of nodes in forward and reverse directions.
