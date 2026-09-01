---
description: Repository layout and contribution conventions for Quest
alwaysApply: true
---

# Quest Conventions

Quest collects interview-prep material in small, reviewable modules. Read [CONTRIBUTING.md](../../CONTRIBUTING.md) before adding or restructuring content.

## Folder Patterns

```text
src/main/data_struct_algo/<topic>/<problem_name>/
src/main/machine_coding/<module_name>/
src/main/system_design/<system_name>/
src/test/machine_coding/<module_name>/
src/test/data_struct_algo/<topic>/<problem_name>/
```

Use lowercase `snake_case` for topic, problem, and module folder names.

## Required Files

- Every new DSA, machine-coding, or system-design entry needs `Problem_Statement.md`.
- Every new DSA Java solution (`Code.java`) needs a matching `CodeTest.java` under `src/test/data_struct_algo/<topic>/<problem_name>/`, in the same package, with one `@Test` per worked example in `Problem_Statement.md`.
- Every new DSA C++ solution (`Code.cpp`) needs a matching `Tests.cpp` colocated in the same folder, `#include`-ing `Code.cpp` and asserting the worked examples in `Problem_Statement.md`. Compile and run it directly with a real GCC, e.g. `g++-16 -std=c++17 Tests.cpp -o /tmp/test && /tmp/test` — no repo-wide C++ build file. (On macOS, plain `g++` is usually an Apple Clang shim with no GNU `libstdc++`, so it cannot compile `#include <bits/stdc++.h>` — install real GCC with `brew install gcc` if `g++-<N>` isn't already on your PATH, and use that binary name instead of plain `g++`.)
- Machine-coding modules should include a demo or tests when the behavior is runnable.
- System-design exercises should include `design.excalidraw` when a diagram helps.

## Agent Expectations

- Keep changes focused on one problem, module, or documentation improvement.
- Avoid broad formatting churn in unrelated files.
- Match the language, naming, and structure of neighboring solutions in the same topic folder.
- Copy starter layouts from `src/main/resources/template/` when creating new entries.
