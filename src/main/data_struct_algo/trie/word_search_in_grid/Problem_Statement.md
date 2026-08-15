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

## Input Format

- `words` — array of target words.
- `board` — a 2D grid of lowercase letters.

## Output Format

- The list of words from `words` that can be formed on the board (order unspecified; de-duplicated).

---

## Constraints

- `1 <= words.length <= 10^4`
- `1 <= words[i].length <= 10`
- `1 <= board.length, board[0].length <= 12`
- `words[i]` and `board` consist of only lowercase English letters
- All strings in `words` are unique

---

## Key Points

1. Build a **Trie of all words** and DFS the grid *once*, following trie edges — dead prefixes are pruned instantly (far better than searching each word separately).
2. Mark a cell **visited during the current path** (no reuse within one word); **unmark on backtrack** so other paths can use it.
3. When a trie node's end-flag is reached, record its word; different words may reuse the same cells.

---

## Approach Hints

### Required idea: Trie + DFS backtracking

```text
build trie from words
for each cell (r, c):
    dfs(r, c, root)

dfs(r, c, node):
    ch = board[r][c]
    if node has no child ch: return
    nxt = node.child[ch]
    if nxt.isEnd: results.add(nxt.word)      // de-dup
    mark (r, c) visited
    for (nr, nc) in 4-neighbors:
        if in-bounds and not visited: dfs(nr, nc, nxt)
    unmark (r, c)
```

---

## Complexity Analysis

- **Trie + DFS (intended):** build `O(sum of word lengths)`; search `O(R · C · 4^L)` worst case, heavily pruned by the trie (`L` = max word length).
- **Naive (DFS per word):** `O(words · R · C · 4^L)` — repeats grid work for every word.