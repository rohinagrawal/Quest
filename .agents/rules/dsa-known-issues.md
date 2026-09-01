---
description: Known-broken and unsolved DSA problems, discovered while adding the CodeTest.java/Tests.cpp test scaffold
alwaysApply: false
---

# DSA Known Issues

Reference this when working on a DSA problem below, or before writing a test for one — it explains why a problem has no test yet, so the failure isn't rediscovered from scratch. See `quest-conventions.md` for the test convention itself.

## Broken solutions (fail their own documented example, or don't compile)

Each of these already has a `Problem_Statement.md` and a solution file, but the solution is wrong or doesn't build. No `CodeTest.java`/`Tests.cpp` exists for them — writing one would either need to assert wrong output (don't) or can't compile.

| Problem | Issue |
| --- | --- |
| `array/next_permutation` | `nextPermutation` is an unimplemented stub (`return A;`) |
| `hash_map/scrambled_word_from_array` | shared frequency-map corrupted across word candidates, never resets between attempts |
| `graph/nearest_targets_from_sources` | wrong output order; drops unreachable sources instead of emitting `-1` |
| `graph/traversal` | `dfs()` order mismatch; no multi-component support |
| `graph/smallest_cost_path` | `vector<queue<int>> buckets(maxCost+1, {})` is an ambiguous constructor call, doesn't compile under GCC 16 |
| `heaps/merge_sorted_arrays` | `Code.cpp` implements an unrelated median-of-stream solution, not merge-k-arrays |
| `linked_list/flatten_multilevel_doubly_linked_list` | `Node` fields are implicitly private, doesn't compile |
| `tree/flatten_binary_tree_to_doubly_linked_list` | copy-pasted from the singly-linked sibling solution, always sets `left = NULL` |
| `tree/leaf_nodes_bst_without_tree` | stub containing invalid Java array syntax in a `.cpp` file |
| `tree/morris_traversal` | undeclared identifier typo (`cur` vs `curr`) breaks compilation; `postOrderTraversal` is an empty stub |

`graph/dijikstras` is partial, not fully broken: `dijikstras_heap()` is tested and passes; `dijikstras_set()` has an empty body and is intentionally untested.

## Unsolved stubs (never attempted, no test expected)

These `Code.cpp` files are empty (`class Code { public: };`) or have empty method bodies — confirmed by direct inspection, not yet attempted:

```text
dp/decode
dp/distinct_subsequences
dp/edit_distance
dp/longest_common_subsequence
dp/longest_common_substring
dp/longest_palindromic_subsequence
dp/longest_palindromic_substring
dp/max_product_subarray
dp/max_sum_non_adjacent_grid
dp/max_triplet_weighted_sum
dp/odd_palindrome_subseq_center
dp/regex_matching
dp/ways_to_party
dp/wildcard_matching
dp/wine_seller_profit
graph/edge_to_decrease_cost
graph/min_max_edge
graph/multiple_with_0_1
graph/number_of_regions
graph/reverse_edge_to_destination
graph/shortest_distance_with_two_weights
graph/sort_nodes_by_group_dependencies
graph/stones_removed
graph/tree_splitting
greedy/busy_man
greedy/maximize_job_profits
greedy/minimum_jumps
heaps/median_of_number_stream
linked_list/longest_palindromic_subsegment
segment_tree/max_sum_pair_from_range
stack/longest_balanced_brackets
tree/max_bst_in_binary_tree
trie/word_search_in_grid
```

## When one of these gets solved or fixed

Do not write the solution yourself unless explicitly asked — these are the user's own practice problems. Once the user fixes one, add its `CodeTest.java`/`Tests.cpp` per `quest-conventions.md`, and delete its row/entry from this file.
