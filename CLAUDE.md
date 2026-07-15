# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project

Quest is a Java 21 practice repository for interview preparation. It collects data structures and algorithms problems, machine-coding modules, and system-design exercises under custom source roots in `src/main/` (not the default Maven `src/main/java` layout — see `Quest.iml`).

Shared agent assets live under `.agents/`.

Use `.agents/README.md` as the source of truth for project context, folder conventions, shared rules, skills, manifest, and validation commands.

Before working in this repo, read every rule file under `.agents/rules/*` — treat `alwaysApply: true` rules (`quest-conventions.md`, `maven-build.md`) as baseline instructions for every task.
