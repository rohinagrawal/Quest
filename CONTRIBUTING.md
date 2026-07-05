# Contributing to Quest

Thanks for helping improve Quest. This repository is easiest to maintain when every contribution is small, well-named, and includes enough context for someone else to learn from it later.

## Contribution Types

Good contributions include:

- A new DSA problem with a clear statement and at least one working solution.
- An additional implementation language or optimized solution for an existing problem.
- A machine-coding module with a problem statement, clean object model, demo, and tests.
- A system-design exercise with notes and an Excalidraw diagram.
- Fixes to incorrect solutions, unclear problem statements, stale docs, or broken tests.

## Repository Conventions

Use lowercase `snake_case` for topic and problem folders.

```text
src/main/data_struct_algo/<topic>/<problem_name>/
src/main/machine_coding/<module_name>/
src/main/system_design/<system_name>/
```

Keep generated files, compiled binaries, IDE caches, and local scratch files out of commits.

## Adding a DSA Problem

1. Create a folder under the relevant topic:

   ```text
   src/main/data_struct_algo/<topic>/<problem_name>/
   ```

2. Add `Problem_Statement.md` with:

   - Problem description
   - Input and output format
   - Constraints
   - Examples
   - Explanation of edge cases, if useful

3. Add one or more implementations:

   - `Code.java`
   - `Code.cpp`
   - `Code.js`
   - `Code_Optimized.<ext>` when adding a materially different optimized approach

4. Keep each solution self-contained enough that a reader can understand the approach from the file and problem statement.

5. If the problem needs special execution steps, add them to the problem statement.

## Adding a Machine-Coding Module

Create the module under:

```text
src/main/machine_coding/<module_name>/
```

Include:

- `Problem_Statement.md` describing requirements, assumptions, APIs, and edge cases.
- A demo class when the module is runnable from the command line or IDE.
- Focused packages for domain objects, services, config, exceptions, and utilities where they help readability.
- JUnit tests under `src/test/machine_coding/<module_name>/` for core behavior.

Machine-coding submissions should favor simple, explicit object-oriented design over clever abstractions.

## Adding a System-Design Exercise

Create the exercise under:

```text
src/main/system_design/<system_name>/
```

Include:

- `Problem_Statement.md` with scope, requirements, constraints, and assumptions.
- `design.excalidraw` for architecture diagrams or flows.
- Notes about tradeoffs when the design makes an intentional choice.

## Coding Standards

- Use Java 21 features only when they make the code clearer.
- Keep Java package names aligned with the configured source roots.
- Prefer readable names over abbreviations.
- Handle edge cases called out in the problem statement.
- Avoid broad rewrites or formatting churn in files unrelated to your change.
- Add short comments only where the reasoning is not obvious from the code.

## Testing and Validation

Before opening a pull request:

- Run the relevant tests from IntelliJ or your configured Java runner.
- Add or update JUnit tests when changing machine-coding behavior.
- Manually validate standalone DSA solutions with the examples from the problem statement.
- Confirm Markdown renders cleanly for any new or changed documentation.

The project currently uses custom source roots registered in `Quest.iml`, not the default Maven `src/main/java` and `src/test/java` layout. If you change the build layout, update the docs and project configuration in the same pull request.

## Pull Request Checklist

Before submitting, verify that:

- The folder and file names follow the repository conventions.
- Every new problem has a `Problem_Statement.md`.
- Every implementation compiles or runs in its intended environment.
- New machine-coding behavior has meaningful tests.
- The pull request is focused on one problem, module, or documentation improvement.
- The description explains what changed and how it was validated.

## Review Process

Maintainers may request changes for correctness, readability, missing edge cases, missing tests, or unclear documentation. Keep follow-up commits focused so reviewers can quickly verify the update.
