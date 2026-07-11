---
description: Build and validation guidance for Quest
alwaysApply: true
---

# Quest Build Rules

Quest is a Java 21 Maven project with custom source roots, plus standalone C++ solutions and Excalidraw design artifacts.

## Source Layout

Java sources live under topic folders such as `src/main/data_struct_algo` and `src/main/machine_coding`, not the default Maven `src/main/java` layout. IntelliJ `Quest.iml` registers the current source and test roots.

Machine-coding tests live under `src/test/machine_coding/<module_name>/`.

## Defaults

- Use `mvn -q -DskipTests compile` for a quick dependency and compiler sanity check from the repo root.
- Use `mvn test` when tests are explicitly requested.
- Prefer running the relevant demo class or JUnit test from IntelliJ for machine-coding modules.
- Compile and run C++ solutions with the toolchain available locally; there is no repo-wide C++ build file.
- Manually validate DSA solutions against the examples in each `Problem_Statement.md`.

## Validation Notes

- Do not assume Maven discovers every Java source file because of the custom layout.
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
