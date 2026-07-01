# Word Search in Grid

## Problem Description

Given an array of words `words` and a 2D matrix of characters `board`, find all words from the array that can be constructed from the matrix by reading **sequentially adjacent cells** either **horizontally or vertically**.

Return the list of words that exist in the grid.

**Note:** The same cell **cannot be used more than once** in a single word.

---

## Examples

### Example 1:

**Input:**
```
words = ["oath", "pea", "eat", "rain"]
board = [
  ['o','a','a','n'],
  ['e','t','a','e'],
  ['i','h','k','r'],
  ['i','f','l','v']
]
```
**Output:** `["oath", "eat"]`
**Explanation:**
- "oath" can be formed starting from board[0][0]
- "eat" can be formed starting from board[1][0]
- "pea" and "rain" cannot be formed

### Example 2:

**Input:**
```
words = ["cat", "dog"]
board = [
  ['c','a','t'],
  ['d','o','g']
]
```
**Output:** `["cat", "dog"]`
**Explanation:**
- "cat" can be formed: board[0][0] -> board[0][1] -> board[0][2]
- "dog" can be formed: board[1][0] -> board[1][1] -> board[1][2]

### Example 3:

**Input:**
```
words = ["abcb"]
board = [
  ['a','b'],
  ['c','d']
]
```
**Output:** `[]`
**Explanation:**
"abcb" cannot be formed as 'b' would need to be used twice

---

## Constraints

- `1 <= words.length <= 10^4`
- `1 <= words[i].length <= 10`
- `1 <= board.length, board[0].length <= 12`
- `words[i]` and `board` consist of only lowercase English letters
- All strings in `words` are unique

---

## Notes

- A word can start from any cell in the grid
- Adjacent cells are horizontal or vertical neighbors (not diagonal)
- Each cell can be used at most once per word
- Different words can reuse the same cells