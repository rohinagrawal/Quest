# DSA Test Scaffold Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Give every solved `src/main/data_struct_algo/<topic>/<problem_name>/` entry an automated test — JUnit 5 for Java solutions, an assert-based `Tests.cpp` for C++ solutions — runnable from the command line, not just "validated manually against the Problem_Statement.md examples."

**Architecture:** Two parallel, language-appropriate mechanisms sharing one folder convention:
- Java: `CodeTest.java` under a new `src/test/data_struct_algo/<topic>/<problem_name>/` root, run via `mvn test` (JUnit 5, already a dependency).
- C++: a sibling `Tests.cpp` colocated with `Code.cpp` in the same `src/main/data_struct_algo/<topic>/<problem_name>/` folder (since C++ has no Maven/IDE test-root concept here), compiled and run per-file with `g++` — no repo-wide C++ build file is introduced, matching the existing convention in `.agents/rules/maven-build.md`.

Both mechanisms only exercise the public methods already documented with worked examples in each problem's `Problem_Statement.md` — these are **characterization/regression tests for already-implemented solutions**, not red/green TDD for new behavior. There is no "write minimal implementation" step; the implementation already exists and was previously validated by hand.

**Tech Stack:** Java 21, JUnit 5.11.4 (`org.junit.jupiter`), Maven (`build-helper-maven-plugin` newly added), C++17, `g++`, `assert`/`iostream`.

**Spec:** This plan's own Architecture section above — there is no separate spec doc; the design was worked out interactively with the user in-session (see conversation).

## Global Constraints

- Folder/file naming stays `snake_case` for directories, matching `.agents/rules/quest-conventions.md`.
- Java test class per problem is named `CodeTest.java`, in the same package as `Code.java`, colocated at `src/test/data_struct_algo/<topic>/<problem_name>/CodeTest.java`.
- C++ test file per problem is named `Tests.cpp`, colocated at `src/main/data_struct_algo/<topic>/<problem_name>/Tests.cpp`, and does `#include "Code.cpp"` — never compiled as a separate translation unit from `Code.cpp`.
- No repo-wide C++ build file (CMake, Makefile, etc.) gets introduced — stays consistent with the existing rule in `.agents/rules/maven-build.md`.
- Any build-layout change (pom.xml, `Quest.iml`) must be accompanied by updates to `Quest.iml`, `.agents/rules/maven-build.md`, `.agents/rules/quest-conventions.md`, and `CONTRIBUTING.md` in the same task — this is an existing repo rule, not new.
- Only write tests for solutions that are actually implemented. Do not write tests for stub/unsolved `Code.cpp` files (empty `class Code { public: };` bodies or methods with empty bodies) — Task 5 lists which problems are stubs and must be skipped.

---

## Background: why Task 1 is needed

`pom.xml` has no `sourceDirectory`/`testSourceDirectory` override and no `build-helper-maven-plugin`. Maven's effective source dirs are the *default* `src/main/java` and `src/test/java`, neither of which exists in this repo. Verified directly:

```bash
mvn -q clean compile   # produces zero .class files — silently compiles nothing
mvn -q test             # exits 0, runs zero tests — the 3 existing machine_coding tests never execute
```

This matches the existing warning in `.agents/rules/maven-build.md` ("Do not assume Maven discovers every Java source file") and explains why that doc says to run tests from IntelliJ. IntelliJ works because `Quest.iml` declares the real source roots directly — Maven CLI does not read `Quest.iml`.

Without fixing this, any new `CodeTest.java` we write would exist but never run via `mvn test`, and neither I nor CI could ever verify it — so this is a prerequisite for the rest of the plan, not optional scope creep.

---

### Task 1: Wire up Maven to discover the custom Java source and test roots

**Files:**
- Modify: `pom.xml`
- Modify: `Quest.iml`
- Modify: `.agents/rules/maven-build.md`

**Interfaces:**
- Produces: a working `mvn -q -DskipTests compile` (compiles all of `src/main/data_struct_algo`, `src/main/machine_coding`, `src/main/system_design`) and `mvn test` (runs everything under `src/test/machine_coding` and, after Task 2, `src/test/data_struct_algo`) from the CLI, with no other task depending on new Java/Maven behavior beyond this.

- [ ] **Step 1: Add `build-helper-maven-plugin` to `pom.xml`**

Add the version property next to the other plugin versions:

