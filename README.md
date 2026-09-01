# Quest

Quest is a practice repository for interview preparation and engineering design exercises. It collects data structures and algorithms problems, machine-coding implementations, and system-design notes in one place.

Each problem is organized as a small, reviewable module with a problem statement and one or more implementations.

## What's Inside

| Area | Path | Contents |
| --- | --- | --- |
| Data Structures and Algorithms | `src/main/data_struct_algo` | Topic-based problem folders with `Problem_Statement.md` and solution files such as `Code.java`, `Code.cpp`, or `Code.js`. |
| Machine Coding | `src/main/machine_coding` | Object-oriented implementations for larger exercises such as an in-memory message broker and logger. |
| System Design | `src/main/system_design` | Problem statements and Excalidraw design artifacts for system-design practice. |
| Templates | `src/main/resources/template` | Starter folder structures for new DSA and system-design entries. |
| Tests | `src/test/machine_coding` | JUnit tests for machine-coding modules. |
| Tests | `src/test/data_struct_algo` | `CodeTest.java` (JUnit) and `Tests.cpp` (assert-based) tests for DSA solutions. |

## Repository Layout

```text
Quest/
├── README.md
├── CONTRIBUTING.md
├── LICENSE
├── pom.xml
├── Quest.iml
└── src/
    ├── main/
    │   ├── data_struct_algo/
    │   │   ├── array/
    │   │   ├── graph/
    │   │   ├── greedy/
    │   │   ├── hash_map/
    │   │   ├── heaps/
    │   │   ├── linked_list/
    │   │   ├── segment_tree/
    │   │   ├── stack/
    │   │   ├── tree/
    │   │   └── trie/
    │   ├── machine_coding/
    │   │   ├── logger/
    │   │   └── message_broker/
    │   ├── resources/template/
    │   └── system_design/
    │       ├── ticketmaster/
    │       └── uber/
    └── test/
        ├── data_struct_algo/
        └── machine_coding/
```

## Explore the Repository

### Data Structures and Algorithms

Browse by topic:

- [Array](src/main/data_struct_algo/array)
- [Graph](src/main/data_struct_algo/graph)
- [Greedy](src/main/data_struct_algo/greedy)
- [Hash Map](src/main/data_struct_algo/hash_map)
- [Heaps](src/main/data_struct_algo/heaps)
- [Linked List](src/main/data_struct_algo/linked_list)
- [Segment Tree](src/main/data_struct_algo/segment_tree)
- [Stack](src/main/data_struct_algo/stack)
- [Tree](src/main/data_struct_algo/tree)
- [Trie](src/main/data_struct_algo/trie)

Typical problem folder:

```text
src/main/data_struct_algo/<topic>/<problem_name>/
├── Problem_Statement.md
├── Code.java
├── Code.cpp
└── Code_Optimized.cpp
```

Not every problem has every language or an optimized variant.

### Machine Coding

- [Logger](src/main/machine_coding/logger)
- [Message Broker](src/main/machine_coding/message_broker)

Machine-coding modules usually include a problem statement, domain objects, services, config classes, a demo entry point, and tests when available.

### System Design

- [Ticketmaster](src/main/system_design/ticketmaster)
- [Uber](src/main/system_design/uber)

System-design folders contain notes and `.excalidraw` diagrams.

## Problem Status

Tracking every problem under `src/main/data_struct_algo`: **40 / 72 solved** (32 have a complete `Problem_Statement.md` with an implementation still pending — the `Code.*` file is an empty stub).

Each entry links to its `Problem_Statement.md`. Expand a topic to see its problems.

<details>
<summary><strong>Array</strong> — 10/10 solved</summary>

