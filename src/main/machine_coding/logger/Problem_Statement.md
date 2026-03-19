# Logger Library - Machine Coding Problem

Design and implement a production-style logger library.

The logger should support:
- Multiple log levels
- Pluggable output destinations (appenders)
- Customizable log formatting
- Synchronous and asynchronous logging modes
- Safe behavior in a multi-threaded environment

The solution should follow SOLID principles and clean object-oriented design.

## Problem Statement

Build a reusable logging library that applications can use to emit logs at different severity levels.

The library must allow users to:
1. Configure the minimum log level to be processed.
2. Choose one or more output destinations (for example, console and/or file).
3. Configure the log message format.
4. Use either:
   - **Synchronous logging** (write on caller thread), or
   - **Asynchronous logging** (enqueue and write using background worker thread).
5. Set a configurable buffer size for asynchronous mode.
6. Shut down gracefully in async mode (drain queued logs before exit).

## Functional Requirements

### 1) Log Levels
Support at least these levels:
- DEBUG
- INFO
- WARN
- ERROR
- FATAL

Logs below configured minimum level should be ignored.

### 2) Log Message Model
Each log event should contain at least:
- Timestamp
- Thread name
- Log level
- Message content

### 3) Destination/Appender Support
- Create an abstraction for destinations (Appender interface).
- Implement at least one concrete appender (Console).
- Design should make it easy to add new appenders (for example, FileAppender) without changing existing core logger logic.

### 4) Formatter Support
- Create an abstraction for format strategy.
- Implement a default formatter.
- Allow custom formatter injection from configuration.

### 5) Logger Types
- **SyncLogger**:
  - Filters by log level.
  - Formats and writes immediately.
- **AsyncLogger**:
  - Filters by log level.
  - Pushes accepted logs to a bounded buffer/queue.
  - A worker thread consumes logs and writes to appenders.
  - Handle backpressure when buffer is full.
  - Support graceful shutdown to avoid losing queued logs.

### 6) Thread Safety
- Multiple producer threads should be able to log concurrently.
- Output should not get corrupted/interleaved unexpectedly.
- Async shutdown and worker lifecycle must be race-safe.

## Non-Functional Expectations

- Follow SOLID and OOD principles.
- Keep abstractions clean and extensible.
- Keep code readable and testable.
- Avoid hardcoding destination or format logic in core logger class.

## Suggested Class Design (Example)

- `LogLevel` (enum)
- `LogMessage` (data object)
- `LogFormatter` (interface) and `DefaultLogFormatter`
- `LogAppender` (interface) and `ConsoleAppender`
- `LoggerConfig` (minimum level, formatter, list of appenders)
- `Logger` (abstract base)
  - `SyncLogger`
  - `AsyncLogger`

This is a suggested direction, not a strict requirement.

## Edge Cases to Handle

- Null/empty messages
- Async buffer full behavior (block/drop/retry policy must be explicit)
- Logging after async shutdown is triggered
- Worker interruption handling
- Ensuring no deadlock during shutdown

## Deliverables

1. Running code for the logger library.
2. Small demo program that:
   - Configures logger
   - Logs from multiple threads
   - Demonstrates sync and async behavior
   - Demonstrates level filtering
3. Basic tests (unit and/or integration style) for key behavior:
   - Level filtering
   - Formatter invocation
   - Appender invocation
   - Async enqueue and drain-on-shutdown
   - Multi-threaded correctness

## Evaluation Criteria

- Correctness of behavior
- Thread safety in concurrent logging
- Extensibility of design
- SOLID adherence
- Code readability and maintainability
- Test quality and coverage

## Solution Approach (Reference)

A clean way to implement this is:
1. Route all public log APIs (`debug`, `info`, `warn`, `error`, `fatal`) through a common base `Logger`.
2. In base `Logger`, apply level filtering using configured minimum level.
3. Create a `LogMessage` with metadata (timestamp, thread, level, message).
4. Delegate write behavior to concrete logger types:
   - `SyncLogger`: write immediately on caller thread.
   - `AsyncLogger`: enqueue in bounded buffer; background worker consumes and writes.
5. Format first (`LogFormatter`) and then fan-out to all configured destinations (`LogAppender`).
6. For async mode, expose `shutdown()` that completes queued work before exit.

This keeps logger orchestration in one place while making formatting and destinations independently extensible.

## Project Structure (Current)

- `logger/enums`
  - `LogLevel`: severity ordering for filtering.
- `logger/pojo`
  - `LogMessage`: immutable log event payload.
- `logger/service/formatter`
  - `LogFormatter` (interface), `DefaultLogFormatter` (implementation).
- `logger/service/appender`
  - `LogAppender` (interface), `ConsoleAppender` (implementation).
- `logger/config`
  - `LoggerConfig`: minimum level + formatter + appenders.
- `logger/service/logger`
  - `Logger` (base abstraction), `SyncLogger`, `AsyncLogger`.
- `logger/LoggerDemo.java`
  - Basic usage and multithreaded async demonstration.