```xml
<maven.compiler.plugin.version>3.13.0</maven.compiler.plugin.version>
<maven.surefire.plugin.version>3.5.2</maven.surefire.plugin.version>
<build.helper.plugin.version>3.6.0</build.helper.plugin.version>
```

Add the plugin inside `<build><plugins>`, after the `maven-surefire-plugin` block:

```xml
<plugin>
    <groupId>org.codehaus.mojo</groupId>
    <artifactId>build-helper-maven-plugin</artifactId>
    <version>${build.helper.plugin.version}</version>
    <executions>
        <execution>
            <id>add-source</id>
            <phase>generate-sources</phase>
            <goals>
                <goal>add-source</goal>
            </goals>
            <configuration>
                <sources>
                    <source>src/main/data_struct_algo</source>
                    <source>src/main/machine_coding</source>
                    <source>src/main/system_design</source>
                </sources>
            </configuration>
        </execution>
        <execution>
            <id>add-test-source</id>
            <phase>generate-test-sources</phase>
            <goals>
                <goal>add-test-source</goal>
            </goals>
            <configuration>
                <sources>
                    <source>src/test/machine_coding</source>
                    <source>src/test/data_struct_algo</source>
                </sources>
            </configuration>
        </execution>
    </executions>
</plugin>
```

- [ ] **Step 2: Create the (currently empty) new test root so Maven/IDE tooling has something to point at**

```bash
mkdir -p src/test/data_struct_algo
```

(Task 2 populates this; an empty directory is fine as a placeholder for `add-test-source` — Maven does not error on an empty extra source root.)

- [ ] **Step 3: Register the new test root in `Quest.iml`**

In the `<content>` block, add a new `<sourceFolder>` line for `src/test/data_struct_algo`, next to the existing `src/test/machine_coding` line (there isn't one yet — add both the root's `isTestSource` folder entries so the two test roots read consistently):

```xml
<sourceFolder url="file://$MODULE_DIR$/src/test/machine_coding" isTestSource="true" />
<sourceFolder url="file://$MODULE_DIR$/src/test/data_struct_algo" isTestSource="true" />
```

(Check the existing file first — `src/test/machine_coding` may already be listed; only add the `data_struct_algo` line if so, add both if neither exists yet.)

- [ ] **Step 4: Verify full compile and test run from the CLI**

```bash
mvn -q clean compile
find target/classes -iname "*.class" | wc -l   # expect 79+ (was 0 before this task)
mvn -q test
find target/test-classes -iname "*.class" | wc -l   # expect 3 (the existing machine_coding tests)
```

If `mvn compile` surfaces genuine compile errors in existing files (not caused by this change, just newly visible because nothing compiled before), fix trivial ones inline (typos, missing imports) and report anything non-trivial rather than guessing at intent.

- [ ] **Step 5: Update `.agents/rules/maven-build.md`**

Replace the line `Do not assume Maven discovers every Java source file because of the custom layout.` with:

```markdown
`mvn -q -DskipTests compile` and `mvn test` now discover every custom source root via the `build-helper-maven-plugin` declared in `pom.xml` — both commands work from the CLI, not just from IntelliJ.
```

Add a note under "Useful Commands" about the repeated `CodeTest`/`Code` class names across DSA packages:

```markdown
- Because every DSA problem package defines a class literally named `Code` (and, once tests exist, `CodeTest`), `-Dtest=CodeTest` matches every DSA test in the repo. Use the fully-qualified name to run one, e.g. `mvn -q -Dtest=array.max_abs_distance.CodeTest test`.
```

- [ ] **Step 6: Commit**

```bash
git add pom.xml Quest.iml .agents/rules/maven-build.md
git commit -m "build: wire up custom source roots for mvn compile/test"
```

---

### Task 2: Java test scaffold + one worked example (`array/max_abs_distance`)

**Files:**
- Create: `src/test/data_struct_algo/array/max_abs_distance/CodeTest.java`

**Interfaces:**
- Consumes: `array.max_abs_distance.Code#maxArr(int[])` from `src/main/data_struct_algo/array/max_abs_distance/Code.java` (already implemented — see file for the returned type/signature, unchanged by this task).
- Produces: the reference shape every future Java `CodeTest.java` in Task 5's backfill copies — package matches `Code`'s package, one `@Test` method per worked example in `Problem_Statement.md`, `assertEquals(expected, code.method(input))`.