| Problem | Status |
| --- | --- |
| [Continuous Sum Query](src/main/data_struct_algo/array/continuous_sum_query/Problem_Statement.md) | ✅ Solved |
| [Max Abs Distance](src/main/data_struct_algo/array/max_abs_distance/Problem_Statement.md) | ✅ Solved |
| [Max Consecutive Gap](src/main/data_struct_algo/array/max_consecutive_gap/Problem_Statement.md) | ✅ Solved |
| [Max Sum Contiguous Subarray](src/main/data_struct_algo/array/max_sum_contiguous_subarray/Problem_Statement.md) | ✅ Solved |
| [Next Permutation](src/main/data_struct_algo/array/next_permutation/Problem_Statement.md) | ✅ Solved |
| [Rain Water Trapped](src/main/data_struct_algo/array/rain_water_trapped/Problem_Statement.md) | ✅ Solved |
| [Search Sorted Matrix](src/main/data_struct_algo/array/search_sorted_matrix/Problem_Statement.md) | ✅ Solved |
| [Set Matrix Zero](src/main/data_struct_algo/array/set_matrix_zero/Problem_Statement.md) | ✅ Solved |
| [Spiral Order Matrix 2](src/main/data_struct_algo/array/spiral_order_matrix_2/Problem_Statement.md) | ✅ Solved |
| [Sum All Submatrices](src/main/data_struct_algo/array/sum_all_submatrices/Problem_Statement.md) | ✅ Solved |

</details>

<details>
<summary><strong>Dynamic Programming</strong> — 1/15 solved</summary>

| Problem | Status |
| --- | --- |
| [Decode](src/main/data_struct_algo/dp/decode/Problem_Statement.md) | 🔲 Stub |
| [Distinct Subsequences](src/main/data_struct_algo/dp/distinct_subsequences/Problem_Statement.md) | 🔲 Stub |
| [Edit Distance](src/main/data_struct_algo/dp/edit_distance/Problem_Statement.md) | 🔲 Stub |
| [Longest Common Subsequence](src/main/data_struct_algo/dp/longest_common_subsequence/Problem_Statement.md) | 🔲 Stub |
| [Longest Common Substring](src/main/data_struct_algo/dp/longest_common_substring/Problem_Statement.md) | 🔲 Stub |
| [Longest Palindromic Subsequence](src/main/data_struct_algo/dp/longest_palindromic_subsequence/Problem_Statement.md) | 🔲 Stub |
| [Longest Palindromic Substring](src/main/data_struct_algo/dp/longest_palindromic_substring/Problem_Statement.md) | 🔲 Stub |
| [Max Product Subarray](src/main/data_struct_algo/dp/max_product_subarray/Problem_Statement.md) | 🔲 Stub |
| [Max Sum Non Adjacent Grid](src/main/data_struct_algo/dp/max_sum_non_adjacent_grid/Problem_Statement.md) | 🔲 Stub |
| [Max Triplet Weighted Sum](src/main/data_struct_algo/dp/max_triplet_weighted_sum/Problem_Statement.md) | 🔲 Stub |
| [Min Squares To Sum](src/main/data_struct_algo/dp/min_squares_to_sum/Problem_Statement.md) | ✅ Solved |
| [Odd Palindrome Subseq Center](src/main/data_struct_algo/dp/odd_palindrome_subseq_center/Problem_Statement.md) | 🔲 Stub |
| [Regex Matching](src/main/data_struct_algo/dp/regex_matching/Problem_Statement.md) | 🔲 Stub |
| [Ways To Party](src/main/data_struct_algo/dp/ways_to_party/Problem_Statement.md) | 🔲 Stub |
| [Wildcard Matching](src/main/data_struct_algo/dp/wildcard_matching/Problem_Statement.md) | 🔲 Stub |

</details>

<details>
<summary><strong>Graph</strong> — 12/21 solved</summary>

