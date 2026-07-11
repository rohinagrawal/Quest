#!/usr/bin/env python3
"""Validate Quest Problem_Statement.md structure after refinement."""

from __future__ import annotations

import argparse
import re
import sys
from pathlib import Path


MACHINE_CODING_MARKERS = (
    "functional requirements",
    "problem statement",
    "deliverables",
)

SYSTEM_DESIGN_MARKERS = (
    "functional requirements",
    "non-functional requirements",
    "out of scope",
)


class Reporter:
    def __init__(self) -> None:
        self.failures: list[str] = []
        self.warnings: list[str] = []
        self.passes: list[str] = []

    def ok(self, message: str) -> None:
        self.passes.append(message)

    def warn(self, message: str) -> None:
        self.warnings.append(message)

    def fail(self, message: str) -> None:
        self.failures.append(message)

    def print(self) -> None:
        for message in self.passes:
            print(f"OK   {message}")
        for message in self.warnings:
            print(f"WARN {message}")
        for message in self.failures:
            print(f"FAIL {message}")


def infer_kind(path: Path, headings: set[str]) -> str:
    path_str = str(path.parent).lower()
    if "machine_coding" in path_str:
        return "machine_coding"
    if "system_design" in path_str:
        return "system_design"
    if any(m in headings for m in MACHINE_CODING_MARKERS):
        return "machine_coding"
    if any(m in headings for m in SYSTEM_DESIGN_MARKERS):
        return "system_design"
    return "dsa"


def heading_text(line: str) -> str | None:
    match = re.match(r"^#{1,6}\s+(.+?)\s*$", line)
    return match.group(1).strip().lower() if match else None


def legacy_heading_text(line: str) -> str | None:
    stripped = line.strip()
    if not stripped or stripped.startswith("#"):
        return None
    if re.match(r"^[-=]{3,}$", stripped):
        return None
    if len(stripped) > 80:
        return None
    return stripped.lower()


def collect_headings(lines: list[str]) -> set[str]:
    headings: set[str] = set()
    for index, line in enumerate(lines):
        h = heading_text(line)
        if h:
            headings.add(h)
            continue
        legacy = legacy_heading_text(line)
        if legacy and index + 1 < len(lines) and re.match(r"^[-=]{3,}$", lines[index + 1].strip()):
            headings.add(legacy)
    legacy_flat = {
        "problem description",
        "problem constraints",
        "input format",
        "output format",
        "example input",
        "example output",
        "example explanation",
    }
    lowered_blob = "\n".join(line.strip().lower() for line in lines if line.strip())
    for marker in legacy_flat:
        if marker in lowered_blob:
            headings.add(marker)
    return headings


def has_title(lines: list[str]) -> bool:
    for line in lines[:10]:
        if line.startswith("# ") and len(line.strip()) > 2:
            return True
    return False


def count_examples(text: str) -> int:
    return len(re.findall(r"^###\s+Example\b", text, flags=re.MULTILINE | re.IGNORECASE))


def has_labeled_example_parts(text: str) -> bool:
    lowered = text.lower()
    return "**input:**" in lowered and "**output:**" in lowered


def broken_image_refs(text: str, folder: Path) -> list[str]:
    broken: list[str] = []
    for match in re.finditer(r"!\[[^\]]*\]\(([^)]+)\)", text):
        target = match.group(1).split("#", 1)[0].strip()
        if target.startswith(("http://", "https://")):
            continue
        candidate = (folder / target).resolve()
        if not candidate.is_file():
            broken.append(target)
    return broken


def excessive_blank_lines(text: str) -> bool:
    return bool(re.search(r"\n{4,}", text))


def stub_like(text: str) -> bool:
    non_empty = [line for line in text.splitlines() if line.strip()]
    if len(non_empty) <= 2:
        return True
    words = len(re.findall(r"\w+", text))
    return words < 25


def validate(path: Path, reporter: Reporter) -> None:
    if not path.is_file():
        reporter.fail(f"missing file: {path}")
        return

    text = path.read_text(encoding="utf-8")
    lines = text.splitlines()
    headings = collect_headings(lines)
    kind = infer_kind(path, headings)

    if not has_title(lines):
        reporter.fail("missing H1 title (# Title)")
    else:
        reporter.ok("H1 title present")

    if stub_like(text):
        reporter.fail("file looks like a stub; expand before finishing")
    else:
        reporter.ok("substantive content")

    if kind == "dsa":
        has_examples = "examples" in headings or "example" in headings or "example input" in text.lower()
        has_constraints = "constraints" in headings or "problem constraints" in headings
        has_description = "problem description" in headings or legacy_heading_text(lines[0] if lines else "") == "problem description"
        labeled_io = has_labeled_example_parts(text) or "example input" in text.lower()

        if not has_examples:
            reporter.fail(f"missing examples section (found headings: {sorted(headings)})")
        elif not has_constraints:
            reporter.fail("missing constraints section")
        elif not has_description and not has_title(lines):
            reporter.fail("missing problem description")
        else:
            reporter.ok("DSA section coverage")

        if not labeled_io:
            reporter.fail("examples must label Input and Output clearly")
        elif "input format" not in headings and "output format" not in headings:
            reporter.warn("no dedicated Input/Output Format sections; acceptable if examples are explicit")
        else:
            reporter.ok("input/output format documented")

        examples = count_examples(text)
        if examples == 0 and "example input" not in text.lower():
            reporter.warn("no ### Example blocks; ensure at least one worked example exists")
        elif examples > 2:
            reporter.warn(f"{examples} examples; skill budget prefers ≤2 full examples")
        else:
            reporter.ok(f"example count OK ({max(examples, 1)})")

    elif kind == "machine_coding":
        if "functional requirements" not in headings and "problem statement" not in headings:
            reporter.fail("machine coding statements need Functional Requirements or Problem Statement")
        else:
            reporter.ok("machine coding section coverage")

    elif kind == "system_design":
        if "functional requirements" not in headings:
            reporter.warn("system design usually includes Functional Requirements")

    broken = broken_image_refs(text, path.parent)
    if broken:
        reporter.fail(f"broken image refs: {', '.join(broken)}")
    else:
        reporter.ok("no broken local image refs")

    if excessive_blank_lines(text):
        reporter.warn("excessive blank lines (>3 consecutive); tighten spacing")
    else:
        reporter.ok("spacing OK")

    line_count = len(lines)
    if kind == "dsa" and line_count > 160:
        reporter.warn(f"{line_count} lines; consider trimming for token efficiency")
    elif kind == "dsa":
        reporter.ok(f"length OK ({line_count} lines)")


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("paths", nargs="+", help="Problem_Statement.md files to validate")
    args = parser.parse_args()

    reporter = Reporter()
    for raw in args.paths:
        validate(Path(raw).resolve(), reporter)

    reporter.print()
    return 1 if reporter.failures else 0


if __name__ == "__main__":
    raise SystemExit(main())
