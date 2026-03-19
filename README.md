# Quest: Problem Solving, DSA, and Machine Coding

Quest is a practice repository for:
- Data Structures and Algorithms (DSA) problems
- Machine coding exercises

Problems include a `Problem_Statement.md` and one or more language implementations (`Code.java`, `Code.js`, `Code.cpp`, etc.).

## Repository Layout

```
Quest/
├── README.md
├── CONTRIBUTING.md
├── pom.xml
├── src/
│   ├── main/
│   │   ├── data_struct_algo/
│   │   │   ├── array/
│   │   │   ├── hash_map/
│   │   │   ├── linked_list/
│   │   │   └── stack/
│   │   └── machine_coding/
│   │       ├── logger/
│   │       ├── message_broker/
│   │       └── splitwise/
│   └── test/
│       └── machine_coding/
│           └── message_broker/
├── LICENSE
└── .gitignore
```

## Current Categories

### Data Structures and Algorithms
- [Array](src/main/data_struct_algo/array)
- [Hash Map](src/main/data_struct_algo/hash_map)
- [Linked List](src/main/data_struct_algo/linked_list)
- [Stack](src/main/data_struct_algo/stack)

### Machine Coding
- [Logger](src/main/machine_coding/logger)
- [Message Broker](src/main/machine_coding/message_broker)
- [Splitwise](src/main/machine_coding/splitwise)

## How to Use

1. Open a topic folder under `src/main/data_struct_algo` or `src/main/machine_coding`.
2. Read the corresponding `Problem_Statement.md` (or `Question` in some modules).
3. Review or run the implementation files in that folder.

## Build and Test

This project uses Maven and is configured for Java 21.

```bash
mvn clean test
```

## Contributing

Please read [CONTRIBUTING.md](CONTRIBUTING.md) before opening a PR.

## License

This project is licensed under the MIT License. See [LICENSE](LICENSE).