- [ ] **Step 1: Write the test, from the two worked examples already in `array/max_abs_distance/Problem_Statement.md`**

```java
package array.max_abs_distance;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CodeTest {

    private final Code code = new Code();

    @Test
    void returnsFiveForMixedSignArray() {
        assertEquals(5, code.maxArr(new int[]{1, 3, -1}));
    }

    @Test
    void returnsZeroForSingleElementArray() {
        assertEquals(0, code.maxArr(new int[]{2}));
    }
}
```

- [ ] **Step 2: Run it and confirm it passes**

```bash
mvn -q -Dtest=array.max_abs_distance.CodeTest test
```

Expected: `BUILD SUCCESS`, 2 tests run, 0 failures. (If it fails, the failure means `Code.maxArr` doesn't actually satisfy its own documented examples — stop and report rather than editing the test to match wrong output.)

- [ ] **Step 3: Commit**

```bash
git add src/test/data_struct_algo/array/max_abs_distance/CodeTest.java
git commit -m "test: add JUnit test scaffold example for max_abs_distance"
```

---

### Task 3: C++ test scaffold + one worked example (`stack/min_stack`)

**Files:**
- Create: `src/main/data_struct_algo/stack/min_stack/Tests.cpp`

**Interfaces:**
- Consumes: `Code` class from `src/main/data_struct_algo/stack/min_stack/Code.cpp` (`push(int)`, `pop()`, `top()`, `getMin()` — already implemented, unchanged by this task), included directly via `#include "Code.cpp"`.
- Produces: the reference shape every future `Tests.cpp` in Task 5's backfill copies — `#include "Code.cpp"`, `assert()` per worked example, a final success message, `return 0`.

- [ ] **Step 1: Write the test, from the worked example already in `stack/min_stack/Problem_Statement.md`**

```cpp
#include "Code.cpp"
#include <cassert>
#include <iostream>

int main() {
    Code minStack;
    minStack.push(-2);
    minStack.push(0);
    minStack.push(-3);
    assert(minStack.getMin() == -3);
    minStack.pop();
    assert(minStack.top() == 0);
    assert(minStack.getMin() == -2);

    std::cout << "All tests passed." << std::endl;
    return 0;
}
```

- [ ] **Step 2: Compile and run it, confirm it passes**

```bash
cd src/main/data_struct_algo/stack/min_stack
g++ -std=c++17 -Wall Tests.cpp -o /tmp/min_stack_tests && /tmp/min_stack_tests
cd -
```

Expected output: `All tests passed.` and exit code 0. An `assert()` failure aborts with a nonzero exit and a line number — if that happens, it means `Code`'s implementation doesn't satisfy its own documented example; stop and report rather than editing the test to match wrong output.

- [ ] **Step 3: Commit**

```bash
git add src/main/data_struct_algo/stack/min_stack/Tests.cpp
git commit -m "test: add C++ test scaffold example for min_stack"
```

---

### Task 4: Lock in the convention — templates, `.agents/rules`, `CONTRIBUTING.md`

**Files:**
- Modify: `.agents/rules/quest-conventions.md`
- Modify: `CONTRIBUTING.md`
- Modify: `src/main/resources/template/dsa_topic_name/question_name/Code.java` directory (add sibling template file)
- Modify: `src/main/resources/template/dsa_topic_name/question_name/Code.cpp` directory (add sibling template file)
- Modify: `src/main/resources/template/dsa_topic_name/question_name_2/Code.cpp` directory (add sibling template file)

**Interfaces:**
- Produces: the documented convention that Task 5 (and all future DSA problems) follow — no new code interfaces, this is documentation + starter templates.

- [ ] **Step 1: Add `CodeTest.java` to the Java template**

Create `src/main/resources/template/dsa_topic_name/question_name/CodeTest.java`:

```java
package topic_name.question_name;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CodeTest {

    private final Code code = new Code();

    @Test
    void describesExpectedBehavior() {
        // assertEquals(expected, code.methodName(input));
    }
}
```

(This template's target location when copied for a real problem is `src/test/data_struct_algo/<topic>/<problem_name>/CodeTest.java`, not next to `Code.java` — call this out in Step 3's doc update, since the template folder itself doesn't mirror the `src/main` vs `src/test` split.)

- [ ] **Step 2: Add `Tests.cpp` to both C++ templates**

Create `src/main/resources/template/dsa_topic_name/question_name/Tests.cpp` and `src/main/resources/template/dsa_topic_name/question_name_2/Tests.cpp`, identical content:

```cpp
#include "Code.cpp"
#include <cassert>
#include <iostream>

int main() {
    // Code obj;
    // assert(obj.methodName(input) == expected);

    std::cout << "All tests passed." << std::endl;
    return 0;
}
```

- [ ] **Step 3: Update `.agents/rules/quest-conventions.md`**

Replace the `## Folder Patterns` code block with:

```text
src/main/data_struct_algo/<topic>/<problem_name>/
src/main/machine_coding/<module_name>/
src/main/system_design/<system_name>/
src/test/machine_coding/<module_name>/
src/test/data_struct_algo/<topic>/<problem_name>/
```

Replace the `## Required Files` bullet `Every new DSA, machine-coding, or system-design entry needs Problem_Statement.md.` with two bullets:

```markdown
- Every new DSA, machine-coding, or system-design entry needs `Problem_Statement.md`.
- Every new DSA Java solution (`Code.java`) needs a matching `CodeTest.java` under `src/test/data_struct_algo/<topic>/<problem_name>/`, in the same package, with one `@Test` per worked example in `Problem_Statement.md`.
- Every new DSA C++ solution (`Code.cpp`) needs a matching `Tests.cpp` colocated in the same folder, `#include`-ing `Code.cpp` and asserting the worked examples in `Problem_Statement.md`. Compile and run it directly with `g++`, e.g. `g++ -std=c++17 Tests.cpp -o /tmp/test && /tmp/test` — no repo-wide C++ build file.
```

- [ ] **Step 4: Update `CONTRIBUTING.md`**

In `## Adding a DSA Problem`, replace step 3's bullet list with:

```markdown
3. Add one or more implementations, each with a matching test:

   - `Code.java` + `src/test/data_struct_algo/<topic>/<problem_name>/CodeTest.java` (JUnit 5)
   - `Code.cpp` + a colocated `Tests.cpp` (assert-based, run with `g++`)
   - `Code.js`
   - `Code_Optimized.<ext>` when adding a materially different optimized approach
```

In `## Testing and Validation`, replace `Manually validate standalone DSA solutions with the examples from the problem statement.` with:

```markdown
- Run `mvn -q -Dtest=<package>.CodeTest test` for Java DSA solutions, and `g++ -std=c++17 Tests.cpp -o /tmp/test && /tmp/test` (from the problem's folder) for C++ ones — both should pass using the examples from `Problem_Statement.md`.
```

- [ ] **Step 5: Commit**

```bash
git add .agents/rules/quest-conventions.md CONTRIBUTING.md \
  src/main/resources/template/dsa_topic_name/question_name/CodeTest.java \
  src/main/resources/template/dsa_topic_name/question_name/Tests.cpp \
  src/main/resources/template/dsa_topic_name/question_name_2/Tests.cpp
git commit -m "docs: document CodeTest.java/Tests.cpp convention for DSA problems"
```

---

### Task 5: Backfill tests for every already-solved DSA problem

**Files:**
- Create: one `src/test/data_struct_algo/<topic>/<problem_name>/CodeTest.java` per Java problem listed below.
- Create: one `src/main/data_struct_algo/<topic>/<problem_name>/Tests.cpp` per C++ problem listed below.

**Interfaces:**
- Consumes: Task 2's `CodeTest.java` shape and Task 3's `Tests.cpp` shape exactly (same package-per-file, same `assert`/`assertEquals`-per-worked-example pattern).
- Produces: nothing consumed by a later task — this is the terminal fan-out task.

**Procedure (apply identically to every problem below):**

1. Read `<problem_dir>/Problem_Statement.md`'s `## Examples` section and `<problem_dir>/Code.{java,cpp}`.
2. For a stateless method (`Code.methodName(args) -> result`): one `@Test`/`assert` per worked example, named/commented after what the example demonstrates (not `test1`, `test2`).
3. For a stateful class (methods called in sequence, like `min_stack`): one `@Test`/`main()` that replays the example's call sequence and asserts each observable return value, exactly like Task 3's `min_stack` example.
4. Java file goes at `src/test/data_struct_algo/<topic>/<problem_name>/CodeTest.java`, same package as `Code.java`.
5. C++ file goes at `src/main/data_struct_algo/<topic>/<problem_name>/Tests.cpp`, `#include "Code.cpp"`.
6. Run it:
   - Java: `mvn -q -Dtest=<package>.CodeTest test`
   - C++: `(cd <problem_dir> && g++ -std=c++17 -Wall Tests.cpp -o /tmp/t && /tmp/t)`
7. If it fails, the solution doesn't satisfy its own documented example — stop on that problem, report it, do not edit the test to match wrong output, and move to the next problem in the list.
8. Commit each problem's test file individually (small, reviewable commits, per repo convention): `git add <file> && git commit -m "test: add CodeTest for <topic>/<problem_name>"` (or `Tests.cpp` for C++).

**Java problems to backfill (10 — all in `src/main/data_struct_algo/`):**

```text
hash_map/scrambled_word_from_array
array/search_sorted_matrix
array/rain_water_trapped
array/set_matrix_zero
array/max_sum_contiguous_subarray
array/max_consecutive_gap
array/next_permutation
array/continuous_sum_query
array/spiral_order_matrix_2
array/sum_all_submatrices
```

**C++ problems to backfill (28 — all in `src/main/data_struct_algo/`):**

```text
dp/min_squares_to_sum
graph/bipartite
graph/disjoint_set_union
graph/floyd_warshall
graph/good_graph
graph/kruskal
graph/min_weighted_cycle
graph/nearest_targets_from_sources
graph/shortest_distance
graph/smallest_cost_path
graph/topological_sort
graph/traversal
heaps/implement_heap
heaps/merge_sorted_arrays
heaps/min_refueling_stops
linked_list/flatten_multilevel_doubly_linked_list
linked_list/reverse_k_nodes_group
segment_tree/bitmask
segment_tree/max_frequency_from_range
segment_tree/sum_of_squares
stack/count_subarray_first_element_minimum
tree/flatten_binary_tree_to_doubly_linked_list
tree/flatten_binary_tree_to_linked_list
tree/leaf_nodes_bst_without_tree
tree/morris_traversal
trie/max_xor_subarray
trie/xor_pairs_in_range
graph/dijikstras   # PARTIAL: only test dijikstras_heap(); dijikstras_set() has an empty body — skip it, do not stub an assertion for it
```

**Explicitly SKIP — these `Code.cpp` files are unsolved stubs (empty `class Code { public: };` or empty method bodies), confirmed by direct inspection on 2026-09-01. Do not write tests for them; leave them for whoever finishes the solution:**

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

(33 stub problems total. Combined with the 2 already done as worked examples in Tasks 2–3, this accounts for all 73 currently-solved-or-stubbed DSA problems: 11 Java + 62 C++ = 73.)

- [ ] **Step 1: Backfill all 10 Java problems**, following the Procedure above, one commit per problem.

- [ ] **Step 2: Backfill all 28 C++ problems** (including the partial `graph/dijikstras`), following the Procedure above, one commit per problem.

- [ ] **Step 3: Final verification — run everything at once**

```bash
mvn -q test
echo "Java: expect $((10+1+3)) tests run across 11 CodeTest classes, 0 failures"   # 10 backfilled + 2 in max_abs_distance, plus the 3 pre-existing machine_coding tests
for d in $(find src/main/data_struct_algo -name Tests.cpp); do
  dir=$(dirname "$d")
  (cd "$dir" && g++ -std=c++17 -Wall Tests.cpp -o /tmp/t && /tmp/t) || echo "FAILED: $dir"
done
```

Expected: no `FAILED:` lines, `mvn -q test` exits 0.

---

## Self-Review

**Spec coverage:** Java scaffold (Task 2), C++ scaffold (Task 3), Maven CLI actually running tests (Task 1, discovered as a hard blocker — not in the original ask but required for Task 2/5 to be verifiable), convention locked into docs/templates (Task 4), full backfill sized accurately by first checking which C++ "solutions" are real vs. stubs (Task 5) — all covered.

**Placeholder scan:** No TBD/TODO in task steps. Task 5 is a fan-out over a concrete, enumerated list rather than 38 fully-spelled-out individual code blocks — each item's transformation is fully specified by the Procedure plus Task 2/3's worked examples, so nothing is deferred to guesswork.

**Type consistency:** `Code` / `CodeTest` naming, `assertEquals`/`assert` usage, and file locations are identical across Tasks 2, 3, and 5.