| Problem | Status |
| --- | --- |
| [Bipartite](src/main/data_struct_algo/graph/bipartite/Problem_Statement.md) | ✅ Solved |
| [Dijkstra's Algorithm](src/main/data_struct_algo/graph/dijikstras/Problem_Statement.md) | ✅ Solved |
| [Disjoint Set Union](src/main/data_struct_algo/graph/disjoint_set_union/Problem_Statement.md) | ✅ Solved |
| [Edge To Decrease Cost](src/main/data_struct_algo/graph/edge_to_decrease_cost/Problem_Statement.md) | 🔲 Stub |
| [Floyd Warshall](src/main/data_struct_algo/graph/floyd_warshall/Problem_Statement.md) | ✅ Solved |
| [Good Graph](src/main/data_struct_algo/graph/good_graph/Problem_Statement.md) | ✅ Solved |
| [Kruskal](src/main/data_struct_algo/graph/kruskal/Problem_Statement.md) | ✅ Solved |
| [Min Max Edge](src/main/data_struct_algo/graph/min_max_edge/Problem_Statement.md) | 🔲 Stub |
| [Min Weighted Cycle](src/main/data_struct_algo/graph/min_weighted_cycle/Problem_Statement.md) | ✅ Solved |
| [Multiple with 0/1 Edge Weights](src/main/data_struct_algo/graph/multiple_with_0_1/Problem_Statement.md) | 🔲 Stub |
| [Nearest Targets From Sources](src/main/data_struct_algo/graph/nearest_targets_from_sources/Problem_Statement.md) | ✅ Solved |
| [Number Of Regions](src/main/data_struct_algo/graph/number_of_regions/Problem_Statement.md) | 🔲 Stub |
| [Reverse Edge To Destination](src/main/data_struct_algo/graph/reverse_edge_to_destination/Problem_Statement.md) | 🔲 Stub |
| [Shortest Distance](src/main/data_struct_algo/graph/shortest_distance/Problem_Statement.md) | ✅ Solved |
| [Shortest Distance With Two Weights](src/main/data_struct_algo/graph/shortest_distance_with_two_weights/Problem_Statement.md) | 🔲 Stub |
| [Smallest Cost Path](src/main/data_struct_algo/graph/smallest_cost_path/Problem_Statement.md) | ✅ Solved |
| [Sort Nodes By Group Dependencies](src/main/data_struct_algo/graph/sort_nodes_by_group_dependencies/Problem_Statement.md) | 🔲 Stub |
| [Stones Removed](src/main/data_struct_algo/graph/stones_removed/Problem_Statement.md) | 🔲 Stub |
| [Topological Sort](src/main/data_struct_algo/graph/topological_sort/Problem_Statement.md) | ✅ Solved |
| [Traversal](src/main/data_struct_algo/graph/traversal/Problem_Statement.md) | ✅ Solved |
| [Tree Splitting](src/main/data_struct_algo/graph/tree_splitting/Problem_Statement.md) | 🔲 Stub |

</details>

<details>
<summary><strong>Greedy</strong> — 0/3 solved</summary>

| Problem | Status |
| --- | --- |
| [Busy Man](src/main/data_struct_algo/greedy/busy_man/Problem_Statement.md) | 🔲 Stub |
| [Maximize Job Profits](src/main/data_struct_algo/greedy/maximize_job_profits/Problem_Statement.md) | 🔲 Stub |
| [Minimum Jumps](src/main/data_struct_algo/greedy/minimum_jumps/Problem_Statement.md) | 🔲 Stub |

</details>

<details>
<summary><strong>Hash Map</strong> — 1/1 solved</summary>

| Problem | Status |
| --- | --- |
| [Scrambled Word From Array](src/main/data_struct_algo/hash_map/scrambled_word_from_array/Problem_Statement.md) | ✅ Solved |

</details>

<details>
<summary><strong>Heaps</strong> — 3/4 solved</summary>

| Problem | Status |
| --- | --- |
| [Implement Heap](src/main/data_struct_algo/heaps/implement_heap/Problem_Statement.md) | ✅ Solved |
| [Median Of Number Stream](src/main/data_struct_algo/heaps/median_of_number_stream/Problem_Statement.md) | 🔲 Stub |
| [Merge Sorted Arrays](src/main/data_struct_algo/heaps/merge_sorted_arrays/Problem_Statement.md) | ✅ Solved |
| [Min Refueling Stops](src/main/data_struct_algo/heaps/min_refueling_stops/Problem_Statement.md) | ✅ Solved |

</details>

<details>
<summary><strong>Linked List</strong> — 2/3 solved</summary>

| Problem | Status |
| --- | --- |
| [Flatten Multilevel Doubly Linked List](src/main/data_struct_algo/linked_list/flatten_multilevel_doubly_linked_list/Problem_Statement.md) | ✅ Solved |
| [Longest Palindromic Subsegment](src/main/data_struct_algo/linked_list/longest_palindromic_subsegment/Problem_Statement.md) | 🔲 Stub |
| [Reverse K Nodes Group](src/main/data_struct_algo/linked_list/reverse_k_nodes_group/Problem_Statement.md) | ✅ Solved |

</details>

<details>
<summary><strong>Segment Tree</strong> — 3/4 solved</summary>

| Problem | Status |
| --- | --- |
| [Bitmask](src/main/data_struct_algo/segment_tree/bitmask/Problem_Statement.md) | ✅ Solved |
| [Max Frequency From Range](src/main/data_struct_algo/segment_tree/max_frequency_from_range/Problem_Statement.md) | ✅ Solved |
| [Max Sum Pair From Range](src/main/data_struct_algo/segment_tree/max_sum_pair_from_range/Problem_Statement.md) | 🔲 Stub |
| [Sum Of Squares](src/main/data_struct_algo/segment_tree/sum_of_squares/Problem_Statement.md) | ✅ Solved |

</details>

<details>
<summary><strong>Stack</strong> — 2/3 solved</summary>

| Problem | Status |
| --- | --- |
| [Count Subarray First Element Minimum](src/main/data_struct_algo/stack/count_subarray_first_element_minimum/Problem_Statement.md) | ✅ Solved |
| [Longest Balanced Brackets](src/main/data_struct_algo/stack/longest_balanced_brackets/Problem_Statement.md) | 🔲 Stub |
| [Min Stack](src/main/data_struct_algo/stack/min_stack/Problem_Statement.md) | ✅ Solved |

</details>

<details>
<summary><strong>Tree</strong> — 4/5 solved</summary>

| Problem | Status |
| --- | --- |
| [Flatten Binary Tree To Doubly Linked List](src/main/data_struct_algo/tree/flatten_binary_tree_to_doubly_linked_list/Problem_Statement.md) | ✅ Solved |
| [Flatten Binary Tree To Linked List](src/main/data_struct_algo/tree/flatten_binary_tree_to_linked_list/Problem_Statement.md) | ✅ Solved |
| [Leaf Nodes BST Without Tree](src/main/data_struct_algo/tree/leaf_nodes_bst_without_tree/Problem_Statement.md) | ✅ Solved |
| [Max BST in Binary Tree](src/main/data_struct_algo/tree/max_bst_in_binary_tree/Problem_Statement.md) | 🔲 Stub |
| [Morris Traversal](src/main/data_struct_algo/tree/morris_traversal/Problem_Statement.md) | ✅ Solved |

</details>

<details>
<summary><strong>Trie</strong> — 2/3 solved</summary>

| Problem | Status |
| --- | --- |
| [Max XOR Subarray](src/main/data_struct_algo/trie/max_xor_subarray/Problem_Statement.md) | ✅ Solved |
| [Word Search In Grid](src/main/data_struct_algo/trie/word_search_in_grid/Problem_Statement.md) | 🔲 Stub |
| [XOR Pairs in Range](src/main/data_struct_algo/trie/xor_pairs_in_range/Problem_Statement.md) | ✅ Solved |

</details>

## Local Setup

Prerequisites:

- Java 21
- Maven 3.9 or newer
- IntelliJ IDEA or another editor that can work with custom Java source roots

This repository intentionally keeps Java sources under topic folders such as `src/main/data_struct_algo` and `src/main/machine_coding` instead of the default Maven `src/main/java` layout. The checked-in `Quest.iml` marks these source and test roots for IntelliJ, and `pom.xml` also registers them via the `build-helper-maven-plugin`, so `mvn compile` and `mvn test` work from the CLI as well.

Useful checks:

```bash
java --version
mvn --version
```

For Java machine-coding work, open the project in IntelliJ and run the relevant demo or JUnit test from the module source roots. The Maven `pom.xml` declares Java 21, Lombok, logging, Jackson, JUnit, AssertJ, and Mockito dependencies, and registers the custom source roots via the `build-helper-maven-plugin`, so `mvn compile` and `mvn test` work from the CLI even though the source layout is not the standard Maven layout.

## How to Use

1. Pick an area under `src/main/data_struct_algo`, `src/main/machine_coding`, or `src/main/system_design`.
2. Read the module's `Problem_Statement.md`.
3. Review the implementation files in the same folder.
4. Run the relevant solution, demo, or test from your IDE.
5. Compare implementations where multiple languages or optimized solutions are present.

## Contributing

Contributions are welcome when they keep the repository easy to navigate and review. Before opening a pull request, read [CONTRIBUTING.md](CONTRIBUTING.md) for naming conventions, folder structure, documentation expectations, and validation guidance.

## License

This project is licensed under the MIT License. See [LICENSE](LICENSE).
