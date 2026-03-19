# In-Memory Message Broker - Machine Coding Problem

Design and implement an **in-memory message broker library** with support for topics, publishers, and consumers.

## Problem Statement

Build a broker where:
- A **Publisher** publishes messages to a **Topic**.
- Multiple **Publishers** can publish to the same topic.
- Multiple **Consumers** can consume messages from the same topic.
- The broker can manage multiple topics.

The solution should focus on correctness, concurrency, and clean object-oriented design.

## Functional Requirements

### 1) Topic Management
- Create topic
- Delete topic
- Handle invalid operations gracefully (for example, duplicate create or delete of non-existing topic)

### 2) Publishing
- Publish string messages to a topic
- Support **parallel publishing** (multiple publishers concurrently)
- Preserve topic ordering guarantees as per your design (must be explicitly documented)

### 3) Consumption
- Consumers should be notified/able to consume when new messages are available for their topic
- Each consumer must maintain its **own offset**
- A consumer should read only from its current offset onward

### 4) Offset Management
- Track per-consumer offset for each subscribed topic
- Ensure offset moves only when a message is considered consumed (define your policy clearly)

### 5) Retention
- Each topic has a configurable **retention period**
- Messages older than retention must be permanently removed
- Expired messages must not be consumable

### 6) Exception Handling
- Handle failures in broker operations gracefully
- Avoid crashing producer/consumer flow due to one bad operation

## Bonus Requirements

1. **Reset offset and replay** messages from a given offset.
2. **Observability APIs** to expose:
   - Consumer last-read offset
   - Consumer lag per topic

## Assumptions / Clarifications

1. Message payload can be a simple string.
2. One publisher publishes to one topic, and one consumer consumes from one topic; however, a topic can have multiple publishers and consumers.
3. Offsets are managed independently per consumer.
4. Once retention expires for a message, it is permanently deleted and cannot be consumed.
5. Lag API should return at least current offset and lag for a consumer.
6. Parallel publishing means multiple publishers can publish to the same topic simultaneously without blocking each other unnecessarily.

## Suggested APIs (Indicative)

- `createTopic(topicId, retentionMs)`
- `deleteTopic(topicId)`
- `publish(topicId, message)`
- `subscribe(topicId, consumer)`
- `poll(consumer)` or `consume(consumer)`
- `getConsumerState(topicId, consumerId)` -> offset, lag
- `resetOffset(topicId, consumerId, offset)` (bonus)

You may choose different method names/signatures, but equivalent behavior is expected.

## Edge Cases to Consider

- Publishing to unknown topic
- Duplicate topic creation
- Consumer slower than producer (lag growth)
- Concurrent publish + retention cleanup
- Offset reset beyond available range
- Topic deletion while publishers/consumers are active

## Deliverables

1. Working in-memory broker implementation
2. Demo showing:
   - Topic creation/deletion
   - Parallel publishing
   - Multi-consumer consumption with independent offsets
   - Retention behavior
3. Tests for critical behavior (concurrency, offsets, and retention)

## Evaluation Criteria

- Correctness of topic/publish/consume lifecycle
- Thread safety under concurrent publishing and consuming
- Sound offset management
- Retention enforcement accuracy
- Code clarity, extensibility, and test quality




