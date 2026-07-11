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
```

Use lowercase `snake_case` for topic, problem, and module folder names.

## Required Files

- Every new DSA, machine-coding, or system-design entry needs `Problem_Statement.md`.
- Machine-coding modules should include a demo or tests when the behavior is runnable.
- System-design exercises should include `design.excalidraw` when a diagram helps.

## Agent Expectations

- Keep changes focused on one problem, module, or documentation improvement.
- Avoid broad formatting churn in unrelated files.
- Match the language, naming, and structure of neighboring solutions in the same topic folder.
- Copy starter layouts from `src/main/resources/template/` when creating new entries.
