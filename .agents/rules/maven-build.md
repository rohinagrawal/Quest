---
description: Build and validation guidance for Quest
alwaysApply: true
---

# Quest Build Rules

Quest is a Java 21 Maven project with custom source roots, plus standalone C++ solutions and Excalidraw design artifacts.

## Source Layout

Java sources live under topic folders such as `src/main/data_struct_algo` and `src/main/machine_coding`, not the default Maven `src/main/java` layout. IntelliJ `Quest.iml` registers the current source and test roots.

Machine-coding tests live under `src/test/machine_coding/<module_name>/`, and DSA tests live under `src/test/data_struct_algo/<topic>/<problem_name>/`.

## Defaults

- Use `mvn -q -DskipTests compile` for a quick dependency and compiler sanity check from the repo root.
- Use `mvn test` when tests are explicitly requested.
- Prefer running the relevant demo class or JUnit test from IntelliJ for machine-coding modules.
- Compile and run C++ solutions with a real GCC, e.g. `g++-16` on macOS via `brew install gcc` (plain `g++` there is usually an Apple Clang shim with no GNU `libstdc++`, so it cannot compile `#include <bits/stdc++.h>`); there is no repo-wide C++ build file.
- Run `mvn -q -Dtest=<package>.CodeTest test` for Java DSA solutions, and `g++-16 -std=c++17 Tests.cpp -o /tmp/test && /tmp/test` for C++ ones, to validate against the examples in each `Problem_Statement.md`.

## Validation Notes

- `mvn -q -DskipTests compile` and `mvn test` now discover every custom source root via the `build-helper-maven-plugin` declared in `pom.xml` — both commands work from the CLI, not just from IntelliJ.
- Do not add build workarounds until the simplest root-level command for the task has been tried.
- If you change the build layout, update `Quest.iml`, docs, and `.agents/rules/` in the same change.

## Useful Commands

```bash
mvn -q -DskipTests compile
mvn test
java --version
mvn --version
python3 .agents/scripts/validate-agent-assets.py --mode adapters
```

- Because every DSA problem package defines a class literally named `Code` (and, once tests exist, `CodeTest`), `-Dtest=CodeTest` matches every DSA test in the repo. Use the fully-qualified name to run one, e.g. `mvn -q -Dtest=array.max_abs_distance.CodeTest test`.
