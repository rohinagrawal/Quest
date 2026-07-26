# Scrambled Word Hidden in a Note

## Problem Description

Given a list of `words` and a `note` string, find the one word from the list whose letters can **all** be formed from the letters of the note. Letters need not be contiguous or in order, but each note letter may be used **at most once** (no reuse). Return that word, or `"-"` if none matches. At most one word will match.

Use a **frequency map (multiset) of the note's letters**: a word matches when, for every letter, the word needs no more of it than the note provides. Compare the word's letter counts against the note's counts.

---

## Examples

### Example 1

**Input:**
```text
words = ["baby", "referee", "cat", "dada", "dog", "bird", "ax", "baz"]
note  = "bcanihjsrrrferet"
```

**Output:**
```text
"cat"
```

**Explanation:**
- The note contains `c`, `a`, and `t` (scattered among other letters), so `"cat"` is fully covered.
- Order and adjacency don't matter — only that each needed letter is present with enough count.

### Example 2

**Input:**
```text
words = ["baby", "cat", "dada"]
note  = "tbaykkjlga"
```

**Output:**
```text
"-"
```

**Explanation:**
- `"cat"` needs a `c`, but the note has none.
- `"baby"` needs three `b`'s but the note has one; `"dada"` needs two `d`'s and has none. No word fits, so return `"-"`.

---

## Input Format

- `words` — an array of `W` lowercase strings.
- `note` — a single string of length up to `S`.

## Output Format

- The matching word, or the string `"-"` if none matches.

---

## Constraints

- `1 <= W <= 10^4`
- `1 <= S <= 10^5` (`S` = max length of a word or the note)
- Letters are lowercase `a`–`z`; a note letter cannot be reused across a word's letters.

---

## Key Points

1. Build the note's letter-count map **once**; test each word against a copy — do not rebuild per word.
2. A word matches only if `wordCount[ch] <= noteCount[ch]` for **every** letter `ch`.
3. Duplicate letters are the trap (`"baby"` needs 2 `b`'s) — counts, not set membership, decide the match.

---

## Approach Hints

### Required idea: letter-frequency comparison

```text
noteCount = frequency map of note
for w in words:
    wc = frequency map of w
    if for all ch: wc[ch] <= noteCount[ch]: return w
return "-"
```

---

## Complexity Analysis

- **Frequency map (intended):** Time `O(S + W * S)` worst case, Space `O(1)` (26-letter counts).
- **Naive (sort & subsequence per word):** heavier `O(W * S log S)` with no benefit.
