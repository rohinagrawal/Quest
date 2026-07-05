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

## Local Setup

Prerequisites:

- Java 21
- Maven 3.9 or newer
- IntelliJ IDEA or another editor that can work with custom Java source roots

This repository intentionally keeps Java sources under topic folders such as `src/main/data_struct_algo` and `src/main/machine_coding` instead of the default Maven `src/main/java` layout. The checked-in `Quest.iml` marks the current source and test roots for IntelliJ.

Useful checks:

```bash
java --version
mvn --version
```

For Java machine-coding work, open the project in IntelliJ and run the relevant demo or JUnit test from the module source roots. The Maven `pom.xml` declares Java 21, Lombok, logging, Jackson, JUnit, AssertJ, and Mockito dependencies, but the current source layout is not the standard Maven layout.

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
